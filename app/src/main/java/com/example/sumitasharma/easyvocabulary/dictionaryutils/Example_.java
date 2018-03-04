package com.example.sumitasharma.easyvocabulary.dictionaryutils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example_ {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("registers")
    @Expose
    private List<String> registers = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getRegisters() {
        return registers;
    }

    public void setRegisters(List<String> registers) {
        this.registers = registers;
    }

}
