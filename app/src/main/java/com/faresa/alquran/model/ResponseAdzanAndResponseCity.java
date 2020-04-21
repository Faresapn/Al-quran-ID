package com.faresa.alquran.model;

import com.faresa.alquran.model.muslimsalat.ResponseAdzan;
import com.faresa.alquran.model.city.ResponseCity;

public class ResponseAdzanAndResponseCity {
    private ResponseAdzan mResponseAdzan;
    private ResponseCity mResponseCity;

    public ResponseAdzanAndResponseCity(ResponseAdzan responseAdzan, ResponseCity responseCity) {
        mResponseAdzan = responseAdzan;
        mResponseCity = responseCity;
    }

    public ResponseAdzan getResponseAdzan() {
        return mResponseAdzan;
    }

    public void setResponseAdzan(ResponseAdzan responseAdzan) {
        mResponseAdzan = responseAdzan;
    }

    public ResponseCity getResponseCity() {
        return mResponseCity;
    }

    public void setResponseCity(ResponseCity responseCity) {
        mResponseCity = responseCity;
    }
}
