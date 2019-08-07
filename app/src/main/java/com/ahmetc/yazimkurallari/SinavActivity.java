package com.ahmetc.yazimkurallari;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.KullaniciDao;
import com.ahmetc.yazimkurallari.Database.SorularDao;
import com.ahmetc.yazimkurallari.Models.Sorular;
import java.util.ArrayList;
import java.util.HashSet;

public class SinavActivity extends AppCompatActivity {
    private ConstraintLayout sinavLayout;
    private FrameLayout secenekAHolder, secenekBHolder;
    private TextView secenekA, secenekB, kategoriYazi, testDogruSayisi, testYanlisSayisi;
    private Toolbar toolbarSinav;
    private int filitre_kategori;
    private int filitre_sorutip;
    private HashSet<String> sorularHashSet;
    private ArrayList<String> karisikList;
    private ArrayList<Sorular> sorularArrayList;
    private DatabaseHelper dbh;
    private SorularDao sorularDao;
    private KullaniciDao kullaniciDao;
    private String dogruCevap, yanlisCevap;
    private int currentSoru = 0, dogruSayisi = 0, yanlisSayisi = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinav);
        init();

        //Toolbar
        toolbarSinav.setTitle("Test");
        setSupportActionBar(toolbarSinav);
        sinavHazirla();

        secenekA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sorular tempSoru = sorularArrayList.get(currentSoru);
                if(dogruCevap.equals(secenekA.getText().toString())) { // Doğru Cevap
                    dogruSayisi++;
                    sorularDao.dogruYapilanEkle(dbh, tempSoru.getSoru_id(),
                            tempSoru.getSoru_dogruyapilan() + 1);
                    secenekAHolder.setBackgroundColor(getResources().getColor(R.color.green));
                    kullaniciDao.dogruArttir(dbh,kullaniciDao.dogruSayisi(dbh));
                }
                else { // Yanlış Cevap
                    yanlisSayisi++;
                    sorularDao.yanlisYapilanEkle(dbh, tempSoru.getSoru_id(),
                            tempSoru.getSoru_yanlisyapilan() + 1);
                    secenekAHolder.setBackgroundColor(getResources().getColor(R.color.red));
                    secenekBHolder.setBackgroundColor(getResources().getColor(R.color.green));
                    kullaniciDao.yanlisArttir(dbh,kullaniciDao.yanlisSayisi(dbh));
                }
                secenekB.setClickable(false);
                secenekA.setClickable(false);
                currentSoru++;
                kategoriYazi.setText("Soruyu geçmek için dokunun.");
                testDogruSayisi.setText("" + dogruSayisi);
                testYanlisSayisi.setText("" + yanlisSayisi);
            }
        });
        secenekB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sorular tempSoru = sorularArrayList.get(currentSoru);
                if(dogruCevap.equals(secenekB.getText().toString())) { // Doğru Cevap
                    dogruSayisi++;
                    sorularDao.dogruYapilanEkle(dbh, tempSoru.getSoru_id(),
                            tempSoru.getSoru_dogruyapilan() + 1);
                    secenekBHolder.setBackgroundColor(getResources().getColor(R.color.green));
                    kullaniciDao.dogruArttir(dbh,kullaniciDao.dogruSayisi(dbh));
                }
                else { // Yanlış Cevap
                    yanlisSayisi++;
                    sorularDao.yanlisYapilanEkle(dbh, tempSoru.getSoru_id(),
                            tempSoru.getSoru_yanlisyapilan() + 1);
                    secenekBHolder.setBackgroundColor(getResources().getColor(R.color.red));
                    secenekAHolder.setBackgroundColor(getResources().getColor(R.color.green));
                    kullaniciDao.yanlisArttir(dbh, kullaniciDao.yanlisSayisi(dbh));
                }
                secenekB.setClickable(false);
                secenekA.setClickable(false);
                currentSoru++;
                kategoriYazi.setText("Soruyu geçmek için dokunun.");
                testDogruSayisi.setText("" + dogruSayisi);
                testYanlisSayisi.setText("" + yanlisSayisi);
            }
        });
        kategoriYazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kategoriYazi.getText().toString().equals("Soruyu geçmek için dokunun."))
                    soruYukle();
            }
        });
        sinavLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kategoriYazi.getText().toString().equals("Soruyu geçmek için dokunun."))
                    soruYukle();
            }
        });
    }
    private void soruYukle() {
        if(currentSoru >= sorularArrayList.size()) { // Sınav Bitti
            sinavBitti();
        }
        else {
            supportInvalidateOptionsMenu(); // refresh toolbar
            secenekB.setClickable(true);
            secenekA.setClickable(true);
            kategoriYazi.setText(sorularArrayList.get(currentSoru).getKategori().getKategori_ad());
            dogruCevap = sorularArrayList.get(currentSoru).getSoru_dogru();
            yanlisCevap = sorularArrayList.get(currentSoru).getSoru_yanlis();
            sorularHashSet.clear();
            sorularHashSet.add(dogruCevap);
            sorularHashSet.add(yanlisCevap);
            karisikList.clear();
            karisikList.addAll(sorularHashSet);
            secenekA.setText(karisikList.get(0));
            secenekB.setText(karisikList.get(1));
            secenekAHolder.setBackground(getResources().getDrawable(R.drawable.square_beyaz));
            secenekBHolder.setBackground(getResources().getDrawable(R.drawable.square_beyaz));
        }
    }
    private void sinavHazirla() {
        sorularArrayList.clear();
        currentSoru = 0;
        dogruSayisi = 0;
        yanlisSayisi = 0;

        testDogruSayisi.setText("" + dogruSayisi);
        testYanlisSayisi.setText("" + yanlisSayisi);

        // Soruları veri tabanından çek
        switch (filitre_sorutip) {
            case 0:
                sorularArrayList = sorularDao.soruGetir(dbh, filitre_kategori, "#EMPTY#", true);
                break;
            case 1:
                sorularArrayList = sorularDao.yanlisYapianSoruGetir(dbh, filitre_kategori, "#EMPTY#", true);
                break;
            case 2:
                sorularArrayList = sorularDao.isaretliSoruGetir(dbh, filitre_kategori, "#EMPTY#", true);
                break;
            case 3:
                sorularArrayList = sorularDao.isaretliYanlisSoruGetir(dbh, filitre_kategori, "#EMPTY#", true);
                break;
            default:
                sorularArrayList = sorularDao.soruGetir(dbh, filitre_kategori, "#EMPTY#", true);
        }
        if (sorularArrayList.size() < 1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.app_name));
            alert.setMessage("Bu kriterlere uygun soru yok");
            alert.setCancelable(false);
            alert.setNeutralButton("Ana Menüye Dön", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                    dialog.dismiss();
                }
            });
            alert.create().show();
        }
        else soruYukle();
    }
    private void sinavBitti() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage("Sınav Bitti\n" + dogruSayisi + " doğru, " + yanlisSayisi + " yanlış.");
        alertDialog.setPositiveButton("Yeniden Dene", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sinavHazirla();
                Toast.makeText(SinavActivity.this, "Sorular Yenilendi", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Ana Menüye Dön", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(SinavActivity.this, MainActivity.class));
                finish();
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.create().show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_changeisaret:
                int tempCurrentSoru; // Kullanıcı cevabını verdikten sonra currentSoru bir arttığı için yanlış soru işaretlenmle fix.
                if (kategoriYazi.getText().toString().equals("Soruyu geçmek için dokunun."))
                    tempCurrentSoru = currentSoru - 1;
                else tempCurrentSoru = currentSoru;
                Sorular soru = sorularArrayList.get(tempCurrentSoru);
                if (soru.getsoru_isaret() > 0) {
                    item.setIcon(getResources().getDrawable(R.drawable.isaretsiz));
                    sorularDao.isaretEkleKaldir(dbh, soru.getSoru_id(), 0);
                    soru.setsoru_isaret(0);
                    Toast.makeText(this, "İşaret kaldırıldı", Toast.LENGTH_SHORT).show();
                } else {
                    item.setIcon(getResources().getDrawable(R.drawable.isaretli));
                    sorularDao.isaretEkleKaldir(dbh, soru.getSoru_id(), 1);
                    soru.setsoru_isaret(1);
                    Toast.makeText(this, "İşaretlendi", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_sharesoru:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hangisi doğru? " + secenekA.getText().toString() +
                        " - " + secenekB.getText().toString() +
                        " Daha fazlası için bağlantıdaki uygulamayı indirebilirsin. https://play.google.com/store/apps/details?id=" + getPackageName();
                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(sharingIntent,getString(R.string.app_name)));
                break;
            case R.id.action_cikis:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Ayrılmak istediğinizden emin misiniz?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sinavBitti();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_sinav, menu);
        try {
            Sorular soru = sorularArrayList.get(currentSoru);
            MenuItem item = menu.findItem(R.id.action_changeisaret);
            if (soru.getsoru_isaret() > 0) {
                item.setIcon(getResources().getDrawable(R.drawable.isaretli));
                item.setTitle("İşareti kaldır");
            } else {
                item.setIcon(getResources().getDrawable(R.drawable.isaretsiz));
                item.setTitle("Soruyu işaretle");
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }
    private void init() {
        filitre_kategori = getIntent().getIntExtra("filitre_kategori", 0);
        filitre_sorutip = getIntent().getIntExtra("filitre_sorutip", 0);
        toolbarSinav = findViewById(R.id.toolbarSinav);
        secenekA = findViewById(R.id.secenekA);
        secenekB = findViewById(R.id.secenekB);
        kategoriYazi = findViewById(R.id.kategoriYazi);
        testDogruSayisi = findViewById(R.id.testdogruSayisi);
        testYanlisSayisi = findViewById(R.id.testYanlisSayisi);
        sinavLayout = findViewById(R.id.sinavLayout);
        secenekAHolder = findViewById(R.id.secenekAHolder);
        secenekBHolder = findViewById(R.id.secenekBHolder);
        dbh = new DatabaseHelper(this);
        sorularDao = new SorularDao();
        kullaniciDao = new KullaniciDao();
        sorularHashSet = new HashSet<>();
        karisikList = new ArrayList< >();
        sorularArrayList = new ArrayList<>();
    }
}
