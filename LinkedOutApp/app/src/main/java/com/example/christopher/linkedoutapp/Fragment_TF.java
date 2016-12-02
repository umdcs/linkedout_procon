package com.example.christopher.linkedoutapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class Fragment_TF extends Fragment implements View.OnClickListener{

    View rootView;
    OnFragmentInteractionListener_TF mListener;
    Button button;
    EditText et1;
    String e1,e2;
    RadioGroup radioGroup;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnFragmentInteractionListener_TF) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tf, container, false);

        button = (Button) rootView.findViewById(R.id.button2A);
        button.setOnClickListener(this);

        button.setOnClickListener(new OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View view) {
                                          // Get references from the views
                                          et1 = (EditText) rootView.findViewById(R.id.frag2_Q);
                                          //et2 = (EditText) rootView.findViewById(R.id.frag_A);
                                          e1=et1.getText().toString();
                                          //e2=et2.getText().toString();

                                          Log.d("Debug: ", "question: "+ e1+ " Answer:"+e2);

                                          mListener.onFragmentInteraction_TF(e1,e2);
                                          Log.d("Debug: ", "questionnnnnnnnnn: "+ e1);

                                      }}
        );

        radioGroup = (RadioGroup) rootView.findViewById(R.id.truefalsegroup);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.trueButton:
                        e2 = "true";
                        break;
                    case R.id.falseButton:
                        e2 = "false";
                        break;
                }
            }
        });


        return rootView;
    }

    public static Fragment_TF newInstance()
    {
        Bundle args = new Bundle();

        Fragment_TF fragment = new Fragment_TF();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {

    }

    //true/false interface
    public interface OnFragmentInteractionListener_TF{
        void onFragmentInteraction_TF(String value, String value1);
    }
}