package com.ahmetc.yazimkurallari;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmetc.yazimkurallari.Database.DatabaseCopyHelper;
import com.ahmetc.yazimkurallari.Fragments.FifthFragment;
import com.ahmetc.yazimkurallari.Fragments.FirstFragment;
import com.ahmetc.yazimkurallari.Fragments.FourthFragment;
import com.ahmetc.yazimkurallari.Fragments.SecondFragment;
import com.ahmetc.yazimkurallari.Fragments.ThirdFragment;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kopyala();

        // Fragments
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.firstFragmentHolder, new FirstFragment());
        ft.add(R.id.secondFragmentHolder, new SecondFragment());
        ft.add(R.id.thirdFragmentHolder, new ThirdFragment());
        ft.add(R.id.fourthFragmentHolder, new FourthFragment());
        ft.add(R.id.fifthFragmentHolder, new FifthFragment());
        ft.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    private void kopyala() {
        DatabaseCopyHelper databaseCopyHelper = new DatabaseCopyHelper(this);
        try {
            databaseCopyHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        databaseCopyHelper.openDataBase();
    }
}
