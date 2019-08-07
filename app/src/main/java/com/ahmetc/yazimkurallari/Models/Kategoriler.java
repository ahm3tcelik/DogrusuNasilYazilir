package com.ahmetc.yazimkurallari.Models;

import java.io.Serializable;

public class Kategoriler implements Serializable {
    private int kategori_id;
    private String kategori_ad;

    public Kategoriler() {}

    public Kategoriler(int kategori_id, String kategori_ad) {
        this.kategori_id = kategori_id;
        this.kategori_ad = kategori_ad;
    }

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }

    public String getKategori_ad() {
        return kategori_ad;
    }

    public void setKategori_ad(String kategori_ad) {
        this.kategori_ad = kategori_ad;
    }
}
