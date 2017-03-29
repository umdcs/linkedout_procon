package com.example.christopher.linkedoutapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;



public class StudentRegisterActivity extends AppCompatActivity {

    //Photo code used when strting the intent to open the gallery.
    public final static int PICK_PHOTO_CODE = 1;
    //To be checked against request code in onActivityResult
    private static final int SELECT_IMAGE = 1;

    //Permission code checked in onRequestPermissionsResult
    private int STORAGE_PERMISSION_CODE = 44;

    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs; // = getSharedPreferences(STUDENT_PREFS, 0); //Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
    }

    public void changeViewToEmployer(View view) {
        Intent intent = new Intent(this, EmployerRegisterActivity.class);
        startActivity(intent);
    }


    public void onClickRegister(View view) {

        //Sets intent to the student profile
        Intent intent = new Intent(this, StudentDefaultView.class);


        //Creates strings from entered text in student profile
        String name = ((EditText) findViewById(R.id.studentName)).getText().toString();
        String username = ((EditText) findViewById(R.id.studentUsername)).getText().toString();
        String email = ((EditText) findViewById(R.id.studentEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.studentPassword)).getText().toString();
        String city = ((EditText) findViewById(R.id.registerStudentCity)).getText().toString();
        String gradyear = ((EditText) findViewById(R.id.registerStudentGradYear)).getText().toString();
        String major = ((EditText) findViewById(R.id.registerStudentMajor)).getText().toString();

        //Grab the selected spinner info
        Spinner stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        String state = stateSpinner.getSelectedItem().toString();
        Spinner gradTermSpinner = (Spinner) findViewById(R.id.FallSpringSpinner);
        String gradterm = gradTermSpinner.getSelectedItem().toString();

        //rest functions?

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("email", email);
        editor.putString("fullName", name);
        editor.putString("city", city);
        editor.putString("state", state);
        editor.putString("gradTerm", gradterm);
        editor.putString("gradYear", gradyear);
        editor.putString("major", major);
        while(!editor.commit());

        //switches to the student profile page
        startActivity(intent);
    }

    public void onClickGallery(View view) {
        if(isReadStorageAllowed()){
            //If permission has already been granted show toast   message.
            Toast.makeText(StudentRegisterActivity.this,"Accessing Photo Gallery",Toast.LENGTH_LONG).show();
        }
        else {
            //If you don't have permission, ask for it.
            requestStoragePermission();
        }
        //Create new intent for selection on photo
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            //Open the photo gallery
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==SELECT_IMAGE){
            Uri selectedImage = data.getData();
            String path = getPath(selectedImage);
            int rotateAngle = getPhotoOrientation(StudentRegisterActivity.this, selectedImage, path);

            Bitmap bitmapImage = BitmapFactory.decodeFile(path);
            ImageView image = (ImageView) findViewById(R.id.thumbnail);
            image.setImageBitmap(getResizedBitmap(bitmapImage, rotateAngle));
        }
    }

    //Get path to image file.
    public String getPath(Uri uri){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        return cursor.getString(columnIndex);
    }

    //Check for read storage permission.
    private boolean isReadStorageAllowed() {
        //Get the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted return true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted return false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously
            //Notify the user they have denied the permission previously
            Toast.makeText(StudentRegisterActivity.this,"You denied this permission previously.",Toast.LENGTH_LONG).show();
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

                //Displaying a toast if permission is granted.
                Toast.makeText(this,"You have granted access to the photo gallery.",Toast.LENGTH_LONG).show();
            }else{
                //Displaying toast if permission is not granted.
                Toast.makeText(this,"You have denied access to your photo gallery.",Toast.LENGTH_LONG).show();
            }
        }
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int rotateAngle) {
        float aspectRatio = bm.getWidth() /
                (float) bm.getHeight();
        int width = 480;
        int height = Math.round(width / aspectRatio);

        bm = Bitmap.createScaledBitmap(
                bm, width, height, false);
        return RotateBitmap(bm, rotateAngle);
    }

    public int getPhotoOrientation(Context context, Uri imageUri, String imagePath){
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

}
