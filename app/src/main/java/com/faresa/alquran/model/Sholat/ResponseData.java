package com.faresa.alquran.model.Sholat;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseData {

    @SerializedName("code")
    @Expose
    private int number;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
