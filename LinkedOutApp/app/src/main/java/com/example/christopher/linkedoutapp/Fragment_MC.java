package com.example.christopher.linkedoutapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by clarenceondieki on 11/15/16.
 */

public class Fragment_MC extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mc, container, false);
        // Inflate the layout for this fragment
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
