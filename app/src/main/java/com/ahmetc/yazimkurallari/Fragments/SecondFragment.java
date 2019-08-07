package com.ahmetc.yazimkurallari.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahmetc.yazimkurallari.R;
import com.ahmetc.yazimkurallari.SorularActivity;

public class SecondFragment extends Fragment {
    private ConstraintLayout secondLayout;
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second, container, false);
        secondLayout = view.findViewById(R.id.secondLayout);

        secondLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), SorularActivity.class));
            }
        });
        return view;
    }
}
