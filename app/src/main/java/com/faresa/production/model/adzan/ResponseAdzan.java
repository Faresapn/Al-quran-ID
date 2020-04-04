package com.faresa.production.model.adzan;

import com.google.gson.annotations.SerializedName;

public class ResponseAdzan {
    @SerializedName("jadwal")
    private AdzanData adzanData;

    public ResponseAdzan(AdzanData adzanData) {
        this.adzanData = adzanData;
    }

    public AdzanData getAdzanData() {
        return adzanData;
    }

    public void setAdzanData(AdzanData adzanData) {
        this.adzanData = adzanData;
    }
}
