package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Chris on 4/10/2017.
 */

public class StudentModSettings extends AppCompatActivity {

    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs;
    ProfilePic pic;
    RESTful_API nodeServer;

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_modsettings);
    } */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_modsettings);
        pic = new ProfilePic(null);
        //pic.setBitmap(pic.getDecodedBitmap(prefs.getString("profilePic", "ERROR")));
        nodeServer = new RESTful_API();
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
        fillInData(prefs);
    }

    //A user can delete their profile.
    public void onClickDelete(View view) {
        nodeServer.deleteProfile(prefs.getString("email","") );
        Intent resumeMain = new Intent(StudentModSettings.this, MainActivity.class);
        resumeMain.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(resumeMain);
    }

    //Save all of the changes made to the user profile.
    public void onClickSave(View view) {
        // Need the old email as a reference
        String oldEmail = prefs.getString("email","");

        //EditText fields connected to the fields in the student profile.
        EditText fullName = (EditText) findViewById(R.id.studentNameSettings);
        EditText email = (EditText) findViewById(R.id.studentEmailSettings);
        EditText major = (EditText) findViewById(R.id.studentMajorSettings);
        EditText city = (EditText) findViewById(R.id.studentCitySettings);
        EditText gradYear = (EditText) findViewById(R.id.studentGradYearSettings);
        EditText un = (EditText) findViewById(R.id.studentUsernameSettings);
        EditText pw = (EditText) findViewById(R.id.studentPasswordSettings);

        //Spinners connected to the state, and graduation term spinners.
        Spinner stateSpinner = (Spinner) findViewById(R.id.stateSpinnerSettings);
        Spinner gradTermSpinner = (Spinner) findViewById(R.id.FallSpringSpinnerSettings);

        //Use the shared preferences to populate all fields.
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", un.getText().toString() );
        editor.putString("password", pw.getText().toString() );
        editor.putString("email", email.getText().toString() );
        editor.putString("fullName", fullName.getText().toString() );
        editor.putString("city", city.getText().toString() );
        editor.putString("state", stateSpinner.getSelectedItem().toString() );
        editor.putString("gradTerm", gradTermSpinner.getSelectedItem().toString() );
        editor.putString("gradYear", gradYear.getText().toString() );
        editor.putString("major", major.getText().toString() );
        if(pic.getBitmap()!=null) {
            editor.putString("profilePic", pic.getEncodedBitmap());
        }
        while (!editor.commit()) ;

        nodeServer.modifySettings(prefs, oldEmail);

        Intent intent = new Intent(this, StudentDefaultView.class);
        startActivity(intent);

        // This is to resume the studentDefaultView, which it does, however I can't get onResume
        // to work in StudentDefaultView to update the views again...
        /*Intent resumeStudent = new Intent(StudentModSettings.this, StudentDefaultView.class);
        resumeStudent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(resumeStudent);*/
    }

    private void fillInData(SharedPreferences prefs) {
        //Edittext's connected to the fields in the student profile.
        EditText fullName = (EditText) findViewById(R.id.studentNameSettings);
        EditText email = (EditText) findViewById(R.id.studentEmailSettings);
        EditText major = (EditText) findViewById(R.id.studentMajorSettings);
        EditText city = (EditText) findViewById(R.id.studentCitySettings);
        EditText gradYear = (EditText) findViewById(R.id.studentGradYearSettings);
        EditText un = (EditText) findViewById(R.id.studentUsernameSettings);
        EditText pw = (EditText) findViewById(R.id.studentPasswordSettings);

        //Spinners for displaying states, and terms of graduation.
        Spinner stateSpinner = (Spinner) findViewById(R.id.stateSpinnerSettings);
        Spinner gradTermSpinner = (Spinner) findViewById(R.id.FallSpringSpinnerSettings);
        ArrayAdapter stateAdapter = (ArrayAdapter) stateSpinner.getAdapter();
        ArrayAdapter gradTermAdapter = (ArrayAdapter) gradTermSpinner.getAdapter();
        int statePos = stateAdapter.getPosition(prefs.getString("state","") );
        int gradPos = gradTermAdapter.getPosition(prefs.getString("gradTerm","") );

        //Getting data from the shared preferences to populate the student profile fields.
        fullName.setText(prefs.getString("fullName", ""));
        email.setText(prefs.getString("email",""));
        major.setText(prefs.getString("major",""));
        city.setText(prefs.getString("city",""));
        gradYear.setText(prefs.getString("gradYear",""));
        un.setText(prefs.getString("username",""));
        pw.setText(prefs.getString("password",""));
        stateSpinner.setSelection(statePos);
        gradTermSpinner.setSelection(gradPos);

        //Create new profile pic object.
        pic = new ProfilePic(null);
        ImageView profilePic = (ImageView) findViewById(R.id.thumbnailSettings);
        String imgString = prefs.getString("profilePic", "");
        //If the image string is blank, set the profile pic in the layout to the default
        //camera drawable image.
        if(imgString == "")
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
        //Otherwise, decode the image string, and use that bitmap for the profile pic.
        else {
            pic.setBitmap(pic.getDecodedBitmap(imgString));
            profilePic.setImageBitmap(pic.getResizedBitmap());
        }
    }

    public void onClickGalleryMod(View view) {
        //Check if the user has granted read storage permissions.
        if (pic.isReadStorageAllowed(StudentModSettings.this)) {
            //If permission has already been granted show toast message.
            Toast.makeText(StudentModSettings.this, "Accessing Photo Gallery", Toast.LENGTH_LONG).show();
        } else {
            //If you don't have permission, ask for it.
            pic.requestStoragePermission(StudentModSettings.this);
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
            int rotateAngle = pic.getPhotoOrientation(StudentModSettings.this, selectedImage, path);
            ImageView image = (ImageView) findViewById(R.id.thumbnailSettings);
            pic.setBitmap(pic.rotateBitmap(rotateAngle));
            image.setImageBitmap(pic.getResizedBitmap());
        }
    }

}
