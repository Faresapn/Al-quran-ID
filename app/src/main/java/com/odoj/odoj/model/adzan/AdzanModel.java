package com.odoj.odoj.model.adzan;

import com.google.gson.annotations.SerializedName;

public class AdzanModel {
    private int id;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("subuh")
    private String subuh;

    @SerializedName("dzuhur")
    private String dzuhur;

    @SerializedName("ashar")
    private String ashar;

    @SerializedName("maghrib")
    private String maghrib;

    @SerializedName("isya")
    private String isya;

    public AdzanModel() {

    }

    public AdzanModel(int id, String tanggal, String subuh, String dzuhur, String ashar, String maghrib, String isya) {
        this.id = id;
        this.tanggal = tanggal;
        this.subuh = subuh;
        this.dzuhur = dzuhur;
        this.ashar = ashar;
        this.maghrib = maghrib;
        this.isya = isya;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSubuh() {
        return subuh;
    }

    public void setSubuh(String subuh) {
        this.subuh = subuh;
    }

    public String getDzuhur() {
        return dzuhur;
    }

    public void setDzuhur(String dzuhur) {
        this.dzuhur = dzuhur;
    }

    public String getAshar() {
        return ashar;
    }

    public void setAshar(String ashar) {
        this.ashar = ashar;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getIsya() {
        return isya;
    }

    public void setIsya(String isya) {
        this.isya = isya;
    }
}
