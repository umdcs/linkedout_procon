package com.example.christopher.linkedoutapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Fragment_SA extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sa, container, false);
        return rootView;
    }

    public static Fragment_SA newInstance()
    {
        Bundle args = new Bundle();

        Fragment_SA fragment = new Fragment_SA();
        fragment.setArguments(args);
        return fragment;
    }
}
