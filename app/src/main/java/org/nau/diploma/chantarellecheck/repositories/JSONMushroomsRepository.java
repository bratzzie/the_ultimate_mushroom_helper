package org.nau.diploma.chantarellecheck.repositories;


import static org.nau.diploma.chantarellecheck.Utils.generateBitmapFromURL;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.nau.diploma.chantarellecheck.SerializableItem;
import org.nau.diploma.chantarellecheck.RecycleViewItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONMushroomsRepository implements MushroomsRepository {
    private final Context context;
    public JSONMushroomsRepository(Context context) {
        this.context = context;
    }
    @Override
    public List<RecycleViewItem> getAllItems() {
            ObjectMapper objectMapper = new ObjectMapper();

            // data to populate the RecyclerView with
            List<SerializableItem> unprocessedItems = new ArrayList<>();

            String json = null;
            try {
                InputStream is = context.getAssets().open("new_dataset.json");
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


            List<RecycleViewItem> items = new ArrayList<>();
            for (SerializableItem unItem : unprocessedItems) {
                Bitmap thumbImage = null;
                try {
                    thumbImage = ThumbnailUtils.extractThumbnail(generateBitmapFromURL(unItem.getImageURL(), context.getAssets()), 128, 128);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                RecycleViewItem item = new RecycleViewItem(unItem.getId(), unItem.getName(), unItem.getSeason(), unItem.getEatable(), unItem.getScientificNames(),
                        unItem.getCommonNames(), unItem.getHabitat(), unItem.getDistribution(),
                        unItem.getCap(), unItem.getTubes(), unItem.getStem(), unItem.getFlesh(),unItem.getAMH(),
                        unItem.getACW(), unItem.getSmell(), unItem.getSporePrint(), unItem.getFrequency(), unItem.getImageURL(),
                        thumbImage, unItem.getConfusionItems());
                items.add(item);
            }

            return items;
    }
}
