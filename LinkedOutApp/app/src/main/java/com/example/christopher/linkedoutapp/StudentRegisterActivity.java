package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class StudentRegisterActivity extends AppCompatActivity {

    Profile_Pic pic = new Profile_Pic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
    }

    public void changeViewToEmployer(View view) {
        Intent intent = new Intent(this, EmployerRegisterActivity.class);
        startActivity(intent);
    }


    public void onClickRegister(View view) {

        //Sets intent to the student profile
        //Intent intent = new Intent(this, StudentDefaultView.class);


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

        Student_Profile profile = new Student_Profile(email, password, username, name,
                                                        city, state, gradterm, gradyear, major);


        fillInData(profile);

        //switches to the student profile page
        //startActivity(intent);
    }

    private void fillInData(Student_Profile profile) {

        setContentView(R.layout.fragment_profile);
        TextView nameText = (TextView) findViewById(R.id.displayStudentName);
        nameText.setText(profile.getName());

        TextView majorText = (TextView) findViewById(R.id.displayMajor);
        majorText.setText(profile.getMajor() + " Major");

        TextView graduationText = (TextView) findViewById(R.id.displayGraduation);
        graduationText.setText("Graduating " + profile.getGradTerm() + " " + profile.getGradYear());

        TextView locationText = (TextView) findViewById(R.id.displayLocation);
        locationText.setText(profile.getCity() + ", " + profile.getState());
    }


    public void onClickGallery(View view) {
        if(pic.isReadStorageAllowed(StudentRegisterActivity.this)){
            //If permission has already been granted show toast message.
            Toast.makeText(StudentRegisterActivity.this,"Accessing Photo Gallery",Toast.LENGTH_LONG).show();
        }
        else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode == pic.SELECT_IMAGE){
            Uri selectedImage = data.getData();
            String path = pic.getPath(selectedImage, this);
            int rotateAngle = pic.getPhotoOrientation(StudentRegisterActivity.this, selectedImage, path);

            Bitmap bitmapImage = BitmapFactory.decodeFile(path);
            ImageView image = (ImageView) findViewById(R.id.thumbnail);
            image.setImageBitmap(pic.getResizedBitmap(bitmapImage, rotateAngle));
        }
    }

}
