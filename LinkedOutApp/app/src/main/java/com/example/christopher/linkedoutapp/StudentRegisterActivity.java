package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;

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
        Intent intent = new Intent(this, StudentDefaultView.class);

        String name = ((EditText) findViewById(R.id.studentName)).getText().toString();
        String username = ((EditText) findViewById(R.id.studentUsername)).getText().toString();
        String email = ((EditText) findViewById(R.id.studentEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.studentPassword)).getText().toString();
        String city = ((EditText) findViewById(R.id.registerStudentCity)).getText().toString();
        int gradyear = Integer.parseInt( ((EditText) findViewById(R.id.registerStudentGradYear)).getText().toString() );
        String major = ((EditText) findViewById(R.id.registerStudentMajor)).getText().toString();

        //Need to grab the spinner select info
        String state = "MN";
        String gradterm = "2018";

        //rest functions?

        Student_Profile profile = new Student_Profile(email, password, username, name,
                                                        city, state, gradterm, gradyear, major);

        //switches to the student profile page
        startActivity(intent);

    }


}
