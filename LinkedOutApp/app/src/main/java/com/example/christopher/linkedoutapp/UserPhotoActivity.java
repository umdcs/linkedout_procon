package com.example.christopher.linkedoutapp;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by imcdo on 3/15/2017.
 */

public class UserPhotoActivity extends AppCompatActivity
        implements ModelViewPresenterComponents.UserPhotoView {

    public final static int PICK_PHOTO_CODE = 1046;

    public void onClickGallery() {
        //Create new intent for selection on photo
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

}
