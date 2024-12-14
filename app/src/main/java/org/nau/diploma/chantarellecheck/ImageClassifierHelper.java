/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nau.diploma.chantarellecheck;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

import org.tensorflow.lite.gpu.CompatibilityList;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.Rot90Op;
import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.vision.classifier.Classifications;
import org.tensorflow.lite.task.vision.classifier.ImageClassifier;

import java.io.IOException;
import java.util.List;

/** Helper class for wrapping Image Classification actions */
public class ImageClassifierHelper {
    private static final String TAG = "ImageClassifierHelper";

    private float threshold;
    private int numThreads;
    private int maxResults;
    private int currentDelegate;
    private int currentModel;
    private final Context context;
    private final ClassifierListener imageClassifierListener;
    private ImageClassifier imageClassifier;

    /** Helper class for wrapping Image Classification actions */
    public ImageClassifierHelper(Float threshold,
                                 int numThreads,
                                 int maxResults,
                                 int currentDelegate,
                                 int currentModel,
                                 Context context,
                                 ClassifierListener imageClassifierListener) {
        this.threshold = threshold;
        this.numThreads = numThreads;
        this.maxResults = maxResults;
        this.currentDelegate = currentDelegate;
        this.currentModel = currentModel;
        this.context = context;
        this.imageClassifierListener = imageClassifierListener;
        setupImageClassifier();
    }

    public static ImageClassifierHelper create(
            Context context,
            ClassifierListener listener
    ) {
        return new ImageClassifierHelper(
                0.40f,
                2,
                4,
                0,
                0,
                context,
                listener
        );
    }


    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }


    public int getMaxResults() {
        return maxResults;
    }



    private void setupImageClassifier() {
        ImageClassifier.ImageClassifierOptions.Builder optionsBuilder =
                ImageClassifier.ImageClassifierOptions.builder()
                        .setScoreThreshold(threshold)
                        .setMaxResults(maxResults);

        BaseOptions.Builder baseOptionsBuilder =
                BaseOptions.builder().setNumThreads(numThreads);

        if (new CompatibilityList().isDelegateSupportedOnThisDevice()) {
            baseOptionsBuilder.useGpu();
        }

        String modelName = "newest_model.tflite";

        try {
            imageClassifier =
                    ImageClassifier.createFromFileAndOptions(
                            context,
                            modelName,
                            optionsBuilder.build());
        } catch (IOException e) {
            imageClassifierListener.onError("Image classifier failed to "
                    + "initialize. See error logs for details");
            Log.e(TAG, "TFLite failed to load model with error: "
                    + e.getMessage());
        }
    }

    public void classify(Bitmap image, int imageRotation) {
        if (imageClassifier == null) {
            setupImageClassifier();
        }

        // Inference time is the difference between the system time at the start and finish of the process
        long inferenceTime = SystemClock.uptimeMillis();

        // Create preprocessor for the image.
        // See https://www.tensorflow.org/lite/inference_with_metadata/
        //            lite_support#imageprocessor_architecture
        ImageProcessor imageProcessor =
                new ImageProcessor.Builder().add(new Rot90Op(-imageRotation / 90))
                        .add(new ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                       .build();

        // Preprocess the image and convert it into a TensorImage for classification.
        TensorImage tensorImage =
                imageProcessor.process(TensorImage.fromBitmap(image));

        List<Classifications> result = imageClassifier.classify(tensorImage);

        inferenceTime = SystemClock.uptimeMillis() - inferenceTime;
        imageClassifierListener.onResults(result, inferenceTime);
    }

    public void clearImageClassifier() {
        imageClassifier = null;
    }

    /** Listener for passing results back to calling class */
    public interface ClassifierListener {
        void onError(String error);

        void onResults(List<Classifications> results, long inferenceTime);
    }
}
