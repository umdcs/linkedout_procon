package com.example.christopher.linkedoutapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by imcdo on 3/15/2017.
 */

public class UserPhotoActivity extends AppCompatActivity
        implements ModelViewPresenterComponents.UserPhotoView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_photo);
        //EditText prevNum = (EditText) findViewById(R.id.editPrev);
        //setupModelViewPresenterComponents();

    }

}
