package com.ahmetc.yazimkurallari.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ahmetc.yazimkurallari.Models.Kategoriler;
import java.util.ArrayList;

public class KategorilerDao {
    public ArrayList<Kategoriler> tumKategoriler(DatabaseHelper dbh) {
        ArrayList<Kategoriler> sonuc = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM kategoriler", null);
        while (c.moveToNext()) {
            Kategoriler kategori = new Kategoriler(
                    c.getInt(c.getColumnIndex("kategori_id")),
                    c.getString(c.getColumnIndex("kategori_ad"))
            );
            sonuc.add(kategori);
        }
        return sonuc;
    }
    public ArrayList<String> tumKategoriAdlari(DatabaseHelper dbh) {
        ArrayList<String> sonuc = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM kategoriler", null);
        while (c.moveToNext()) {
            String kategori_ad = c.getString(c.getColumnIndex("kategori_ad"));
            sonuc.add(kategori_ad);
        }
        return sonuc;
    }
    public int kategoriSayisi(DatabaseHelper dbh) {
        int sonuc = 0;
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT count(*) as sonuc FROM kategoriler", null);
        while (c.moveToNext()) {
            sonuc = c.getInt(c.getColumnIndex("sonuc"));
        }
        return sonuc;
    }
}
