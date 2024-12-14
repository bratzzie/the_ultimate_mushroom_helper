package org.nau.diploma.chantarellecheck;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimilarSpeciesItem {
    private String id;
    private String name;
    private String description;
    @JsonProperty("eatable")
    private String edibility;

    public SimilarSpeciesItem() {
    }

    public SimilarSpeciesItem(String id, String name, String description, String edibility) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.edibility = edibility;
    }

    public String getEdibility() {
        return edibility;
    }

    public void setEdibility(String edibility) {
        this.edibility = edibility;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
