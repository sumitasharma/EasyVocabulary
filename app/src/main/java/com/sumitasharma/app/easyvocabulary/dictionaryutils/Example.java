package com.sumitasharma.app.easyvocabulary.dictionaryutils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("example")
    @Expose
    private String example;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

}