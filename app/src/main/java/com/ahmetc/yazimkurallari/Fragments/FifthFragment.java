package com.ahmetc.yazimkurallari.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmetc.yazimkurallari.HedefActivity;
import com.ahmetc.yazimkurallari.R;

public class FifthFragment extends Fragment {
    private View view;
    private ConstraintLayout fifthLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        fifthLayout = view.findViewById(R.id.fifthLayout);
        fifthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HedefActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}
