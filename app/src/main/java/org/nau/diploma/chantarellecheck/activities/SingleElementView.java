package org.nau.diploma.chantarellecheck.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import org.nau.diploma.chantarellecheck.ServiceForLoadingMushroomsDataset;
import org.nau.diploma.chantarellecheck.adapters.SimilarSpeciesRecyclerViewAdapter;
import org.nau.diploma.chantarellecheck.adapters.clicklisteners.SimilarSpeciesItemClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import rg.nau.diploma.chantarellecheck.R;
import rg.nau.diploma.chantarellecheck.databinding.SingleElementFragmentBinding;

public class SingleElementView extends AppCompatActivity{

    private AssetManager assetManager;
    private Bitmap generateBitmapFromURL(String url) throws IOException {
        try (
                //declaration of inputStream in try-with-resources statement will automatically close inputStream
                // ==> no explicit inputStream.close() in additional block finally {...} necessary
                InputStream inputStream = assetManager.open(url)
        ) {
            return BitmapFactory.decodeStream(inputStream);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingleElementFragmentBinding singleElementViewBinding = SingleElementFragmentBinding.inflate(getLayoutInflater());
        setContentView(singleElementViewBinding.getRoot());

        assetManager = getAssets();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        List<String> scientificNames = intent.getStringArrayListExtra("scientificNames");
        List<String> commonNames = intent.getStringArrayListExtra("commonNames");
        String imageURL = intent.getStringExtra("image");
        String habitat = intent.getStringExtra("habitat");
        String distribution = intent.getStringExtra("distribution");
        String cap = intent.getStringExtra("cap");
        String tubes = intent.getStringExtra("tubes");
        String stem = intent.getStringExtra("stem");
        String flesh = intent.getStringExtra("flesh");
        String AMH = intent.getStringExtra("AMH");
        String ACW = intent.getStringExtra("ACW");
        String smell = intent.getStringExtra("smell");
        String sporePrint = intent.getStringExtra("sporePrint");
        String frequency = intent.getStringExtra("frequency");
        String season = intent.getStringExtra("season");
        String eatable = intent.getStringExtra("eatable");
        String id = intent.getStringExtra("id");


        Bitmap image;
        try {
            image = generateBitmapFromURL(imageURL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShapeableImageView mainPhoto = findViewById(R.id.imageView19);
            mainPhoto.setImageBitmap(image);


        Bitmap blurred = blurRenderScript(this,image, 25);
        ((ImageView) findViewById(R.id.imageView20)).setImageBitmap(blurred);

        ((TextView) findViewById(R.id.textView25)).setText(name);
        ((TextView) findViewById(R.id.textView27)).setText(String.join(", ", scientificNames));
        ((TextView) findViewById(R.id.textView29)).setText(String.join(", ", commonNames));
        ((TextView) findViewById(R.id.textView31)).setText(eatable);
        ((TextView) findViewById(R.id.textView33)).setText(season);
        ((TextView) findViewById(R.id.textView36)).setText(habitat);
        ((TextView) findViewById(R.id.textView37)).setText(distribution);
        ((TextView) findViewById(R.id.textView40)).setText(cap);
        ((TextView) findViewById(R.id.textView41)).setText(tubes);
        ((TextView) findViewById(R.id.textView43)).setText(stem);
        ((TextView) findViewById(R.id.textView45)).setText(flesh);
        ((TextView) findViewById(R.id.textView49)).setText(AMH);
        ((TextView) findViewById(R.id.textView51)).setText(ACW);
        ((TextView) findViewById(R.id.textView57)).setText(smell);
        ((TextView) findViewById(R.id.textView58)).setText(frequency);
        ((TextView) findViewById(R.id.textView59)).setText(sporePrint);



        try {
            ((ImageView) findViewById(R.id.imageView21)).setImageBitmap(generateBitmapFromURL("images_dataset/" + id + "/" + "cap.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            ((ImageView) findViewById(R.id.imageView22)).setImageBitmap(generateBitmapFromURL("images_dataset/" + id + "/" + "tubes.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            ((ImageView) findViewById(R.id.imageView23)).setImageBitmap(generateBitmapFromURL("images_dataset/" + id + "/" + "stem.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            ((ImageView) findViewById(R.id.imageView24)).setImageBitmap(generateBitmapFromURL("images_dataset/" + id + "/" + "flesh.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        ImageButton show = (ImageButton) findViewById(R.id.show);
        ImageButton hide = (ImageButton) findViewById(R.id.hide);

        show.setVisibility(View.INVISIBLE);
        hide.setVisibility(View.VISIBLE);

        show.setOnClickListener(v -> {
            System.out.println("Show button");
            show.setVisibility(View.INVISIBLE);
            hide.setVisibility(View.VISIBLE);

            findViewById(R.id.textView34).setVisibility(View.VISIBLE);
            findViewById(R.id.textView35).setVisibility(View.VISIBLE);
            findViewById(R.id.textView36).setVisibility(View.VISIBLE);
            findViewById(R.id.textView37).setVisibility(View.VISIBLE);
        });

        hide.setOnClickListener(v -> {
            System.out.println("Hide button");
            hide.setVisibility(View.INVISIBLE);
            show.setVisibility(View.VISIBLE);

            findViewById(R.id.textView34).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView35).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView36).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView37).setVisibility(View.INVISIBLE);
        });


        ImageButton showIdentify = (ImageButton) findViewById(R.id.ia_show);
        ImageButton hideIdentify = (ImageButton) findViewById(R.id.ia_hide);

        showIdentify.setVisibility(View.INVISIBLE);
        hideIdentify.setVisibility(View.VISIBLE);

        showIdentify.setOnClickListener(v -> {
            System.out.println("Show button");
            showIdentify.setVisibility(View.INVISIBLE);
            hideIdentify.setVisibility(View.VISIBLE);

            findViewById(R.id.textView38).setVisibility(View.VISIBLE);
            findViewById(R.id.textView39).setVisibility(View.VISIBLE);
            findViewById(R.id.textView40).setVisibility(View.VISIBLE);
            findViewById(R.id.textView41).setVisibility(View.VISIBLE);
            findViewById(R.id.textView42).setVisibility(View.VISIBLE);
            findViewById(R.id.textView43).setVisibility(View.VISIBLE);
            findViewById(R.id.textView44).setVisibility(View.VISIBLE);
            findViewById(R.id.textView45).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView21).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView22).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView23).setVisibility(View.VISIBLE);
            findViewById(R.id.textView24).setVisibility(View.VISIBLE);
        });

        hideIdentify.setOnClickListener(v -> {
            System.out.println("Hide button");
            hideIdentify.setVisibility(View.INVISIBLE);
            showIdentify.setVisibility(View.VISIBLE);

            findViewById(R.id.textView38).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView39).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView40).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView41).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView42).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView43).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView44).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView45).setVisibility(View.INVISIBLE);
            findViewById(R.id.imageView21).setVisibility(View.INVISIBLE);
            findViewById(R.id.imageView22).setVisibility(View.INVISIBLE);
            findViewById(R.id.imageView23).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView24).setVisibility(View.INVISIBLE);
        });


        ImageButton showCharacteristics = (ImageButton) findViewById(R.id.c_show);
        ImageButton hideCharacteristics = (ImageButton) findViewById(R.id.c_hide);

        showCharacteristics.setVisibility(View.INVISIBLE);
        hideCharacteristics.setVisibility(View.VISIBLE);

        showCharacteristics.setOnClickListener(v -> {
            System.out.println("Show button");
            showCharacteristics.setVisibility(View.INVISIBLE);
            hideCharacteristics.setVisibility(View.VISIBLE);
            findViewById(R.id.textView48).setVisibility(View.VISIBLE);
            findViewById(R.id.textView49).setVisibility(View.VISIBLE);
            findViewById(R.id.textView50).setVisibility(View.VISIBLE);
            findViewById(R.id.textView51).setVisibility(View.VISIBLE);
            findViewById(R.id.textView53).setVisibility(View.VISIBLE);
            findViewById(R.id.textView54).setVisibility(View.VISIBLE);
            findViewById(R.id.textView55).setVisibility(View.VISIBLE);
            findViewById(R.id.textView57).setVisibility(View.VISIBLE);
            findViewById(R.id.textView58).setVisibility(View.VISIBLE);
            findViewById(R.id.textView59).setVisibility(View.VISIBLE);

        });

        hideCharacteristics.setOnClickListener(v -> {
            System.out.println("Hide button");
            hideCharacteristics.setVisibility(View.INVISIBLE);
            showCharacteristics.setVisibility(View.VISIBLE);
            findViewById(R.id.textView48).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView49).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView50).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView51).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView53).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView54).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView55).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView57).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView58).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView59).setVisibility(View.INVISIBLE);
        });

        SimilarSpeciesRecyclerViewAdapter recyclerViewAdapter = new SimilarSpeciesRecyclerViewAdapter(this, ServiceForLoadingMushroomsDataset.getSimilarItems(id, getApplicationContext()));
        SimilarSpeciesItemClickListener clickListener = new SimilarSpeciesItemClickListener(recyclerViewAdapter);
        recyclerViewAdapter.setClickListener(clickListener);

        RecyclerView similarItemsRecyclerView = findViewById(R.id.similar_species_recyclew_view);

        similarItemsRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        similarItemsRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        similarItemsRecyclerView.setAdapter(recyclerViewAdapter);

        ImageButton showSimilar = (ImageButton) findViewById(R.id.ss_show);
        ImageButton hideSimilar = (ImageButton) findViewById(R.id.ss_hide);
        showSimilar.setVisibility(View.INVISIBLE);
        hideSimilar.setVisibility(View.VISIBLE);
        recyclerViewAdapter.setShowItems(true);
        recyclerViewAdapter.notifyDataSetChanged();


        showSimilar.setOnClickListener(v -> {
            System.out.println("Show button");
            showSimilar.setVisibility(View.INVISIBLE);
            hideSimilar.setVisibility(View.VISIBLE);
            recyclerViewAdapter.setShowItems(true);
            recyclerViewAdapter.notifyDataSetChanged();
        });

        hideSimilar.setOnClickListener(v -> {
            System.out.println("Hide button");
            hideSimilar.setVisibility(View.INVISIBLE);
            showSimilar.setVisibility(View.VISIBLE);
            recyclerViewAdapter.setShowItems(false);
            recyclerViewAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NewApi")
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;
    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }


}