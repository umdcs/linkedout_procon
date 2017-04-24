package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class StudentRegisterActivity extends AppCompatActivity {


    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs;
    RESTful_API nodeServer;

    ProfilePic pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
        nodeServer = new RESTful_API();
        pic = new ProfilePic(null);
    }

    public void changeViewToEmployer(View view) {
        Intent intent = new Intent(this, EmployerRegisterActivity.class);
        startActivity(intent);
    }


    public void onClickRegister(View view) {

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

        // Update the local prefs
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
        editor.putString("skillName1", "");
        editor.putString("skillName2", "");
        editor.putString("skillName3", "");
        editor.putString("skillName4", "");
        editor.putString("skillName5", "");
        editor.putString("skillHow1", "");
        editor.putString("skillHow2", "");
        editor.putString("skillHow3", "");
        editor.putString("skillHow4", "");
        editor.putString("skillHow5", "");
        editor.putString("skillDesc1", "");
        editor.putString("skillDesc2", "");
        editor.putString("skillDesc3", "");
        editor.putString("skillDesc4", "");
        editor.putString("skillDesc5", "");
        editor.putInt("skillCount", 0);
        if(pic.getBitmap() == null)
            editor.putString("profilePic", "");
        else
        editor.putString("profilePic", pic.getEncodedBitmap());
        while (!editor.commit()) ;

        /* Update server. NOTE: Since the server is having issues sending a response
           it will not notify if the email is in use (ie. info not stored...)
           The app will still make a new *local* profile to display, but you will
           not be able to login with this info (as it was never stored...)
        */
        nodeServer.registerStudentPOST(prefs, this);

        //Sets intent to the student profile
        Intent intent = new Intent(this, StudentDefaultView.class);
        //switches to the student profile page
        startActivity(intent);
    }

/*  The server is refusing to send a response, this needs to be fixed...
    // Should only start the new profile if no error from server.
    public void startProfile() {
        //Sets intent to the student profile
        Intent intent = new Intent(this, StudentDefaultView.class);
        //switches to the student profile page
        startActivity(intent);
    }

    // This function is called if the server sends back an ERROR message
    public void signalError(String error) {
        Toast.makeText(StudentRegisterActivity.this, error, Toast.LENGTH_LONG).show();
    } */

    public void onClickGallery(View view) {
        if (pic.isReadStorageAllowed(StudentRegisterActivity.this)) {
            //If permission has already been granted show toast message.
            Toast.makeText(StudentRegisterActivity.this, "Accessing Photo Gallery", Toast.LENGTH_LONG).show();
        } else {
            //If you don't have permission, ask for it.
            pic.requestStoragePermission(StudentRegisterActivity.this);
        }
        //Create new intent for selection on photo
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            //Open the photo gallery
            startActivityForResult(intent, pic.PICK_PHOTO_CODE);
        }
    }

    //This happens when a user selects a photo from their gallery, and is called automatically.
    //If the result code and request code are as expected (rare to have an issue here), the
    //data that is returned is used to get the path to the image, and the path is used to be
    //decoded into a bitmap.
    //The bitmap is then set as the profile pic thumbnail.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == pic.SELECT_IMAGE) {
            Uri selectedImage = data.getData();
            String path = pic.getPath(selectedImage, this);
            pic.setPath(path);
            Bitmap bitmapImage = BitmapFactory.decodeFile(path);
            pic.setBitmap(bitmapImage);
            int rotateAngle = pic.getPhotoOrientation(StudentRegisterActivity.this, selectedImage, path);
            pic.setBitmap(pic.rotateBitmap(rotateAngle));
            ImageView image = (ImageView) findViewById(R.id.thumbnail);
            image.setImageBitmap(pic.getResizedBitmap());
        }
    }
}


