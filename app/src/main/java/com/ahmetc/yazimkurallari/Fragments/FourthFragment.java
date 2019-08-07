package com.ahmetc.yazimkurallari.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmetc.yazimkurallari.OzelSorularActivity;
import com.ahmetc.yazimkurallari.R;

public class FourthFragment extends Fragment {
    private ConstraintLayout fourthLayout;
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fourth, container, false);
        fourthLayout = view.findViewById(R.id.fourthLayout);

        fourthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OzelSorularActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}
