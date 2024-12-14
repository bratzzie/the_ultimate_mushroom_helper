package org.nau.diploma.chantarellecheck;


import static org.nau.diploma.chantarellecheck.Utils.generateBitmapFromURL;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ServiceForLoadingMushroomsDataset {
    private static ServiceForLoadingMushroomsDataset instance;
    private static List<RecycleViewItem> items = new ArrayList<>();

    private ServiceForLoadingMushroomsDataset() {

    }

    public static ServiceForLoadingMushroomsDataset getInstance(Context context) {
        if (instance == null) {
            initService(context);
        }
            return instance;

    }

    private static void initService(Context context) {
        instance = new ServiceForLoadingMushroomsDataset();
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


        items = new ArrayList<>();
        for (SerializableItem unItem : unprocessedItems) {
            Bitmap thumbImage = null;
            try {
                thumbImage = ThumbnailUtils.extractThumbnail(generateBitmapFromURL(unItem.getImageURL(), context.getAssets()), 128, 128);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            RecycleViewItem item = new RecycleViewItem(unItem.getId(), unItem.getName(), unItem.getSeason(), unItem.getEatable(), unItem.scientificNames,
                    unItem.getCommonNames(), unItem.habitat, unItem.distribution,
                    unItem.getCap(), unItem.getTubes(), unItem.getStem(), unItem.getFlesh(),unItem.getAMH(),
                    unItem.getACW(), unItem.getSmell(), unItem.getSporePrint(), unItem.getFrequency(), unItem.getImageURL(),
                     thumbImage, unItem.getConfusionItems());
            items.add(item);
        }

    }

    public static RecycleViewItem getItemByName(String name, Context context) {
        if (instance == null) {
            initService(context);
        }

        return items.stream().filter(item -> item.getName().toLowerCase().equals(name)).findFirst().orElse(null);
    }

    public static String getItemName(String id, Context context) {
        if (instance == null) {
            initService(context);
        }

        for (RecycleViewItem it : items) {
            if (id.trim().equalsIgnoreCase(it.getId().trim())) {
                return it.getName();
            }
        }
        return " ";
    }

    public static RecycleViewItem getItemById(String id, Context context) {
        if (instance == null) {
            initService(context);
        }

        for (RecycleViewItem it : items) {
            if (id.trim().equalsIgnoreCase(it.getId().trim())) {
                return it;
            }
        }
            return new RecycleViewItem();
       }
    public static String getOtherNamesById(String label, Context context) {
        RecycleViewItem item = getItemById(label, context);
        return String.join(", ", item.getCommonNames()) + ", " + (String.join(", ", item.getScientificNames()));
    }


    public static List<SimilarSpeciesItem> getSimilarItems(String id, Context context) {
        if (instance == null) {
            initService(context);
        }

        return items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null).getConfusionItems();
    }
}
