package com.sumitasharma.app.easyvocabulary.dictionaryutils;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LexicalEntry {

    @SerializedName("entries")
    @Expose
    private List<Entry> entries = null;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("lexicalCategory")
    @Expose
    private String lexicalCategory;
    @SerializedName("pronunciations")
    @Expose
    private List<Pronunciation> pronunciations = null;
    @SerializedName("text")
    @Expose
    private String text;

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLexicalCategory() {
        return lexicalCategory;
    }

    public void setLexicalCategory(String lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
    }

    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    public void setPronunciations(List<Pronunciation> pronunciations) {
        this.pronunciations = pronunciations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
