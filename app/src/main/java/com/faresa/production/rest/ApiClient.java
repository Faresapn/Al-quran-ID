package com.faresa.production.rest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL_1 = "https://api.banghasan.com/";
    public static final String BASE_URL_2 = "https://muslimsalat.com/";

    public static Retrofit retrofit = null;

    public static Retrofit getClient(Boolean first) {
        if (first) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
