package com.ahmetc.yazimkurallari.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahmetc.yazimkurallari.Models.Sorular;
import com.ahmetc.yazimkurallari.R;
import com.ahmetc.yazimkurallari.SoruDetayActivity;
import java.util.ArrayList;

public class SorularAdapter extends RecyclerView.Adapter<SorularAdapter.CardViewHolder> {
    private ArrayList<Sorular> sorularArrayList;
    private Context context;

    public SorularAdapter(Context context, ArrayList<Sorular> sorularArrayList) {
        this.context = context;
        this.sorularArrayList = sorularArrayList;
    }
    class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView cardText;
        private ConstraintLayout cardConstraintLayout;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardText = itemView.findViewById(R.id.cardText);
            cardConstraintLayout = itemView.findViewById(R.id.ozelLayout);
        }
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardviewsoru, viewGroup, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        final Sorular soru = sorularArrayList.get(i);
        cardViewHolder.cardText.setText(soru.getSoru_dogru().trim());

        if(soru.getsoru_isaret() == 1)
            cardViewHolder.cardText.setTextColor(context.getResources().getColor(R.color.blue));
        else if(soru.getSoru_yanlisyapilan() > 0)
            cardViewHolder.cardText.setTextColor(context.getResources().getColor(R.color.red));
        else cardViewHolder.cardText.setTextColor(context.getResources().getColor(R.color.green));

        cardViewHolder.cardConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SoruDetayActivity.class);
                intent.putExtra("soru", soru);
                context.startActivity(intent);
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
