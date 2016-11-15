package com.example.christopher.linkedoutapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;



public class Fragment_TF extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tf, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }

    public static Fragment_TF newInstance()
    {
        Bundle args = new Bundle();

        Fragment_TF fragment = new Fragment_TF();
        fragment.setArguments(args);
        return fragment;
    }
}