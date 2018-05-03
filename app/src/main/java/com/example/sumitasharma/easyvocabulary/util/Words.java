package com.example.sumitasharma.easyvocabulary.util;


public class Words {


    private String wordId;
    private String word;
    private String wordMeaning;
    private String wordLevel;
    private String lastUpdated;
    private Boolean wordPracticed;

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordMeaning() {
        return wordMeaning;
    }

    public void setWordMeaning(String wordMeaning) {
        this.wordMeaning = wordMeaning;
    }

    public String getWordLevel() {
        return wordLevel;
    }

    public void setWordLevel(String wordLevel) {
        this.wordLevel = wordLevel;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean getWordPracticed() {
        return wordPracticed;
    }

    public void setWordPracticed(Boolean wordPracticed) {
        this.wordPracticed = wordPracticed;
    }
}
