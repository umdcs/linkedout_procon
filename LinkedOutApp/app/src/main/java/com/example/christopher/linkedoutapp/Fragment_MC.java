package com.example.christopher.linkedoutapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Fragment_MC extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mc, container, false);
        return rootView;
    }

    public static Fragment_MC newInstance()
    {
        Bundle args = new Bundle();

        Fragment_MC fragment = new Fragment_MC();
        fragment.setArguments(args);
        return fragment;
    }
}