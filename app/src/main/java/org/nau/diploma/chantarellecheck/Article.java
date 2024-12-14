package org.nau.diploma.chantarellecheck;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Article {
    String category;
    String title;
    String description;
    @JsonProperty("image_id")
    String imageDrawableId;
    @JsonProperty("mushrooms_ids")
    List<String> items;


    public Article() {
    }

    public Article(String category, String title, String description, String imageDrawableId, List<String> items) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.imageDrawableId = imageDrawableId;
        this.items = items;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageDrawableId() {
        return imageDrawableId;
    }

    public void setImageDrawableId(String imageDrawableId) {
        this.imageDrawableId = imageDrawableId;
    }
}
