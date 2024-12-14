package org.nau.diploma.chantarellecheck;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SerializableItem extends AbstractItem{
    @JsonProperty("image_url")
    private String imageURL;
    @JsonProperty("confusion_items")
    private List<SimilarSpeciesItem> confusionItems;

    public SerializableItem() {
        super();
    }

    public SerializableItem(String id, String name, String season, String eatable, List<String> scientificNames, List<String> commonNames, String habitat, String distribution, String cap, String tubes, String stem, String flesh, String AMH, String ACW, String smell, String sporePrint, String frequency, String imageURL, List<SimilarSpeciesItem> confusionItems) {
        super(id, name, season, eatable, scientificNames, commonNames, habitat, distribution, cap, tubes, stem, flesh, AMH, ACW, smell, sporePrint, frequency);
        this.imageURL = imageURL;
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


    @Override
    public String toString() {
        return "SerializableItem{" +
                "imageURL='" + imageURL + '\'' +
                ", confusionItems=" + confusionItems +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", season='" + season + '\'' +
                ", eatable='" + eatable + '\'' +
                ", scientificNames=" + scientificNames +
                ", commonNames=" + commonNames +
                ", habitat='" + habitat + '\'' +
                ", distribution='" + distribution + '\'' +
                ", cap='" + cap + '\'' +
                ", tubes='" + tubes + '\'' +
                ", stem='" + stem + '\'' +
                ", flesh='" + flesh + '\'' +
                ", AMH='" + AMH + '\'' +
                ", ACW='" + ACW + '\'' +
                ", smell='" + smell + '\'' +
                ", sporePrint='" + sporePrint + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
