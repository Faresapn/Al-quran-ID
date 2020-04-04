package com.odoj.odoj.rest;

import com.odoj.odoj.model.city.ResponseCity;
import com.odoj.odoj.model.muslimsalat.ResponseAdzan;

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
    Call<com.odoj.odoj.model.adzan.ResponseAdzan> getShalat(@Path("kota") String kota, @Path("tanggal") String tanggal);

    @GET("{kota}/.json")
    Call<ResponseAdzan> getJadwalShalat(@Path("kota") String kota);

    @GET("{kota}/.json")
    Observable<ResponseAdzan> getShalat(@Path("kota") String kota);
}
