package com.odoj.odoj.model.city;

import com.google.gson.annotations.SerializedName;

public class CityModel {
    private transient Integer id;

    @SerializedName("id")
    private String cityId;

    @SerializedName("nama")
    private String cityText;

    public CityModel() {

    }

    public CityModel(Integer id, String cityId, String cityText) {
        this.id = id;
        this.cityId = cityId;
        this.cityText = cityText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }
}
