package com.odoj.odoj.model;

public class SurahModel {
    private Integer id;
    private String surahText;

    public SurahModel() {

    }

    public SurahModel(Integer id, String surahText) {
        this.id = id;
        this.surahText = surahText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurahText() {
        return surahText;
    }

    public void setSurahText(String surahText) {
        this.surahText = surahText;
    }
}
