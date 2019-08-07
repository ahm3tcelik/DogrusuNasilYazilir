package com.ahmetc.yazimkurallari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private ImageView splashLogo;
    private TextView splashAppName, splashDesc;
    private LinearLayout splashTDK, splashIcons;
    Animation alpha01, alpha10, scale01, traslateright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();

        splashAppName.setAnimation(alpha10);
        splashLogo.setAnimation(alpha01);
        splashTDK.setAnimation(alpha01);
        splashDesc.setAnimation(alpha01);
        splashIcons.setAnimation(traslateright);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        };
        timerThread.start();
    }
    private void init() {
        splashAppName = findViewById(R.id.splashAppName);
        splashDesc = findViewById(R.id.splashDesc);
        splashLogo = findViewById(R.id.splashLogo);
        splashTDK = findViewById(R.id.splashTDK);
        splashIcons = findViewById(R.id.splashIcons);
        alpha01 = AnimationUtils.loadAnimation(this, R.anim.alpha01);
        alpha10 = AnimationUtils.loadAnimation(this, R.anim.alpha10);
        scale01 = AnimationUtils.loadAnimation(this, R.anim.scale01);
        traslateright = AnimationUtils.loadAnimation(this, R.anim.translateright);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}