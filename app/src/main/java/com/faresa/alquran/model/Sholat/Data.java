package com.faresa.alquran.model.Sholat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {
    @SerializedName(("timings"))
    @Expose
    private Timings timings;

    public Timings getTimings() {
        return timings;
    }

    public void setTimings(Timings timings) {
        this.timings = timings;
    }
}
