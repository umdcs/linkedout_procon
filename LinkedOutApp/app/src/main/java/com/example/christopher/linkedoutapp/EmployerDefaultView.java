package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EmployerDefaultView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_default_view);
    }
    public void createJob(View view){
        Intent intent = new Intent(this, EmployerCreateJob.class);
        startActivity(intent);
    }
}
