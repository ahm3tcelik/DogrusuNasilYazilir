package com.ahmetc.yazimkurallari;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.ahmetc.yazimkurallari.Adapters.OzelSoruAdapter;
import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.KategorilerDao;
import com.ahmetc.yazimkurallari.Database.OzelSorularDao;
import com.ahmetc.yazimkurallari.Models.Sorular;
import java.util.ArrayList;

public class OzelSorularActivity extends AppCompatActivity {
    private Toolbar toolbarOzelSorular;
    private RecyclerView ozelSorularRv;
    private FloatingActionButton soruEkleFab;
    private OzelSoruAdapter ozelSoruAdapter;
    private ArrayList<Sorular> sorularArrayList;
    private DatabaseHelper dbh;
    private Spinner scanKategori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozel_sorular);
        init();

        //Toolbar
        toolbarOzelSorular.setTitle("Özel Sorular");
        setSupportActionBar(toolbarOzelSorular);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // RecyclerView
        ozelSorularRv.setHasFixedSize(true);
        ozelSorularRv.setItemAnimator(new DefaultItemAnimator());
        ozelSorularRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ozelSorularRv.setLayoutManager(new LinearLayoutManager(this));
        ozelSoruAdapter = new OzelSoruAdapter(this, sorularArrayList);
        ozelSorularRv.setAdapter(ozelSoruAdapter);

        listeyiYenile();

        soruEkleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(OzelSorularActivity.this);
                dialog.setContentView(R.layout.soru_ekle);
                final EditText scanSoruDogru = dialog.findViewById(R.id.scanSoruDogru);
                final EditText scanSoruYanlis = dialog.findViewById(R.id.scanSoruYanlis);
                Button soru_onayla = dialog.findViewById(R.id.soru_onayla);
                Button soru_vazgec = dialog.findViewById(R.id.soru_vazgec);
                scanKategori = dialog.findViewById(R.id.scanKategori);

                //Kategoriler Spinner

                ArrayList<String> kategoriAdlari = new KategorilerDao().tumKategoriAdlari(dbh);
                ArrayAdapter<String> kategoriAdapter = new ArrayAdapter<>(OzelSorularActivity.this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        kategoriAdlari);
                scanKategori.setAdapter(kategoriAdapter);
                soru_vazgec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                soru_onayla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dogru = null, yanlis = null;
                        try {
                            dogru = scanSoruDogru.getText().toString().trim();
                            yanlis = scanSoruYanlis.getText().toString().trim();
                        } catch (NullPointerException e) {}
                        if (!dogru.isEmpty() && !yanlis.isEmpty()) {
                            new OzelSorularDao().ekleOzelSoru(dbh, dogru, yanlis,scanKategori.getSelectedItemPosition() + 1);
                            dialog.dismiss();
                            Toast.makeText(OzelSorularActivity.this, "Soru başarıyla eklendi!", Toast.LENGTH_SHORT).show();
                            listeyiYenile();
                        }
                        else {
                            Toast.makeText(OzelSorularActivity.this, "Tüm bilgileri doldurun!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void listeyiYenile() {
        sorularArrayList = new OzelSorularDao().ozelSorular(dbh);
        ozelSoruAdapter.refresh(sorularArrayList);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void init() {
        toolbarOzelSorular = findViewById(R.id.toolbarOzelSorular);
        ozelSorularRv = findViewById(R.id.ozelSorularRv);
        soruEkleFab = findViewById(R.id.soruEkleFab);
        sorularArrayList = new ArrayList<>();
        dbh = new DatabaseHelper(this);
    }
}
