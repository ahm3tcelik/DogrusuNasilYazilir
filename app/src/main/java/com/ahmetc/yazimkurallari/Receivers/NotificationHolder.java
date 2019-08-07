package com.ahmetc.yazimkurallari.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.SorularDao;
import com.ahmetc.yazimkurallari.R;

public class NotificationHolder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CreateNotification(context);
    }
    public void CreateNotification(Context context) {
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final String title = "Doğru mu yanlış mı?";
        String[] gelen;
        gelen = new SorularDao().notificationSoru(new DatabaseHelper(context));
        String soru = gelen[(int)(Math.random() * 2)];
        Intent intentTrue = new Intent(context, NotificationReceiver.class);
        intentTrue.putExtra("soru", soru);
        intentTrue.putExtra("dogru", gelen[0]);
        intentTrue.setAction("DOGRU");
        intentTrue.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent intentFalse = new Intent(context, NotificationReceiver.class);
        intentFalse.setAction("YANLIS");
        intentFalse.putExtra("soru", soru);
        intentFalse.putExtra("dogru", gelen[0]);
        intentFalse.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntentTrue = PendingIntent.getBroadcast(context, 0, intentTrue, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentFalse = PendingIntent.getBroadcast(context, 1, intentFalse, PendingIntent.FLAG_CANCEL_CURRENT);
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
            builder = new NotificationCompat.Builder(context, kanalId);
            builder.setContentTitle(title);
            builder.setContentText(soru);
            builder.setOngoing(true);
            builder.addAction(actionTrue);
            builder.addAction(actionFalse);
            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setAutoCancel(true);

        } else {
            builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(title);
            builder.setContentText(soru);
            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setAutoCancel(true);
            builder.addAction(actionTrue);
            builder.addAction(actionFalse);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("notification", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isOpen", false))
            notificationManager.notify(1, builder.build());
    }
}
