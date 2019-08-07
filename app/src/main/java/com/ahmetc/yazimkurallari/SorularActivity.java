package com.ahmetc.yazimkurallari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.ahmetc.yazimkurallari.Adapters.SorularAdapter;
import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.KategorilerDao;
import com.ahmetc.yazimkurallari.Database.SorularDao;
import com.ahmetc.yazimkurallari.Models.Sorular;
import java.util.ArrayList;

public class SorularActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbarSorular;
    private Spinner spinnerKategori;
    private Spinner spinnerSoruTip;
    private RecyclerView recyclerViewSorular;
    private SorularAdapter sorularAdapter;
    private ArrayAdapter<String> kategoriAdapter;
    private ArrayAdapter<String> soruTipAdapter;
    private ArrayList<Sorular> sorularArraylist;
    private ArrayList<String> kategoriAdlari;
    private ArrayList<String> soruTipleri;
    private DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorular);
        init();

        //Toolbar
        toolbarSorular.setTitle("Tüm Sorular");
        setSupportActionBar(toolbarSorular);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // RecyclerView
        recyclerViewSorular.setHasFixedSize(true);
        recyclerViewSorular.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSorular.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        sorularAdapter = new SorularAdapter(this, sorularArraylist);

        // Soru Tipleri Spinner
        soruTipleri.add("Tüm Sorular");soruTipleri.add("Sadece Yanlış Sorular");
        soruTipleri.add("Sadece İşaretli Sorular");soruTipleri.add("İşaretli ve Yanlış Sorular");
        soruTipAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, soruTipleri);
        spinnerSoruTip.setAdapter(soruTipAdapter);

        recyclerViewSorular.setAdapter(sorularAdapter);
        kategoriAdlari = new KategorilerDao().tumKategoriAdlari(dbh);
        kategoriAdlari.add(0,"Tüm Konular");

        kategoriAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                kategoriAdlari);
        spinnerKategori.setAdapter(kategoriAdapter);

        listeyiYenile("#EMPTY#");

        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listeyiYenile("#EMPTY#");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        spinnerSoruTip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listeyiYenile("#EMPTY#");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void listeyiYenile(String aranan) {
        if(spinnerSoruTip.getSelectedItemPosition() == 0)
            sorularArraylist = new SorularDao().soruGetir(dbh, spinnerKategori.getSelectedItemPosition(), aranan, false);
        else if(spinnerSoruTip.getSelectedItemPosition() == 1)
            sorularArraylist = new SorularDao().yanlisYapianSoruGetir(dbh, spinnerKategori.getSelectedItemPosition(), aranan, false);
        else if(spinnerSoruTip.getSelectedItemPosition() == 2)
            sorularArraylist = new SorularDao().isaretliSoruGetir(dbh, spinnerKategori.getSelectedItemPosition(), aranan, false);
        else if(spinnerSoruTip.getSelectedItemPosition() == 3)
            sorularArraylist = new SorularDao().isaretliYanlisSoruGetir(dbh, spinnerKategori.getSelectedItemPosition(), aranan, false);
        else sorularArraylist = new SorularDao().soruGetir(dbh, spinnerKategori.getSelectedItemPosition(), aranan, false);
        sorularAdapter.refresh(sorularArraylist);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_sorular, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void init() {
        toolbarSorular = findViewById(R.id.toolbarSorular);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        recyclerViewSorular = findViewById(R.id.recyclerViewSorular);
        spinnerSoruTip = findViewById(R.id.spinnerSoruTip);
        dbh = new DatabaseHelper(this);
        sorularArraylist = new ArrayList<>();
        kategoriAdlari = new ArrayList<>();
        soruTipleri = new ArrayList<>();

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listeyiYenile(s);
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listeyiYenile("#EMPTY#");
    }
}