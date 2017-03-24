package com.example.christopher.linkedoutapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Spinner;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.R.attr.width;

public class StudentRegisterActivity extends AppCompatActivity {

    Spinner spinner;
    private static final int SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        /*
        //implement question spinner
        spinner = (Spinner) findViewById(R.id.stateSpinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.questions_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        */
        /*
        Spinner spinner = (Spinner) findViewById(R.id.stateSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        */
    }

    public void changeViewToEmployer(View view) {
        Intent intent = new Intent(this, EmployerRegisterActivity.class);
        startActivity(intent);
    }

    public final static int PICK_PHOTO_CODE = 1;

    public void onClickGallery(View view) {

        if(isReadStorageAllowed()){
            //If permission is already having then showing the toast
            Toast.makeText(StudentRegisterActivity.this,"Accessing Photo Gallery",Toast.LENGTH_LONG).show();
        }
        else {
            //If the app has not the permission then asking for the permission
            requestStoragePermission();
        }

        //Create new intent for selection on photo
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }


    private boolean isReadStorageAllowed() {
        //Get the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted return false
        return false;
    }

    //Permission code checked in onRequestPermissionsResult
    private int STORAGE_PERMISSION_CODE = 44;

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously
            //Notify need for permissions
        }

        //Request permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code
        if(requestCode == STORAGE_PERMISSION_CODE){

            //Permission granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"You have granted access to the photo gallery.",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"You have denied access to your photo gallery.",Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==SELECT_IMAGE){
            Uri selectedImage = data.getData();
            String path = getPath(selectedImage);
            int rotateAngle = getCameraPhotoOrientation(StudentRegisterActivity.this, selectedImage, path);

            Bitmap bitmapImage = BitmapFactory.decodeFile(path);
//            RotateBitmap(bitmapImage, rotateAngle);
            ImageView image = (ImageView) findViewById(R.id.thumbnail);
            image.setImageBitmap(getResizedBitmap(bitmapImage, rotateAngle));
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int rotateAngle) {
        float aspectRatio = bm.getWidth() /
                (float) bm.getHeight();
        int width = 480;
        int height = Math.round(width / aspectRatio);

        bm = Bitmap.createScaledBitmap(
                bm, width, height, false);
//        return bm;
        return RotateBitmap(bm, rotateAngle);
    }

    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imageFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

        System.out.println("angle: " + rotate);
        return rotate;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public String getPath(Uri uri){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        return cursor.getString(columnIndex);
    }

}
