package com.faresa.production.model;

import com.faresa.production.model.muslimsalat.ResponseAdzan;
import com.faresa.production.model.city.ResponseCity;

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
