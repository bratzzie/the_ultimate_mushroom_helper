package org.nau.diploma.chantarellecheck;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractItem {
    protected String id;
    protected String name;
    protected String season;
    protected String eatable;
    @JsonProperty("scientific_names")
    public List<String> scientificNames = new ArrayList<>();
    @JsonProperty("common_names")
    protected List<String> commonNames = new ArrayList<>();
    public String habitat;
    public String distribution;
    protected String cap;
    protected String tubes;
    protected String stem;
    protected String flesh;
    @JsonProperty("AMH")
    protected String AMH;
    @JsonProperty("ACW")
    protected String ACW;
    protected String smell;
    @JsonProperty("spore_print")
    protected String sporePrint;
    protected String frequency;

    protected AbstractItem() {
    }

    protected AbstractItem(String id, String name, String season, String eatable, List<String> scientificNames, List<String> commonNames, String habitat, String distribution, String cap, String tubes, String stem, String flesh, String AMH, String ACW, String smell, String sporePrint, String frequency) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.eatable = eatable;
        this.scientificNames = scientificNames;
        this.commonNames = commonNames;
        this.habitat = habitat;
        this.distribution = distribution;
        this.cap = cap;
        this.tubes = tubes;
        this.stem = stem;
        this.flesh = flesh;
        this.AMH = AMH;
        this.ACW = ACW;
        this.smell = smell;
        this.sporePrint = sporePrint;
        this.frequency = frequency;
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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEatable() {
        return eatable;
    }

    public void setEatable(String eatable) {
        this.eatable = eatable;
    }

    public List<String> getScientificNames() {
        return scientificNames;
    }

    public void setScientificNames(List<String> scientificNames) {
        this.scientificNames = scientificNames;
    }

    public List<String> getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(List<String> commonNames) {
        this.commonNames = commonNames;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getTubes() {
        return tubes;
    }

    public void setTubes(String tubes) {
        this.tubes = tubes;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getFlesh() {
        return flesh;
    }

    public void setFlesh(String flesh) {
        this.flesh = flesh;
    }

    public String getAMH() {
        return AMH;
    }

    public void setAMH(String AMH) {
        this.AMH = AMH;
    }

    public String getACW() {
        return ACW;
    }

    public void setACW(String ACW) {
        this.ACW = ACW;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getSporePrint() {
        return sporePrint;
    }

    public void setSporePrint(String sporePrint) {
        this.sporePrint = sporePrint;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
