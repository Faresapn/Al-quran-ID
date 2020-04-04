package com.odoj.odoj.model;

/**
 * Created by imam-pc on 14/08/2016.
 */
public class QuranModel {

    private Integer id, databaseId, juzId, suratId, verseId;
    private String ayatText, translation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Integer databaseId) {
        this.databaseId = databaseId;
    }

    public Integer getJuzId() {
        return juzId;
    }

    public void setJuzId(Integer juzId) {
        this.juzId = juzId;
    }

    public Integer getSuratId() {
        return suratId;
    }

    public void setSuratId(Integer suratId) {
        this.suratId = suratId;
    }

    public Integer getVerseId() {
        return verseId;
    }

    public void setVerseId(Integer verseId) {
        this.verseId = verseId;
    }

    public String getAyatText() {
        return ayatText;
    }

    public void setAyatText(String ayatText) {
        this.ayatText = ayatText;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
