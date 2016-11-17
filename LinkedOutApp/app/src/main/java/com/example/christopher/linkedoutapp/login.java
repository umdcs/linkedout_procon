package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onClickStudent(View view){
        Intent intent = new Intent(this, StudentDefaultView.class);
        startActivity(intent);
    }

    public void onClickEmployer(View view){
        Intent intent = new Intent(this, ArrayQuiz.class);
        startActivity(intent);
    }
}
