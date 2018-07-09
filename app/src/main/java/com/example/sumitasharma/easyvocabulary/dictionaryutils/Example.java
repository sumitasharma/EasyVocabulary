package com.example.sumitasharma.easyvocabulary.dictionaryutils;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("defs")
    @Expose
    private List<String> defs = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<String> getDefs() {
        return defs;
    }

    public void setDefs(List<String> defs) {
        this.defs = defs;
    }

}