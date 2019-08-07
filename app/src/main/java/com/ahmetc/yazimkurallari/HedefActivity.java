package com.ahmetc.yazimkurallari;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.SorularDao;
import com.ahmetc.yazimkurallari.Receivers.NotificationHolder;
import com.ahmetc.yazimkurallari.Receivers.NotificationReceiver;

import java.util.ArrayList;
import java.util.Calendar;

public class HedefActivity extends AppCompatActivity {
    private NotificationCompat.Builder builder;
    private Spinner spinnerSure;
    private EditText scanValue;
    private ArrayAdapter<String> sureAdapter;
    private ArrayList<String> sureArrayList = new ArrayList<>();
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbarHedef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hedef);
        init();

        //Toolbar
        setSupportActionBar(toolbarHedef);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sureArrayList.add("Dakika");sureArrayList.add("Saat");

        sureAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                sureArrayList);
        spinnerSure.setAdapter(sureAdapter);
    }
    public void buttonIptal(View v) {

        if(alarmManager != null) alarmManager.cancel(pendingIntent);

        Toast.makeText(this, "İptal edildi!", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isOpen", false);
        editor.apply();
    }
    public void buttonOnayla(View v) {

        int scanSure;
        try {
            scanSure = Integer.parseInt(scanValue.getText().toString());
        } catch (Exception e) {
            scanSure = 0;
        }
        Intent broadcastIntent = new Intent(this, NotificationHolder.class);
        pendingIntent = PendingIntent.getBroadcast(this,
                3,
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long tekrarlama = (spinnerSure.getSelectedItemPosition() == 1) ?
                scanSure * 1000 * 60 : scanSure * 1000;
        if(scanSure == 0) {
            Toast.makeText(this, "Sıklı süresini belirtiniz.", Toast.LENGTH_SHORT).show();
        }
        else if(sharedPreferences.getBoolean("isOpen", false))
            Toast.makeText(this, "Zaten aktif!", Toast.LENGTH_SHORT).show();
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isOpen", true);
            editor.apply();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),tekrarlama, pendingIntent);
            Toast.makeText(this, "Aktifleştirildi!", Toast.LENGTH_SHORT).show();
            scanValue.setText("");
        }
    }
    public void showNotification(View v) {
        final String title = "Doğru mu yanlış mı?";
        String[] gelen;
        gelen = new SorularDao().notificationSoru(new DatabaseHelper(this));
        String soru = gelen[(int)(Math.random() * 2)];
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intentTrue = new Intent(this, NotificationReceiver.class);
        intentTrue.putExtra("soru", soru);
        intentTrue.putExtra("dogru", gelen[0]);
        intentTrue.setAction("DOGRU");
        intentTrue.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent intentFalse = new Intent(this, NotificationReceiver.class);
        intentFalse.setAction("YANLIS");
        intentFalse.putExtra("soru", soru);
        intentFalse.putExtra("dogru", gelen[0]);
        intentFalse.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntentTrue = PendingIntent.getBroadcast(this, 0, intentTrue, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentFalse = PendingIntent.getBroadcast(this, 1, intentFalse, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action actionTrue = new NotificationCompat.Action(R.drawable.ic_notification, "Doğru", pendingIntentTrue);
        NotificationCompat.Action actionFalse = new NotificationCompat.Action(R.drawable.ic_notification, "Yanlış", pendingIntentFalse);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String kanalId = "kanalId";
            String kanalAd = "kanalAd";
            String kanalTanim = "kanalTanim";
            int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH; // Yüksek öncelikli olarak göster
            NotificationChannel channel = notificationManager.getNotificationChannel("kanalId");
            if (channel == null) {
                channel = new NotificationChannel(kanalId, kanalAd, kanalOnceligi);
                channel.setDescription(kanalTanim);
                notificationManager.createNotificationChannel(channel);
            }
            builder = new NotificationCompat.Builder(this, kanalId);
            builder.setContentTitle(title);
            builder.setContentText(soru);
            builder.setOngoing(true);
            builder.addAction(actionTrue);
            builder.addAction(actionFalse);
            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setAutoCancel(true);

        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle(title);
            builder.setContentText(soru);
            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setAutoCancel(true);
            builder.addAction(actionTrue);
            builder.addAction(actionFalse);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        notificationManager.notify(1, builder.build());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void init() {
        spinnerSure = findViewById(R.id.spinnerSure);
        scanValue = findViewById(R.id.scanValue);
        sharedPreferences = getSharedPreferences("notification", MODE_PRIVATE);
        toolbarHedef = findViewById(R.id.toolbarHedef);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
