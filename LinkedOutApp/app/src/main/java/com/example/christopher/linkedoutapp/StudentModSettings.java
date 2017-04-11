package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Chris on 4/10/2017.
 */

public class StudentModSettings extends AppCompatActivity {

    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs; // = getSharedPreferences(STUDENT_PREFS, 0); //Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.NEW_XML_LAYOUT);
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
    }

    public void onClickSave(View view) {

        //Sets intent to the student profile
        Intent intent = new Intent(this, StudentDefaultView.class);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("skillname", skillName);
        editor.putString("skillhow", skillHow);
        editor.putString("skilldescription", skillDescription);
        while(!editor.commit());

        //switches to the student profile page
        startActivity(intent);
    }


}
