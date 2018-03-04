package com.example.sumitasharma.easyvocabulary.dictionaryutils;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entry {

    @SerializedName("etymologies")
    @Expose
    private List<String> etymologies = null;
    @SerializedName("grammaticalFeatures")
    @Expose
    private List<GrammaticalFeature> grammaticalFeatures = null;
    @SerializedName("homographNumber")
    @Expose
    private String homographNumber;
    @SerializedName("senses")
    @Expose
    private List<Sense> senses = null;

    public List<String> getEtymologies() {
        return etymologies;
    }

    public void setEtymologies(List<String> etymologies) {
        this.etymologies = etymologies;
    }

    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    public void setGrammaticalFeatures(List<GrammaticalFeature> grammaticalFeatures) {
        this.grammaticalFeatures = grammaticalFeatures;
    }

    public String getHomographNumber() {
        return homographNumber;
    }

    public void setHomographNumber(String homographNumber) {
        this.homographNumber = homographNumber;
    }

    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }

}
