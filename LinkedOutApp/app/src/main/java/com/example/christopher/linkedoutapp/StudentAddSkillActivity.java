package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


public class StudentAddSkillActivity extends AppCompatActivity {


    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs; // = getSharedPreferences(STUDENT_PREFS, 0); //Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_addskill);
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
    }

    public void onClickSubmit(View view) {

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



}
