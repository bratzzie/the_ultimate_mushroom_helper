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
package org.nau.diploma.chantarellecheck.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import org.nau.diploma.chantarellecheck.ImageClassifierHelper;
import org.nau.diploma.chantarellecheck.activities.ScanResultActivity;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.vision.classifier.Classifications;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rg.nau.diploma.chantarellecheck.R;
import rg.nau.diploma.chantarellecheck.databinding.FragmentCameraBinding;

/** Fragment for displaying and controlling the device camera and other UI */
public class CameraFragment extends Fragment
        implements ImageClassifierHelper.ClassifierListener {
    private static final String TAG = "Image Classifier";

    private FragmentCameraBinding fragmentCameraBinding;
    private ImageClassifierHelper imageClassifierHelper;
    private Bitmap bitmapBuffer;
    private ClassificationResultAdapter classificationResultsAdapter;
    private ImageAnalysis imageAnalyzer;
    private ProcessCameraProvider cameraProvider;
    private final Object task = new Object();

    /**
     * Blocking camera operations are performed using this executor
     */
    private ExecutorService cameraExecutor;

    private String previousResult = "";

    private FloatingActionButton actionButton;
    private List<String> itemsNames = new ArrayList<>();
    private List<String> itemsScores = new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentCameraBinding = FragmentCameraBinding
                .inflate(inflater, container, false);
        

        cameraExecutor = Executors.newSingleThreadExecutor();
        imageClassifierHelper = ImageClassifierHelper.create(requireContext()
                , this);

        // setup result adapter
        classificationResultsAdapter = new ClassificationResultAdapter();
        classificationResultsAdapter
                .updateAdapterSize(imageClassifierHelper.getMaxResults());

        // Set up the camera and its use cases
        fragmentCameraBinding.viewFinder.post(this::setUpCamera);


        View createdView = fragmentCameraBinding.getRoot();


        actionButton = createdView.findViewById(R.id.make_analysis);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemsNames.size() != 0 && itemsScores.size() != 0) {
                Intent intent = new Intent(requireContext(), ScanResultActivity.class);
                intent.putStringArrayListExtra("results_ids", (ArrayList<String>) itemsNames);
                intent.putStringArrayListExtra("results_scores", (ArrayList<String>) itemsScores);
                requireContext().startActivity(intent);
                } else {
                    Toast.makeText(requireContext(), "No mushrooms identified!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return createdView;
    }

    @Override
    public void onResume() {
        super.onResume();

        itemsNames = new ArrayList<>();
        itemsScores = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Shut down our background executor
        cameraExecutor.shutdown();
        synchronized (task) {
            imageClassifierHelper.clearImageClassifier();
        }
    }



    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        imageAnalyzer.setTargetRotation(
                fragmentCameraBinding.viewFinder.getDisplay().getRotation()
        );
    }


    // Initialize CameraX, and prepare to bind the camera use cases
    private void setUpCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();

                // Build and bind the camera use cases
                bindCameraUseCases();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    // Declare and bind preview, capture and analysis use cases
    private void bindCameraUseCases() {
        // CameraSelector - makes assumption that we're only using the back
        // camera
        CameraSelector.Builder cameraSelectorBuilder = new CameraSelector.Builder();
        CameraSelector cameraSelector = cameraSelectorBuilder
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

        // Preview. Only using the 4:3 ratio because this is the closest to
        // our model
        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(
                        fragmentCameraBinding.viewFinder
                                .getDisplay().getRotation()
                )
                .build();

        // ImageAnalysis. Using RGBA 8888 to match how our models work
        imageAnalyzer = new ImageAnalysis.Builder()
                .setTargetRotation(fragmentCameraBinding.viewFinder.getDisplay().getRotation())
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build();

        // The analyzer can then be assigned to the instance
        imageAnalyzer.setAnalyzer(cameraExecutor, image -> {
            if (bitmapBuffer == null) {
                bitmapBuffer = Bitmap.createBitmap(
                        image.getWidth(),
                        image.getHeight(),
                        Bitmap.Config.ARGB_8888);
            }
            classifyImage(image);
        });

        // Must unbind the use-cases before rebinding them
        cameraProvider.unbindAll();

        try {
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageAnalyzer
            );

            // Attach the viewfinder's surface provider to preview use case
            preview.setSurfaceProvider(
                    fragmentCameraBinding.viewFinder.getSurfaceProvider()
            );
        } catch (Exception exc) {
            Log.e(TAG, "Use case binding failed", exc);
        }
    }

    private void classifyImage(@NonNull ImageProxy image) {
        // Copy out RGB bits to the shared bitmap buffer
        bitmapBuffer.copyPixelsFromBuffer(image.getPlanes()[0].getBuffer());

        int imageRotation = image.getImageInfo().getRotationDegrees();
        image.close();
        synchronized (task) {
            // Pass Bitmap and rotation to the image classifier helper for
            // processing and classification
            imageClassifierHelper.classify(bitmapBuffer, imageRotation);
        }
    }

    @Override
    public void onError(String error) {
       requireActivity().runOnUiThread(() -> {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            classificationResultsAdapter.updateResults(new ArrayList<>());
        });
    }

    @Override
    public void onResults(List<Classifications> results, long inferenceTime) {
        if (!results.get(0).getCategories().isEmpty()) {

            List<String> categories = new ArrayList<>();
            List<String> percentages = new ArrayList<>();
            for (Classifications classifications : results) {
                for (Category category : classifications.getCategories()) {
                    categories.add(category.getLabel());
                    percentages.add(String.valueOf(category.getScore()));
                }
            }
            itemsNames = categories;
            itemsScores = percentages;
        }

    }
}


