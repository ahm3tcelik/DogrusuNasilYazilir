package com.ahmetc.yazimkurallari;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.SorularDao;
import com.ahmetc.yazimkurallari.Models.Sorular;

public class SoruDetayActivity extends AppCompatActivity {
    private Toolbar soruDetayToolbar;
    private TextView detayYanlis, detayDogru, soruDogruSayisiYazi, soruYanlisSayisiYazi, soruIsaretYazi;
    private ImageButton soruIsaretButton;
    private Button soruGeri;
    private SorularDao sorularDao = new SorularDao();
    private DatabaseHelper dbh;

    private Sorular soru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_detay);
        init();

        //Toolbar
        setSupportActionBar(soruDetayToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbh = new DatabaseHelper(this);
        soru = (Sorular) getIntent().getSerializableExtra("soru");
        updateUI();

        soruIsaretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soru.getsoru_isaret() > 0) {
                    soru.setsoru_isaret(0);
                    sorularDao.isaretEkleKaldir(dbh, soru.getSoru_id(), 0);
                    soruIsaretButton.setImageResource(R.drawable.isaretsiz);
                    soruIsaretYazi.setText("İşaretle : ");
                    Toast.makeText(SoruDetayActivity.this, "İşaret Kaldırıldı", Toast.LENGTH_SHORT).show();
                }
                else {
                    soru.setsoru_isaret(1);
                    soruIsaretButton.setImageResource(R.drawable.isaretli);
                    soruIsaretYazi.setText("İşareti Kaldır : ");
                    sorularDao.isaretEkleKaldir(dbh, soru.getSoru_id(), 1);
                    Toast.makeText(SoruDetayActivity.this, "İşaretlendi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        soruGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void init() {
        soruDetayToolbar = findViewById(R.id.soruDetayToolbar);
        detayYanlis = findViewById(R.id.detayYanlis);
        detayDogru = findViewById(R.id.detayDogru);
        soruDogruSayisiYazi = findViewById(R.id.soruDogruSayisiYazi);
        soruYanlisSayisiYazi = findViewById(R.id.soruYanlisSayisiYazi);
        soruIsaretYazi = findViewById(R.id.soruIsaretYazi);
        soruIsaretButton = findViewById(R.id.soruIsaretButton);
        soruGeri = findViewById(R.id.soruGeri);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_sorudetay, menu);
        return true;
    }
    private boolean isConnected() {
        ConnectivityManager con_manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        return (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected());
    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_info:
                final Dialog dialog = new Dialog(SoruDetayActivity.this);
                dialog.setContentView(R.layout.soru_info);
                final WebView webView = dialog.findViewById(R.id.infoWebView);
                webView.getSettings().setJavaScriptEnabled(true);
                dialog.show();
                if(isConnected()) {
                    webView.loadUrl("https://www.google.com.tr/search?q=" + soru.getSoru_dogru() + "+nedir");
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(SoruDetayActivity.this, "İnternet bağlantısı yok!", Toast.LENGTH_LONG).show();
                }
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        webView.clearMatches();
                        webView.clearCache(true);
                        webView.clearFormData();
                        webView.clearHistory();
                        webView.destroy();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateUI() {
        detayDogru.setText(soru.getSoru_dogru());
        detayYanlis.setText(soru.getSoru_yanlis());
        soruDogruSayisiYazi.setText("Yapılan doğru sayısı : " + soru.getSoru_dogruyapilan());
        soruYanlisSayisiYazi.setText("Yapılan yanlış sayısı : " + soru.getSoru_yanlisyapilan());
        soruIsaretYazi.setText((soru.getsoru_isaret() > 0) ? "İşareti Kaldır : " : "İşaretle : ");
        soruIsaretButton.setImageResource((soru.getsoru_isaret() > 0) ? R.drawable.isaretli : R.drawable.isaretsiz);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dialog = null;
    }
}
