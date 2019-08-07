package com.ahmetc.yazimkurallari.Database;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ahmetc.yazimkurallari.Models.Kategoriler;
import com.ahmetc.yazimkurallari.Models.Sorular;
import java.util.ArrayList;

public class OzelSorularDao {

    public ArrayList<Sorular> ozelSorular(DatabaseHelper dbh) {
        ArrayList<Sorular> sonuc = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbh.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM ozel_sorular, kategoriler WHERE kategoriler.kategori_id = ozel_sorular.kategori_id", null);
        while (c.moveToNext()) {
            Kategoriler kategori = new Kategoriler(
                    c.getInt(c.getColumnIndex("kategori_id")),
                    c.getString(c.getColumnIndex("kategori_ad")));
            Sorular soru = new Sorular(
                    c.getInt(c.getColumnIndex("soru_id")),
                    c.getString(c.getColumnIndex("soru_dogru")),
                    c.getString(c.getColumnIndex("soru_yanlis")),
                    c.getInt(c.getColumnIndex("soru_yanlisyapilan")),
                    c.getInt(c.getColumnIndex("soru_dogruyapilan")),
                    c.getInt(c.getColumnIndex("soru_isaret")),
                    kategori);
            sonuc.add(soru);
        }
        return sonuc;
    }

    public void ekleOzelSoru(DatabaseHelper dbh, String soru_dogru, String soru_yanlis, int kategori_id) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();

        sqLiteDatabase.execSQL("INSERT INTO sorular(soru_dogru,soru_yanlis,soru_yanlisyapilan, soru_dogruyapilan, soru_isaret, kategori_id) VALUES ('"
                + soru_dogru + "','" + soru_yanlis + "',0,0,0," + kategori_id + ")");
        sqLiteDatabase.execSQL("INSERT INTO ozel_sorular(soru_dogru,soru_yanlis,soru_yanlisyapilan, soru_dogruyapilan, soru_isaret, kategori_id) VALUES ('"
                + soru_dogru + "','" + soru_yanlis + "',0,0,0," + kategori_id + ")");
        sqLiteDatabase.close();
    }

    public void kaldirOzelSoru(DatabaseHelper dbh, int soru_id) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT soru_dogru FROM ozel_sorular WHERE soru_id = ?",
                new String[]{String.valueOf(soru_id)});
        String soru_dogru = "SA";
        while (c.moveToNext()) {
            soru_dogru = c.getString(c.getColumnIndex("soru_dogru"));
        }
        sqLiteDatabase.delete("ozel_sorular", "soru_id = ?", new String[]{String.valueOf(soru_id)});
        sqLiteDatabase.delete("sorular", "soru_dogru = ?", new String[]{soru_dogru});
        sqLiteDatabase.close();
    }

    public ArrayList<Sorular> ozelSoruGonder(DatabaseHelper dbh) {
        ArrayList<Sorular> sonuc = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM ozel_sorular, kategoriler WHERE kategoriler.kategori_id = ozel_sorular.kategori_id", null);
        while (c.moveToNext()) {
            Kategoriler kategori = new Kategoriler(
                    c.getInt(c.getColumnIndex("kategori_id")),
                    c.getString(c.getColumnIndex("kategori_id")));
            Sorular soru = new Sorular(
                    c.getInt(c.getColumnIndex("soru_id")),
                    c.getString(c.getColumnIndex("soru_dogru")),
                    c.getString(c.getColumnIndex("soru_yanlis")),
                    c.getInt(c.getColumnIndex("soru_yanlisyapilan")),
                    c.getInt(c.getColumnIndex("soru_dogruyapilan")),
                    c.getInt(c.getColumnIndex("soru_isaret")),
                    kategori);
            sonuc.add(soru);
        }
        return sonuc;
    }

    public int ozelSoruSayisi(DatabaseHelper dbh) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT count(*) as sonuc FROM ozel_sorular", null);
        int sonuc = 0;
        while (c.moveToNext()) {
            sonuc = c.getInt(c.getColumnIndex("sonuc"));
        }
        return sonuc;
    }
}
