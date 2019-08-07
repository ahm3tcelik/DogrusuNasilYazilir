package com.ahmetc.yazimkurallari;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.KullaniciDao;
import com.ahmetc.yazimkurallari.Database.SorularDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class IstatistiklerActivity extends AppCompatActivity {
    private PieChart pieChart;
    private Toolbar toolbarIst;
    private DatabaseHelper dbh;
    private int dogru_sayisi = 0, yanlis_sayisi = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istatistikler);
        init();

        // Toolbar
        toolbarIst.setTitle("İstatistikler");
        setSupportActionBar(toolbarIst);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.05f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.getDescription().setText("Doğru/Yanlış Oranı");
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        chart();
    }
    private void init() {
        pieChart = findViewById(R.id.pieChart);
        toolbarIst = findViewById(R.id.toolbarIst);
        dbh = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_istatistikler, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        else if(item.getItemId() == R.id.action_sifirla) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.app_name));
            alert.setMessage("İstatistikleri sıfırlamak istediğinizden emin misiniz?");
            alert.setNegativeButton("Hayır", null);
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new SorularDao().sifirlaTumSoru(dbh);
                    new KullaniciDao().sifirlaKullanici(dbh);
                    pieChart.clear();
                    chart();
                    Toast.makeText(IstatistiklerActivity.this, "İstatistikler sıfırlandı!", Toast.LENGTH_SHORT).show();
                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void chart() {
        KullaniciDao kullaniciDao = new KullaniciDao();
        dogru_sayisi = kullaniciDao.dogruSayisi(dbh);
        yanlis_sayisi = kullaniciDao.yanlisSayisi(dbh) ;

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry((dogru_sayisi == 0 && yanlis_sayisi == 0) ? 1:dogru_sayisi, "Doğru"));
        yValues.add(new PieEntry((dogru_sayisi == 0 && yanlis_sayisi == 0) ? 1:yanlis_sayisi, "Yanlış"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("Doğru : " + dogru_sayisi + "\nYanlış : " + yanlis_sayisi );
        pieChart.setData(data);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
