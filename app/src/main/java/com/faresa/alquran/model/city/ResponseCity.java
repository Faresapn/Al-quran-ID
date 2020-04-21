package com.faresa.alquran.model.city;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCity {
    @SerializedName("kota")
    private List<CityModel> cities;

    public ResponseCity(List<CityModel> cities) {
        this.cities = cities;
    }

    public List<CityModel> getCities() {
        return cities;
    }

    public void setCities(List<CityModel> cities) {
        this.cities = cities;
    }
}
