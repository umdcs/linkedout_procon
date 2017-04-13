package com.example.christopher.linkedoutapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;


public class Fragment_SA extends Fragment implements View.OnClickListener{

    private View rootView;
    private Button b1;
    private EditText et1;
    private EditText et2;
    private String e1;
    private String e2;
    private OnFragmentInteractionListener_SA mListener;


    public interface OnFragmentInteractionListener_SA{
        void onFragmentInteraction_SA(String value, String value1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnFragmentInteractionListener_SA) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sa, container, false);

        b1 = (Button) rootView.findViewById(R.id.frag1Button);
        b1.setOnClickListener(this);

        b1.setOnClickListener(new OnClickListener()
                              {
                                  @Override
                                  public void onClick(View view) {
                                      // Get references from the views
                                      et1 = (EditText) rootView.findViewById(R.id.frag_Q);
                                      et2 = (EditText) rootView.findViewById(R.id.frag_A);
                                      e1=et1.getText().toString();
                                      e2=et2.getText().toString();

                                      mListener.onFragmentInteraction_SA(e1,e2);
                                  }}
        );

        return rootView;
    }

    public static Fragment_SA newInstance()
    {
        Bundle args = new Bundle();

        Fragment_SA fragment = new Fragment_SA();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {

    }
}
