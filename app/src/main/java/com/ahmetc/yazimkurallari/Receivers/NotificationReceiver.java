package com.ahmetc.yazimkurallari.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String dogru = intent.getStringExtra("dogru");
        String soru = intent.getStringExtra("soru");
        if (intent.getAction().equalsIgnoreCase("DOGRU")) {
            if(dogru.equals(soru)) {
                Toast.makeText(context, "Tebrikler! Cevabınız doğru", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Cevabınız yanlış. Doğru cevap " + dogru, Toast.LENGTH_SHORT).show();
            }
        } else if (intent.getAction().equalsIgnoreCase("YANLIS")) {
            if (dogru.equals(soru)) {
                Toast.makeText(context, "Cevabınız yanlış. Doğru cevap " + dogru, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Tebrikler! Cevabınız doğru", Toast.LENGTH_SHORT).show();
            }
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);
    }
}
