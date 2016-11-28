package com.example.christopher.linkedoutapp.QuizModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import com.example.christopher.linkedoutapp.R;


public class SV_Fragment_sa extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sv_fragment_sa, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }

    public static SV_Fragment_sa newInstance()
    {
        Bundle args = new Bundle();

        SV_Fragment_sa fragment = new SV_Fragment_sa();
        fragment.setArguments(args);
        return fragment;
    }
}
