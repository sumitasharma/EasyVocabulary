package com.sumitasharma.app.easyvocabulary.dictionaryutils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example__ {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
