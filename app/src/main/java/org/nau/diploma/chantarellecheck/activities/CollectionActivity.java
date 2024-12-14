package org.nau.diploma.chantarellecheck.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.nau.diploma.chantarellecheck.RecycleViewItem;
import org.nau.diploma.chantarellecheck.SerializableItem;
import org.nau.diploma.chantarellecheck.fragments.CameraFragment;
import org.nau.diploma.chantarellecheck.fragments.CollectionRecycleViewAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rg.nau.diploma.chantarellecheck.R;

public class CollectionActivity extends AppCompatActivity implements CollectionRecycleViewAdapter.ItemClickListener {

    CollectionRecycleViewAdapter adapter;
    ObjectMapper objectMapper = new ObjectMapper();
    AssetManager assetManager;
    SearchView searchView;

    public CollectionActivity() {
        super(R.layout.collection_activity);
    }


    private Bitmap generateBitmapFromURL(String url) throws IOException {
        try (
                //declaration of inputStream in try-with-resources statement will automatically close inputStream
                // ==> no explicit inputStream.close() in additional block finally {...} necessary
                InputStream inputStream = assetManager.open(url)
        ) {
            return  BitmapFactory.decodeStream(inputStream);
    }
}


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rg.nau.diploma.chantarellecheck.databinding.CollectionActivityBinding collectionFragmentBinding
                = rg.nau.diploma.chantarellecheck.databinding.CollectionActivityBinding.inflate(getLayoutInflater());
        setContentView(collectionFragmentBinding.getRoot());
        assetManager = getAssets();


        searchView = findViewById(R.id.search_view);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterList(newText);
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            adapter.resetFilter();
            return true;
        });

        AppCompatImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
       closeButton.setOnClickListener(
               v -> {
                   adapter.resetFilter();
                   searchView.setQuery("", false);
                   searchView.clearFocus();
               }
       );
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
              //  System.err.println("Item is " + unItem.toString());
                thumbImage = ThumbnailUtils.extractThumbnail(generateBitmapFromURL(unItem.getImageURL()), 128, 128);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            RecycleViewItem item = new RecycleViewItem(unItem.getId(), unItem.getName(), unItem.getSeason(), unItem.getEatable(), unItem.getScientificNames(),
                    unItem.getCommonNames(), unItem.getHabitat(), unItem.getDistribution(),
                    unItem.getCap(), unItem.getTubes(), unItem.getStem(), unItem.getFlesh(),unItem.getAMH(),
                    unItem.getACW(), unItem.getSmell(), unItem.getSporePrint(), unItem.getFrequency(), unItem.getImageURL(),
                    thumbImage, unItem.getConfusionItems());
            recycleViewItems.add(item);
        }


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.collection_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        adapter = new CollectionRecycleViewAdapter(getApplicationContext(), recycleViewItems);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView= findViewById(R.id.collection_bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.encyclopedia_page);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan_page:
                        startActivity(new Intent(getApplicationContext(), CameraFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.encyclopedia_page:
                        return true;
                    case R.id.findings_page:
                        startActivity(new Intent(getApplicationContext(), FindingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }



    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), SingleElementView.class);

        RecycleViewItem selectedItem = adapter.getItem(position);

        intent.putExtra("name", selectedItem.getName());
        intent.putStringArrayListExtra("scientificNames", (ArrayList<String>) selectedItem.getScientificNames());
        intent.putStringArrayListExtra("commonNames", (ArrayList<String>) selectedItem.getCommonNames());
        intent.putExtra("image", selectedItem.getImageURL());
        intent.putExtra("habitat", selectedItem.getHabitat());
        intent.putExtra("distribution", selectedItem.getDistribution());
        intent.putExtra("cap", selectedItem.getCap());
        intent.putExtra("tubes", selectedItem.getTubes());
        intent.putExtra("stem", selectedItem.getStem());
        intent.putExtra("flesh", selectedItem.getFlesh());
        intent.putExtra("AMH", selectedItem.getAMH());
        intent.putExtra("ACW", selectedItem.getACW());
        intent.putExtra("smell", selectedItem.getSmell());
        intent.putExtra("sporePrint", selectedItem.getSporePrint());
        intent.putExtra("frequency", selectedItem.getFrequency());
        intent.putExtra("season", selectedItem.getSeason());
        intent.putExtra("eatable", selectedItem.getEatable());
        intent.putExtra("id", selectedItem.getId());


        CollectionActivity.this.startActivity(intent);
    }
}
