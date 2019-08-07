package com.ahmetc.yazimkurallari.Models;

import java.io.Serializable;

public class Sorular implements Serializable {
    private int soru_id;
    private String soru_dogru;
    private String soru_yanlis;
    private int soru_yanlisyapilan;
    private int soru_dogruyapilan;
    private int soru_isaret;
    private Kategoriler kategori;

    public Sorular() {}

    public Sorular(int soru_id, String soru_dogru, String soru_yanlis, int soru_yanlisyapilan, int soru_dogruyapilan, int soru_isaret, Kategoriler kategori) {
        this.soru_id = soru_id;
        this.soru_dogru = soru_dogru;
        this.soru_yanlis = soru_yanlis;
        this.soru_yanlisyapilan = soru_yanlisyapilan;
        this.soru_dogruyapilan = soru_dogruyapilan;
        this.soru_isaret = soru_isaret;
        this.kategori = kategori;
    }

    public int getSoru_id() {
        return soru_id;
    }

    public void setSoru_id(int soru_id) {
        this.soru_id = soru_id;
    }

    public String getSoru_dogru() {
        return soru_dogru;
    }

    public void setSoru_dogru(String soru_dogru) {
        this.soru_dogru = soru_dogru;
    }

    public String getSoru_yanlis() {
        return soru_yanlis;
    }

    public void setSoru_yanlis(String soru_yanlis) {
        this.soru_yanlis = soru_yanlis;
    }

    public int getSoru_yanlisyapilan() {
        return soru_yanlisyapilan;
    }

    public void setSoru_yanlisyapilan(int soru_yanlisyapilan) {
        this.soru_yanlisyapilan = soru_yanlisyapilan;
    }
    public int getSoru_dogruyapilan() {
        return soru_dogruyapilan;
    }

    public void setSoru_dogruyapilan(int soru_dogruyapilan) {
        this.soru_dogruyapilan = soru_dogruyapilan;
    }

    public int getsoru_isaret() {
        return soru_isaret;
    }

    public void setsoru_isaret(int soru_isaret) {
        this.soru_isaret = soru_isaret;
    }

    public Kategoriler getKategori() {
        return kategori;
    }

    public void setKategori(Kategoriler kategori) {
        this.kategori = kategori;
    }
}
