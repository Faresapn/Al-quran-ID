package com.faresa.alquran.model.adzan;

import com.google.gson.annotations.SerializedName;

public class AdzanData {
    @SerializedName("data")
    private AdzanModel adzanModel;

    public AdzanData(AdzanModel adzanModel) {
        this.adzanModel = adzanModel;
    }

    public AdzanModel getAdzanModel() {
        return adzanModel;
    }

    public void setAdzanModel(AdzanModel adzanModel) {
        this.adzanModel = adzanModel;
    }
}
