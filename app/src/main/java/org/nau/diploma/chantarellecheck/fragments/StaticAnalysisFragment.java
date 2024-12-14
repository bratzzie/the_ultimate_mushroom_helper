package org.nau.diploma.chantarellecheck.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.nau.diploma.chantarellecheck.ImageClassifierHelper;
import org.nau.diploma.chantarellecheck.RecycleViewItem;
import org.nau.diploma.chantarellecheck.ServiceForLoadingMushroomsDataset;
import org.nau.diploma.chantarellecheck.activities.SingleElementView;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.vision.classifier.Classifications;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rg.nau.diploma.chantarellecheck.R;


public class StaticAnalysisFragment extends Fragment implements ImageClassifierHelper.ClassifierListener{

    private Bitmap bitmap;
    ImageView imageView;
    Uri imageuri;
    MaterialButton classifyButton;

    public StaticAnalysisFragment() {
        super(R.layout.static_analysis_fragment);
    }

    private final Object task = new Object();
    private ImageClassifierHelper imageClassifierHelper;
    private int imageRotation = 0;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Shut down our background executor
        synchronized (task) {
            imageClassifierHelper.clearImageClassifier();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageClassifierHelper = ImageClassifierHelper.create(requireContext()
                , this);

        imageView=(ImageView) view.findViewById(R.id.static_image);
        classifyButton =(MaterialButton) view.findViewById(R.id.classify);

        imageView.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),12);
        });


        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (task) {
                    // Pass Bitmap and rotation to the image classifier helper for
                    // processing and classification
                    imageClassifierHelper.classify(bitmap, imageRotation);
                    imageRotation = 0;
                }
            }
        });

    }

    public Bitmap getBitmapFromContentUri(ContentResolver contentResolver, Uri imageUri)
            throws IOException {
        Bitmap decodedBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
        if (decodedBitmap == null) {
            return null;
        }
        int orientation = getExifOrientationTag(contentResolver, imageUri);

        int rotationDegrees = 0;
        boolean flipX = false;
        boolean flipY = false;
        // See e.g. https://magnushoff.com/articles/jpeg-orientation/ for a detailed explanation on each
        // orientation.
        switch (orientation) {
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                flipX = true;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotationDegrees = 90;
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                rotationDegrees = 90;
                flipX = true;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotationDegrees = 180;
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                flipY = true;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotationDegrees = -90;
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                rotationDegrees = -90;
                flipX = true;
                break;
            case ExifInterface.ORIENTATION_UNDEFINED:
            case ExifInterface.ORIENTATION_NORMAL:
            default:
                // No transformations necessary in this case.
        }

        imageRotation = rotationDegrees;
        return rotateBitmap(decodedBitmap, rotationDegrees, flipX, flipY);
    }

    /** Rotates a bitmap if it is converted from a bytebuffer. */
    private Bitmap rotateBitmap(
            Bitmap bitmap, int rotationDegrees, boolean flipX, boolean flipY) {
        Matrix matrix = new Matrix();

        // Rotate the image back to straight.
        matrix.postRotate(rotationDegrees);

        // Mirror the image along the X or Y axis.
        matrix.postScale(flipX ? -1.0f : 1.0f, flipY ? -1.0f : 1.0f);
        Bitmap rotatedBitmap =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // Recycle the old bitmap if it has changed.
        if (rotatedBitmap != bitmap) {
            bitmap.recycle();
        }
        return rotatedBitmap;
    }

    private int getExifOrientationTag(ContentResolver resolver, Uri imageUri) {
        // We only support parsing EXIF orientation tag from local file on the device.
        // See also:
        // https://android-developers.googleblog.com/2016/12/introducing-the-exifinterface-support-library.html
        if (!ContentResolver.SCHEME_CONTENT.equals(imageUri.getScheme())
                && !ContentResolver.SCHEME_FILE.equals(imageUri.getScheme())) {
            return 0;
        }

        ExifInterface exif;
        try (InputStream inputStream = resolver.openInputStream(imageUri)) {
            if (inputStream == null) {
                return 0;
            }

            exif = new ExifInterface(inputStream);
        } catch (IOException e) {
            Log.e("TAG", "failed to open file to read rotation meta data: " + imageUri, e);
            return 0;
        }

        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data!=null) {
            imageuri = data.getData();
            try {
                bitmap = getBitmapFromContentUri(getContext().getContentResolver(), imageuri);

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onError(String error) {
        requireActivity().runOnUiThread(() -> {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onResults(List<Classifications> results, long inferenceTime) {

        if (!results.get(0).getCategories().isEmpty()) {
            Category result = results.get(0).getCategories().get(0);

                requireActivity().runOnUiThread(() -> {
                    Snackbar.make(getView(), result.getLabel() + " " + String.format(Locale.US, "%.2f",result.getScore() * 100 ) + "%", Snackbar.LENGTH_LONG)
                            .setAction("Details...", v -> {

                                RecycleViewItem foundItem = ServiceForLoadingMushroomsDataset.getItemByName(result.getLabel().toLowerCase().trim(), getContext());

                                if (foundItem != null) {
                                    Intent intent = new Intent(getContext(), SingleElementView.class);
                                    intent.putExtra("name", foundItem.getName());
                                    intent.putStringArrayListExtra("scientificNames", (ArrayList<String>) foundItem.getScientificNames());
                                    intent.putStringArrayListExtra("commonNames", (ArrayList<String>) foundItem.getCommonNames());
                                    intent.putExtra("image", foundItem.getImageURL());
                                    intent.putExtra("habitat", foundItem.getHabitat());
                                    intent.putExtra("distribution", foundItem.getDistribution());
                                    intent.putExtra("cap", foundItem.getCap());
                                    intent.putExtra("tubes", foundItem.getTubes());
                                    intent.putExtra("stem", foundItem.getStem());
                                    intent.putExtra("flesh", foundItem.getFlesh());
                                    intent.putExtra("AMH", foundItem.getAMH());
                                    intent.putExtra("ACW", foundItem.getACW());
                                    intent.putExtra("smell", foundItem.getSmell());
                                    intent.putExtra("sporePrint", foundItem.getSporePrint());
                                    intent.putExtra("frequency", foundItem.getFrequency());
                                    intent.putExtra("season", foundItem.getSeason());
                                    intent.putExtra("eatable", foundItem.getEatable());
                                    intent.putExtra("id", foundItem.getId());

                                    Toast.makeText(getContext(), "Inside if statement", Toast.LENGTH_SHORT).show();
                                    getContext().startActivity(intent);
                                }
                            })
                            .show();
                });
        }
    }
}

