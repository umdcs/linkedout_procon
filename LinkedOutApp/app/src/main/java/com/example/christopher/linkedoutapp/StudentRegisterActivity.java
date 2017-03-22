package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;

public class StudentRegisterActivity extends AppCompatActivity {

    Spinner spinner;

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
        TextView nameText = (TextView)findViewById(R.id.displayStudentName);
        nameText.setText(profile.getName());

        TextView majorText = (TextView)findViewById(R.id.displayMajor);
        majorText.setText(profile.getMajor() + " Major");

        TextView graduationText = (TextView)findViewById(R.id.displayGraduation);
        graduationText.setText("Graduating " + profile.getGradTerm() + " " + profile.getGradYear());

        TextView locationText = (TextView)findViewById(R.id.displayLocation);
        locationText.setText(profile.getCity() + ", " + profile.getState());

    }


}
