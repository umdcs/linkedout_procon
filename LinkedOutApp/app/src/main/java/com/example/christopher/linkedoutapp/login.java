package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import static org.json.JSONObject.NULL;

public class login extends AppCompatActivity {

    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs;
    RESTful_API nodeServer;
    JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
        nodeServer = new RESTful_API();
        Log.d("in function ", "onCreate");
    }

    // fix this !!
    public void onClickStudent(View view){
        String email = ((EditText) findViewById(R.id.loginEmailText)).getText().toString();
        String password = ((EditText) findViewById(R.id.studentPassword)).getText().toString();
        Log.d("email and password: ", email + " " + password);
        /*data =*/ nodeServer.loginGET(email, password);
        /*if(data != NULL)
        Log.d("Node Data: ", data.toString());
        */
        Intent intent = new Intent(this, StudentDefaultView.class);
        startActivity(intent);
    }

    public void onClickEmployer(View view){
        Intent intent = new Intent(this, EmployerDefaultView.class);
        startActivity(intent);
    }
}
