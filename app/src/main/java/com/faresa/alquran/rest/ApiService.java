package com.faresa.alquran.rest;

import com.faresa.alquran.model.AsmaulHuzna.ResponseDataAsmaul;
import com.faresa.alquran.model.QuranMurottal;
import com.faresa.alquran.model.Sholat.ResponseData;
import com.faresa.alquran.model.city.ResponseCity;
import com.faresa.alquran.model.muslimsalat.ResponseAdzan;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("sholat/format/json/kota")
    Observable<ResponseCity> getAllCity();

    @GET("sholat/format/json/kota")
    Call<ResponseCity> getCity();

    @GET("sholat/format/json/jadwal/kota/{kota}/tanggal/{tanggal}")
    Call<com.faresa.alquran.model.adzan.ResponseAdzan> getShalat(@Path("kota") String kota, @Path("tanggal") String tanggal);

    @GET("{kota}/.json")
    Call<ResponseAdzan> getJadwalShalat(@Path("kota") String kota);

    @GET("{kota}/.json")
    Observable<ResponseAdzan> getShalat(@Path("kota") String kota);

    @GET("data.json?print=pretty")
    Call<List<QuranMurottal>> getDataQuran();


    @GET("v1/timingsByCity")
    Call<ResponseData> getDataTimePray(
            @Query("city") String city,
            @Query("country") String country
    );

    @GET("asmaAlHusna")
    Call<ResponseDataAsmaul> getDataAsmaul();
}
