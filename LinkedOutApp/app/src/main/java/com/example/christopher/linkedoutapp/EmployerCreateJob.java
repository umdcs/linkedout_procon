package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EmployerCreateJob extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_create_job);
    }
    public void onClickCancel(View view){
        Intent intent = new Intent(this, EmployerDefaultView.class);
        startActivity(intent);
    }
}
