package com.sumitasharma.app.easyvocabulary.dictionaryutils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subsense {

    @SerializedName("definitions")
    @Expose
    private List<String> definitions = null;
    @SerializedName("domains")
    @Expose
    private List<String> domains = null;
    @SerializedName("examples")
    @Expose
    private List<Example__> examples = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("registers")
    @Expose
    private List<String> registers = null;
    @SerializedName("notes")
    @Expose
    private List<Note> notes = null;

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<Example__> getExamples() {
        return examples;
    }

    public void setExamples(List<Example__> examples) {
        this.examples = examples;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRegisters() {
        return registers;
    }

    public void setRegisters(List<String> registers) {
        this.registers = registers;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}
