package com.sumitasharma.app.easyvocabulary.dictionaryutils;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pronunciation {

    @SerializedName("audioFile")
    @Expose
    private String audioFile;
    @SerializedName("dialects")
    @Expose
    private List<String> dialects = null;
    @SerializedName("phoneticNotation")
    @Expose
    private String phoneticNotation;
    @SerializedName("phoneticSpelling")
    @Expose
    private String phoneticSpelling;

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public List<String> getDialects() {
        return dialects;
    }

    public void setDialects(List<String> dialects) {
        this.dialects = dialects;
    }

    public String getPhoneticNotation() {
        return phoneticNotation;
    }

    public void setPhoneticNotation(String phoneticNotation) {
        this.phoneticNotation = phoneticNotation;
    }

    public String getPhoneticSpelling() {
        return phoneticSpelling;
    }

    public void setPhoneticSpelling(String phoneticSpelling) {
        this.phoneticSpelling = phoneticSpelling;
    }

}
