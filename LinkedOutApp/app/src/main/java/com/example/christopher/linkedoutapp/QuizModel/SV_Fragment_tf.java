package com.example.christopher.linkedoutapp.QuizModel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.christopher.linkedoutapp.R;


public class SV_Fragment_tf extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sv_fragment_tf, container, false);
    }

    public static SV_Fragment_tf newInstance()
    {
        Bundle args = new Bundle();

        SV_Fragment_tf fragment = new SV_Fragment_tf();
        fragment.setArguments(args);
        return fragment;
    }
}
