package com.example.christopher.linkedoutapp;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class fragmentTest extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayAdapter<String> adapter;
    ArrayList<String> itemList;
    Spinner spinner;
    String Q,A;
    //FragmentExample obj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        spinner = (Spinner) findViewById(R.id.questions_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.questions_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int qv = parent.getSelectedItemPosition();
        String value  = spinner.getSelectedItem().toString();
        Log.d("Debug: ", "position on array: : " + value + "," + "Position: " + qv );

        if (qv == 1)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, Fragment_SA.newInstance())
                    .commit();

            //onFragmentInteraction(Q,A);
        }
        else if (qv == 2)
        {
            Log.d("Debug: ", "4531 fragment " );
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, Fragment_TF.newInstance())
                    .commit();
        }
        else if (qv == 3)
        {

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
