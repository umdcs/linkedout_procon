package com.example.christopher.linkedoutapp.QuizModel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.christopher.linkedoutapp.R;


public class SV_Fragment_mc extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sv_fragment_mc, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }

    public static SV_Fragment_mc newInstance()
    {
        Bundle args = new Bundle();

        SV_Fragment_mc fragment = new SV_Fragment_mc();
        fragment.setArguments(args);
        return fragment;
    }
}
