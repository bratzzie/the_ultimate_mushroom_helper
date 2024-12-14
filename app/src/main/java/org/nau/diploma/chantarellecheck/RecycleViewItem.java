package org.nau.diploma.chantarellecheck;

import android.graphics.Bitmap;

import java.util.List;

public class RecycleViewItem extends AbstractItem {
    private String imageURL;
    private Bitmap thumbnail;
    private List<SimilarSpeciesItem> confusionItems;

    public RecycleViewItem() {
        super();
    }

    public RecycleViewItem(String id, String name, String season, String eatable, List<String> scientificNames, List<String> commonNames, String habitat, String distribution, String cap, String tubes, String stem, String flesh, String AMH, String ACW, String smell, String sporePrint, String frequency, String imageURL, Bitmap thumbnail) {
        super(id, name, season, eatable, scientificNames, commonNames, habitat, distribution, cap, tubes, stem, flesh, AMH, ACW, smell, sporePrint, frequency);
        this.imageURL = imageURL;
        this.thumbnail = thumbnail;
    }

    public RecycleViewItem(String id, String name, String season, String eatable, List<String> scientificNames, List<String> commonNames, String habitat, String distribution, String cap, String tubes, String stem, String flesh, String AMH, String ACW, String smell, String sporePrint, String frequency, String imageURL, Bitmap thumbnail, List<SimilarSpeciesItem> confusionItems) {
        super(id, name, season, eatable, scientificNames, commonNames, habitat, distribution, cap, tubes, stem, flesh, AMH, ACW, smell, sporePrint, frequency);
        this.imageURL = imageURL;
        this.thumbnail = thumbnail;
        this.confusionItems = confusionItems;
    }

    public List<SimilarSpeciesItem> getConfusionItems() {
        return confusionItems;
    }

    public void setConfusionItems(List<SimilarSpeciesItem> confusionItems) {
        this.confusionItems = confusionItems;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }



}
