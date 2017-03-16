package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EmployerRegisterActivity extends AppCompatActivity {

    private ModelViewPresenterComponents.RegisterActivityView photoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_register);
    }

    public void changeViewToStudent(View view) {
        Intent intent = new Intent(this, StudentRegisterActivity.class);
        startActivity(intent);
    }


    public final static int PICK_PHOTO_CODE = 1046;

    public void onClickGallery(View view) {
        //Create new intent for selection on photo
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

}
