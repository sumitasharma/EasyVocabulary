package com.example.sumitasharma.easyvocabulary.dictionaryutils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sense {

    @SerializedName("definitions")
    @Expose
    private List<String> definitions = null;
    @SerializedName("domains")
    @Expose
    private List<String> domains = null;
    @SerializedName("examples")
    @Expose
    private List<Example_> examples = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("registers")
    @Expose
    private List<String> registers = null;
    @SerializedName("subsenses")
    @Expose
    private List<Subsense> subsenses = null;
    @SerializedName("regions")
    @Expose
    private List<String> regions = null;

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

    public List<Example_> getExamples() {
        return examples;
    }

    public void setExamples(List<Example_> examples) {
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

    public List<Subsense> getSubsenses() {
        return subsenses;
    }

    public void setSubsenses(List<Subsense> subsenses) {
        this.subsenses = subsenses;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

}
