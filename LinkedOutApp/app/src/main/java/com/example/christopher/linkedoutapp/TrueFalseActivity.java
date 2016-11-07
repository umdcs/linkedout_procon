package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class TrueFalseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);

        EditText TrueFalseQ = (EditText) findViewById(R.id.editText4);
        String tfQ = TrueFalseQ.getText().toString();

        

    }

    public void onBackPressedtf(View view) {

        super.onBackPressed();
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }
}


