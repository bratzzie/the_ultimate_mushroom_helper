package org.nau.diploma.chantarellecheck.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.nau.diploma.chantarellecheck.RecycleViewItem;
import org.nau.diploma.chantarellecheck.SerializableItem;
import org.nau.diploma.chantarellecheck.adapters.clicklisteners.CollectionItemClickListener;
import org.nau.diploma.chantarellecheck.fragments.CollectionRecycleViewAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rg.nau.diploma.chantarellecheck.R;

public class HomeCollectionActivity extends AppCompatActivity {
    private ObjectMapper objectMapper;
    private AssetManager assetManager;

    public HomeCollectionActivity() {
        objectMapper = new ObjectMapper();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_collection);

        assetManager = getAssets();

        FloatingActionButton backButton = findViewById(R.id.floatingActionButton2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String image_id = intent.getStringExtra("image_id");
        List<String> items = intent.getStringArrayListExtra("items_ids");

        // data to populate the RecyclerView with
        List<SerializableItem> unprocessedItems = new ArrayList<>();

        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("new_dataset.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        try {
            unprocessedItems = objectMapper.readValue(json, new TypeReference<List<SerializableItem>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        List<RecycleViewItem> recycleViewItems = new ArrayList<>();
        for (SerializableItem unItem : unprocessedItems) {
            Bitmap thumbImage = null;
            try {
                thumbImage = ThumbnailUtils.extractThumbnail(generateBitmapFromURL(unItem.getImageURL()), 128, 128);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            RecycleViewItem item = new RecycleViewItem(unItem.getId(), unItem.getName(), unItem.getSeason(), unItem.getEatable(), unItem.scientificNames,
                    unItem.getCommonNames(), unItem.habitat, unItem.distribution,
                    unItem.getCap(), unItem.getTubes(), unItem.getStem(), unItem.getFlesh(),unItem.getAMH(),
                    unItem.getACW(), unItem.getSmell(), unItem.getSporePrint(), unItem.getFrequency(), unItem.getImageURL(),
                    thumbImage, unItem.getConfusionItems());
            recycleViewItems.add(item);
        }

        findViewById(R.id.imageView16).setBackgroundResource(getResources().getIdentifier(image_id, "drawable", this.getPackageName()));
        ((TextView) findViewById(R.id.textView24)).setText(title);

        List<RecycleViewItem> filteredItems = new ArrayList<>();
        for (String item_id : items) {
           filteredItems.add(recycleViewItems.stream().filter(recycleViewItem -> recycleViewItem.getId().equals(item_id)).findFirst().get());
        }

        CollectionRecycleViewAdapter recyclerViewAdapter = new CollectionRecycleViewAdapter(this, filteredItems);
        CollectionItemClickListener itemClickListener = new CollectionItemClickListener(recyclerViewAdapter, this);
        recyclerViewAdapter.setClickListener(itemClickListener);
        RecyclerView recyclerView = findViewById(R.id.recycleview_home);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private Bitmap generateBitmapFromURL(String url) throws IOException {
        try (
                InputStream inputStream = assetManager.open(url)
        ) {
            return BitmapFactory.decodeStream(inputStream);
        }
    }
}