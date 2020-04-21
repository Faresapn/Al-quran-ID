package com.faresa.alquran.model.muslimsalat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAdzan {
    @SerializedName("items")
    private List<Jadwal> items;

    public ResponseAdzan(List<Jadwal> items) {
        this.items = items;
    }

    public List<Jadwal> getItems() {
        return items;
    }

    public void setItems(List<Jadwal> items) {
        this.items = items;
    }
}
