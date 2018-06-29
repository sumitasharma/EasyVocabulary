package com.example.sumitasharma.easyvocabulary.dictionaryutils;

public class WordsAndMeaning {
    String word;
    String wordMeaning;
    String wordLevel;

    public WordsAndMeaning() {

    }

    public WordsAndMeaning(String word, String meaning, String wordLevel) {
        this.word = word;
        this.wordMeaning = meaning;
        this.wordLevel = wordLevel;
    }

    public String getWordLevel() {
        return wordLevel;
    }

    public void setWordLevel(String wordLevel) {
        this.wordLevel = wordLevel;
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


}
