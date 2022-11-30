package com.example.restorancepatsaji.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Berita {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("judul")
    @Expose
    private String judul;

    @SerializedName("isiberita")
    @Expose
    private String isi;

    @SerializedName("trending")
    @Expose
    private String trending;

    @SerializedName("category")
    @Expose
    private String category;

    @Override
    public String toString() {
        return "Berita{" +
                "email='" + email + '\'' +
                ", judul='" + judul + '\'' +
                ", isi='" + isi + '\'' +
                ", trending='" + trending + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTrending() {
        return trending;
    }

    public void setTrending(String trending) {
        this.trending = trending;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
