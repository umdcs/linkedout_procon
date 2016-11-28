package com.example.christopher.linkedoutapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;


public class Fragment_MC extends Fragment implements View.OnClickListener{

    View rootView;
    Button button;
    EditText et1,et2,et3,et4;
    String e1,e2,e3,e4;
    OnFragmentInteractionListener_MC mListener;

    @Override
    public void onClick(View v) {

    }


    public interface OnFragmentInteractionListener_MC{
        void onFragmentInteraction_MC(String value, String value1, String value2, String value3);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnFragmentInteractionListener_MC) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mc, container, false);

        button = (Button) rootView.findViewById(R.id.button6);
        button.setOnClickListener(this);

        button.setOnClickListener(new View.OnClickListener()
                              {
                                  @Override
                                  public void onClick(View view) {
                                      // Get references from the views
                                      et1 = (EditText) rootView.findViewById(R.id.frag3_Q);
                                      et2 = (EditText) rootView.findViewById(R.id.option1);
                                      et3 = (EditText) rootView.findViewById(R.id.option2);
                                      et4 = (EditText) rootView.findViewById(R.id.option3);

                                      e1=et1.getText().toString();
                                      e2=et2.getText().toString();
                                      e3=et3.getText().toString();
                                      e4=et4.getText().toString();

                                      mListener.onFragmentInteraction_MC(e1,e2,e3,e4);
                                  }}
        );


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