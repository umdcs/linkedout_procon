package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MultipleChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);
    }

    public void onBackPressedmc(View view) {

        super.onBackPressed();
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }

}
