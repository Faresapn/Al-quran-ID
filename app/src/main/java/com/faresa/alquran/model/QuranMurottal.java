package com.faresa.alquran.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuranMurottal implements Parcelable {
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("nomor")
    @Expose
    private String nomor;
    @SerializedName("audio")
    @Expose
    private String audio;

    protected QuranMurottal(Parcel in) {
        nama = in.readString();
        nomor = in.readString();
        audio = in.readString();
    }

    public static final Creator<QuranMurottal> CREATOR = new Creator<QuranMurottal>() {
        @Override
        public QuranMurottal createFromParcel(Parcel in) {
            return new QuranMurottal(in);
        }

        @Override
        public QuranMurottal[] newArray(int size) {
            return new QuranMurottal[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(nomor);
        dest.writeString(audio);
    }
}
