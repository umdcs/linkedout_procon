package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EmployerRegisterActivity extends AppCompatActivity {

    private ModelViewPresenterComponents.RegisterActivityView photoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }





    public void changeViewToStudent(View view) {
        Intent intent = new Intent(this, StudentRegisterActivity.class);
        startActivity(intent);

    }

    /*public void onClickRegister() {

    }            */

}
