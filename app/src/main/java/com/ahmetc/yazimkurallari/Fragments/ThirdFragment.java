package com.ahmetc.yazimkurallari.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmetc.yazimkurallari.IstatistiklerActivity;
import com.ahmetc.yazimkurallari.R;

public class ThirdFragment extends Fragment {
    private ConstraintLayout thirdLayout;
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_third, container, false);
        thirdLayout = view.findViewById(R.id.thirdLayout);

        thirdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), IstatistiklerActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}
