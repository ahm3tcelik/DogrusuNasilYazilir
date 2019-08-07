package com.ahmetc.yazimkurallari.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.KategorilerDao;
import com.ahmetc.yazimkurallari.R;
import com.ahmetc.yazimkurallari.SinavActivity;

import java.util.ArrayList;

public class FirstFragment extends Fragment{
    private ConstraintLayout firstLayout;
    private View view;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        firstLayout = view.findViewById(R.id.firstLayout);

        firstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.test_dialog);
                dialog.show();
                final Spinner spinnerSoruTip, spinnerKategori;
                TextView filitreText;
                Button dialogStart;
                ImageView filitreImage;
                filitreImage = dialog.findViewById(R.id.filitreImage);
                spinnerKategori = dialog.findViewById(R.id.spinnerKategori);
                spinnerSoruTip = dialog.findViewById(R.id.spinnerSoruTip);
                filitreText = dialog.findViewById(R.id.filitreText);
                dialogStart = dialog.findViewById(R.id.dialogStart);


                ArrayAdapter<String> kategoriAdapter;
                ArrayAdapter<String> soruTipAdapter;
                ArrayList<String> kategoriAdlari;
                ArrayList<String> soruTipleri = new ArrayList<>();

                filitreText.setTextColor(Color.WHITE);
                filitreImage.setImageResource(R.drawable.filitrele);

                // Soru Tipleri Spinner
                soruTipleri.add("Tüm Sorular");soruTipleri.add("Sadece Yanlış Sorular");
                soruTipleri.add("Sadece İşaretli Sorular");soruTipleri.add("İşaretli ve Yanlış Sorular");
                soruTipAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, soruTipleri);
                spinnerSoruTip.setAdapter(soruTipAdapter);

                spinnerSoruTip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //Kategoriler Spinner
                kategoriAdlari = new KategorilerDao().tumKategoriAdlari(new DatabaseHelper(getActivity().getApplicationContext()));
                kategoriAdlari.add(0,"Tüm Konular");

                kategoriAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        kategoriAdlari);
                spinnerKategori.setAdapter(kategoriAdapter);

                spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                dialogStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SinavActivity.class);
                        intent.putExtra("filitre_kategori", spinnerKategori.getSelectedItemPosition());
                        intent.putExtra("filitre_sorutip", spinnerSoruTip.getSelectedItemPosition());
                        startActivity(intent);
                        getActivity().finish();
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }
}
