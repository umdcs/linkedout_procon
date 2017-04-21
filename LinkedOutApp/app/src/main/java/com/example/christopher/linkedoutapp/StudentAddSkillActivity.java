package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StudentAddSkillActivity extends AppCompatActivity {

    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs; // = getSharedPreferences(STUDENT_PREFS, 0); //Context.MODE_PRIVATE);
    RESTful_API nodeServer;

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

        //Get the skill count for this user. Used because the maximum skills allowed is currently 5.
        int skillCount = prefs.getInt("skillCount", 0);

        //If they have not yet reached the maximum skill count:
        //Increment the skill count, and add the skill data to their respective spots in the
        //shared preferences.
        if(skillCount < 5) {
            skillCount++;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("skillCount", skillCount);
            editor.putString("skillName" + skillCount, skillName);
            editor.putString("skillHow" + skillCount, "Skill obtained from: " + skillHow);
            editor.putString("skillDesc" + skillCount, "Description: " + skillDescription);

            while (!editor.commit()) ;
        }

        //If they already have 5 skills entered, notify them.
        else{
            Toast.makeText(this,"You already have the maximum number of skills.",Toast.LENGTH_LONG).show();
        }

        nodeServer.addSkillPOST(prefs);

        //switches to the student profile page
        startActivity(intent);
    }


}
