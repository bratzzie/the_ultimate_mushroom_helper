package org.nau.diploma.chantarellecheck;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class UnprocessedItem {

    private String name;
    private String id;
    @JsonProperty("image_url")
    private String imageURL;
    private String description;
    private String season;
    private String location;
    private String eatable;
    private String amount;
    @JsonProperty("other_photos")
    private List<String> otherImagesURL;

    public UnprocessedItem(String name, String id, String imageURL, String description, String season, String location, String eatable, String amount, List<String> otherImagesURL) {
        this.name = name;
        this.id = id;
        this.imageURL = imageURL;
        this.description = description;
        this.season = season;
        this.location = location;
        this.eatable = eatable;
        this.amount = amount;
        this.otherImagesURL = otherImagesURL;
    }

    public UnprocessedItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEatable() {
        return eatable;
    }

    public void setEatable(String eatable) {
        this.eatable = eatable;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<String> getOtherImagesURL() {
        return otherImagesURL;
    }

    public void setOtherImagesURL(List<String> otherImagesURL) {
        this.otherImagesURL = otherImagesURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnprocessedItem that = (UnprocessedItem) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(imageURL, that.imageURL) && Objects.equals(description, that.description) && Objects.equals(season, that.season) && Objects.equals(location, that.location) && Objects.equals(eatable, that.eatable) && Objects.equals(amount, that.amount) && Objects.equals(otherImagesURL, that.otherImagesURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, imageURL, description, season, location, eatable, amount, otherImagesURL);
    }

    @Override
    public String toString() {
        return "UnprocessedItem{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                ", season='" + season + '\'' +
                ", location='" + location + '\'' +
                ", eatable='" + eatable + '\'' +
                ", amount='" + amount + '\'' +
                ", otherImagesURL=" + otherImagesURL +
                '}';
    }
}
