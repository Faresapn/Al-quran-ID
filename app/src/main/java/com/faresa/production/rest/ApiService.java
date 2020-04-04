package com.faresa.production.rest;

import com.faresa.production.model.city.ResponseCity;
import com.faresa.production.model.muslimsalat.ResponseAdzan;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("sholat/format/json/kota")
    Observable<ResponseCity> getAllCity();

    @GET("sholat/format/json/kota")
    Call<ResponseCity> getCity();

    @GET("sholat/format/json/jadwal/kota/{kota}/tanggal/{tanggal}")
    Call<com.faresa.production.model.adzan.ResponseAdzan> getShalat(@Path("kota") String kota, @Path("tanggal") String tanggal);

    @GET("{kota}/.json")
    Call<ResponseAdzan> getJadwalShalat(@Path("kota") String kota);

    @GET("{kota}/.json")
    Observable<ResponseAdzan> getShalat(@Path("kota") String kota);
}
