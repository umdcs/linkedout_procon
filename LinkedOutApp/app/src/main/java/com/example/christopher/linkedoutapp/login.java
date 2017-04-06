package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import org.json.JSONArray;
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
    private SharedPreferences prefs;
    private RESTful_API nodeServer;
    private JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
        nodeServer = new RESTful_API();
        Log.d("in function ", "onCreate");
    }

    /* This method is called in the restAPI, to ensure the async response has finished
     * and sets response from server.
     *
     * @param data The login local is set to the JSONObject returned from the node server
    * */
    public void setData(JSONObject d) {
        data = d;
        Log.d("setData", data.toString());
        String s = data.optString("username");
        Log.d("optString(username)", s );
    }


    public void onClickStudent(View view){
        data = new JSONObject();
        String email = ((EditText) findViewById(R.id.loginEmailText)).getText().toString();
        String password = ((EditText) findViewById(R.id.studentPassword)).getText().toString();
        nodeServer.loginGET(email, password, this);
    }

    public void onClickEmployer(View view){
        Intent intent = new Intent(this, EmployerDefaultView.class);
        startActivity(intent);
    }

    /* This method is called from the restAPI to update the local sharedPrefs so
     * that the student activity can begin.
     */
    public void updatePrefsAndStart() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", data.optString("username"));
        editor.putString("password", data.optString("password"));
        editor.putString("email", data.optString("email"));
        editor.putString("fullName", data.optString("fullName"));
        editor.putString("city", data.optString("city"));
        editor.putString("state", data.optString("state"));
        editor.putString("gradTerm", data.optString("gradTerm"));
        editor.putString("gradYear", data.optString("gradYear"));
        editor.putString("major", data.optString("major"));
        editor.putString("proflePic", data.optString("photo"));
        Log.d("DEBUG", data.optString("photo"));
        while(!editor.commit());

        Intent intent = new Intent(this, StudentDefaultView.class);
        startActivity(intent);
    }

}
