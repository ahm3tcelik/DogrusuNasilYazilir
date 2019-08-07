package com.ahmetc.yazimkurallari.Database;

import com.ahmetc.yazimkurallari.Models.Kategoriler;
import com.ahmetc.yazimkurallari.Models.Sorular;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class SorularDao {
    public ArrayList<Sorular> soruGetir(DatabaseHelper dbh, int kategori_id, String aranan, boolean isRandom) {
        ArrayList<Sorular> sonuc = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        String find_query = (aranan.equals("#EMPTY#")) ? " " : " and soru_dogru like '%" + aranan + "%'";
        String random = (isRandom) ? " ORDER BY RANDOM()" : " ";
        Cursor c;
        if(kategori_id == 0) c = sqLiteDatabase.rawQuery("SELECT * FROM sorular, kategoriler where sorular.kategori_id = kategoriler.kategori_id" + find_query + random, null);
        else c = sqLiteDatabase.rawQuery("SELECT * FROM sorular, kategoriler where sorular.kategori_id = kategoriler.kategori_id and kategoriler.kategori_id = " + kategori_id + find_query + random, null);
        while (c.moveToNext()) {
            Kategoriler kategori = new Kategoriler(
                    c.getInt(c.getColumnIndex("kategori_id")),
                    c.getString(c.getColumnIndex("kategori_ad"))
            );
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
        sqLiteDatabase.close();
        return sonuc;
    }
    public ArrayList<Sorular> yanlisYapianSoruGetir(DatabaseHelper dbh, int kategori_id, String aranan, boolean isRandom) {
        ArrayList<Sorular> sonuc =  new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        String find_query = (aranan.equals("#EMPTY#")) ? " " : " and soru_dogru like '%" + aranan + "%'";
        String random = (isRandom) ? " ORDER BY RANDOM()" : "";
        Cursor c;
        if(kategori_id == 0) c = sqLiteDatabase.rawQuery("SELECT * FROM sorular, kategoriler where sorular.kategori_id = kategoriler.kategori_id and soru_yanlisyapilan > 0" + find_query + random, null);
        else c = sqLiteDatabase.rawQuery("SELECT * FROM sorular, kategoriler where sorular.kategori_id = kategoriler.kategori_id and soru_yanlisyapilan > 0 and kategoriler.kategori_id = " + kategori_id + find_query + random, null);
        while(c.moveToNext()) {
            Kategoriler kategori = new Kategoriler(
                    c.getInt(c.getColumnIndex("kategori_id")),
                    c.getString(c.getColumnIndex("kategori_ad"))
            );
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
        sqLiteDatabase.close();
        return sonuc;
    }
    public ArrayList<Sorular> isaretliSoruGetir(DatabaseHelper dbh, int kategori_id, String aranan, boolean isRandom) {
        ArrayList<Sorular> response =  new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        String find_query = (aranan.equals("#EMPTY#")) ? " " : " and soru_dogru like '%" + aranan + "%'";
        String random = (isRandom) ? " ORDER BY RANDOM()" : "";
        Cursor c;
        if(kategori_id == 0) c = sqLiteDatabase.rawQuery("SELECT * FROM sorular, kategoriler where sorular.kategori_id = kategoriler.kategori_id and soru_isaret > 0" + find_query + random, null);
        else c = sqLiteDatabase.rawQuery("SELECT * FROM sorular, kategoriler where soru_isaret > 0 and sorular.kategori_id = kategoriler.kategori_id and sorular.kategori_id = " + kategori_id + find_query + random, null);
        while(c.moveToNext()) {
            Kategoriler kategori = new Kategoriler(
                    c.getInt(c.getColumnIndex("kategori_id")),
                    c.getString(c.getColumnIndex("kategori_ad"))
            );
            Sorular soru = new Sorular(
                    c.getInt(c.getColumnIndex("soru_id")),
                    c.getString(c.getColumnIndex("soru_dogru")),
                    c.getString(c.getColumnIndex("soru_yanlis")),
                    c.getInt(c.getColumnIndex("soru_yanlisyapilan")),
                    c.getInt(c.getColumnIndex("soru_dogruyapilan")),
                    c.getInt(c.getColumnIndex("soru_isaret")),
                    kategori);
            response.add(soru);
        }
        sqLiteDatabase.close();
        return response;
    }
    public ArrayList<Sorular> isaretliYanlisSoruGetir(DatabaseHelper dbh, int kategori_id, String aranan, boolean isRandom) {
        ArrayList<Sorular> sonuc =  new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        String find_query = (aranan.equals("#EMPTY#")) ? " " : " and soru_dogru like '%" + aranan + "%'";
        String random = (isRandom) ? " ORDER BY RANDOM()" : "";
        Cursor c;
        if(kategori_id == 0) c = sqLiteDatabase.rawQuery("SELECT * FROM sorular, kategoriler where sorular.kategori_id = kategoriler.kategori_id and soru_isaret > 0 and soru_yanlisyapilan > 0" + find_query + random, null);
        else c = sqLiteDatabase.rawQuery("SELECT * FROM sorular, kategoriler where sorular.kategori_id = kategoriler.kategori_id and soru_isaret > 0 and soru_yanlisyapilan > 0 and kategoriler.kategori_id = " + kategori_id + find_query + random, null);
        while(c.moveToNext()) {
            Kategoriler kategori = new Kategoriler(
                    c.getInt(c.getColumnIndex("kategori_id")),
                    c.getString(c.getColumnIndex("kategori_ad"))
            );
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
    public void yanlisYapilanEkle(DatabaseHelper dbh, int soru_id, int miktar) { // 1.soru 3 defa yanlış yapılmış, 1 arttır.
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("soru_yanlisyapilan", ++miktar);
        sqLiteDatabase.update("sorular", contentValues,"soru_id = ?", new String[]{String.valueOf(soru_id)});
        sqLiteDatabase.close();
    }
    public void dogruYapilanEkle(DatabaseHelper dbh, int soru_id, int miktar) { // 1.soru 3 defa doğru yapılmış, 1 arttır.
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("soru_dogruyapilan", ++miktar);
        sqLiteDatabase.update("sorular", contentValues,"soru_id = ?", new String[]{String.valueOf(soru_id)});
        sqLiteDatabase.close();
    }
    public int soruSayisi(DatabaseHelper dbh) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT count(*) as sonuc FROM sorular", null);
        int sonuc = 0;
        while (c.moveToNext()) {
            sonuc = c.getInt(c.getColumnIndex("sonuc"));
        }
        sqLiteDatabase.close();
        return sonuc;
    }
    public int isaretliSayisi(DatabaseHelper dbh) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT count(*) as sonuc FROM sorular WHERE soru_isaret > 0", null);
        int sonuc = 0;
        while (c.moveToNext()) {
            sonuc = c.getInt(c.getColumnIndex("sonuc"));
        }
        sqLiteDatabase.close();
        return sonuc;
    }
    public void isaretEkleKaldir(DatabaseHelper dbh, int soru_id, int value) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("soru_isaret", value);
        sqLiteDatabase.update("sorular", contentValues,"soru_id = ?", new String[]{String.valueOf(soru_id)});
        sqLiteDatabase.close();
    }
    public void ozelSoruYukle(DatabaseHelper dbh, ArrayList<Sorular> sorularArrayList) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        ContentValues valuesSoru = new ContentValues();
        for(Sorular soru : sorularArrayList) {
            valuesSoru.put("soru_dogru", soru.getSoru_dogru());
            valuesSoru.put("soru_yanlis", soru.getSoru_yanlis());
            valuesSoru.put("soru_yanlisyapilan", soru.getSoru_yanlisyapilan());
            valuesSoru.put("soru_dogruyapilan", soru.getSoru_dogruyapilan());
            valuesSoru.put("soru_isaret", soru.getsoru_isaret());
            valuesSoru.put("kategori_id", soru.getKategori().getKategori_id());
            sqLiteDatabase.insertOrThrow("sorular", null, valuesSoru);
        }
        sqLiteDatabase.close();
    }
    public String[] notificationSoru(DatabaseHelper dbh) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT soru_dogru, soru_yanlis FROM sorular ORDER BY RANDOM() LIMIT 1", null);
        String[] sonuc = new String[2];
        while (c.moveToNext()) {
            sonuc[0] = (c.getString(c.getColumnIndex("soru_dogru")));
            sonuc[1] = (c.getString(c.getColumnIndex("soru_yanlis")));
        }
        sqLiteDatabase.close();
        return sonuc;
    }
    public void sifirlaTumSoru(DatabaseHelper dbh) {
        SQLiteDatabase sqLiteDatabase = dbh.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE sorular set soru_dogruyapilan = 0, soru_yanlisyapilan = 0, soru_isaret = 0");
        sqLiteDatabase.execSQL("UPDATE ozel_sorular set soru_dogruyapilan = 0, soru_yanlisyapilan = 0, soru_isaret = 0");
        sqLiteDatabase.close();
    }
}
