package org.nau.diploma.chantarellecheck;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    private Utils() {

    }

    public static Bitmap generateBitmapFromURL(String url, AssetManager assetManager) throws IOException {
        try (
                //declaration of inputStream in try-with-resources statement will automatically close inputStream
                // ==> no explicit inputStream.close() in additional block finally {...} necessary
                InputStream inputStream = assetManager.open(url)
        ) {
            return BitmapFactory.decodeStream(inputStream);
        }
    }
}
