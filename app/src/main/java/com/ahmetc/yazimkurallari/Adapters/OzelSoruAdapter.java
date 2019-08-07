package com.ahmetc.yazimkurallari.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetc.yazimkurallari.Database.DatabaseHelper;
import com.ahmetc.yazimkurallari.Database.OzelSorularDao;
import com.ahmetc.yazimkurallari.Models.Sorular;
import com.ahmetc.yazimkurallari.R;
import com.ahmetc.yazimkurallari.SoruDetayActivity;

import java.util.ArrayList;

public class OzelSoruAdapter extends RecyclerView.Adapter<OzelSoruAdapter.CardViewHolder> {
    private ArrayList<Sorular> sorularArrayList;
    private Context context;

    public OzelSoruAdapter(Context context, ArrayList<Sorular> sorularArrayList) {
        this.context = context;
        this.sorularArrayList = sorularArrayList;
    }
    class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView OzelSoruText;
        private ConstraintLayout ozelLayout;
        private FloatingActionButton soruSilFab;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            OzelSoruText = itemView.findViewById(R.id.OzelSoruText);
            ozelLayout = itemView.findViewById(R.id.ozelLayout);
            soruSilFab = itemView.findViewById(R.id.soruSilFab);
        }
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ozelsorucard, viewGroup, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        final Sorular soru = sorularArrayList.get(i);
        cardViewHolder.OzelSoruText.setText(soru.getSoru_dogru().trim());
        final int position = i;
        cardViewHolder.ozelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SoruDetayActivity.class);
                intent.putExtra("soru", soru);
                context.startActivity(intent);
            }
        });
        cardViewHolder.soruSilFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(context.getString(R.string.app_name));
                alert.setMessage("Soruyu silmek istediğinizden emin misiniz?");
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OzelSorularDao ozelSorularDao = new OzelSorularDao();
                        ozelSorularDao.kaldirOzelSoru(new DatabaseHelper(context), soru.getSoru_id());
                        Toast.makeText(context, "Soru başarıyla kaldırıldı!", Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
                        sorularArrayList.remove(soru);
                    }
                });
                alert.setNegativeButton("Hayır", null);
                alert.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return sorularArrayList.size();
    }
    public void refresh(ArrayList<Sorular> sorular) {
        sorularArrayList.clear();
        sorularArrayList.addAll(sorular);
        notifyDataSetChanged();
    }
}
