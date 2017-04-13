package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class StudentAddSkillActivity extends AppCompatActivity {

    private final static String STUDENT_PREFS = "Student Preferences";
    private SharedPreferences prefs;
    private RESTful_API nodeServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_addskill);
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
        nodeServer = new RESTful_API();
    }

    public void onClickSubmit(View view) {

        //Sets intent to the student profile
        Intent intent = new Intent(this, StudentDefaultView.class);

        //Creates strings from entered text in new skill
        String skillName = ((EditText) findViewById(R.id.newSkillName)).getText().toString();
        String skillHow = ((EditText) findViewById(R.id.newSkillHow)).getText().toString();
        String skillDescription = ((EditText) findViewById(R.id.newSkillDescription)).getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("skillname", skillName);
        editor.putString("skillhow", skillHow);
        editor.putString("skilldescription", skillDescription);

        //Need while loop for SharedPreferences to work
        //noinspection StatementWithEmptyBody
        while(!editor.commit());


        nodeServer.registerStudentPOST(prefs);

        //switches to the student profile page
        startActivity(intent);
    }


}
