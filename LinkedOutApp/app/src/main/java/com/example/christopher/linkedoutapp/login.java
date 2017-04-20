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
    }

    public JSONObject getData() {
        return data;
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
        int skillCount = 0;

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
        editor.putString("profilePic", data.optString("photo"));

        editor.putString("skillName1", data.optString("skill1"));
        editor.putString("skillHow1", data.optString("obtained1"));
        editor.putString("skillDesc1", data.optString("description1"));
        if(prefs.getString("skillName1", "") != "") skillCount = 1;

        editor.putString("skillName2", data.optString("skill2"));
        editor.putString("skillHow2", data.optString("obtained2"));
        editor.putString("skillDesc2", data.optString("description2"));
        if(prefs.getString("skillName2", "") != "") skillCount = 2;

        editor.putString("skillName3", data.optString("skill3"));
        editor.putString("skillHow3", data.optString("obtained3"));
        editor.putString("skillDesc3", data.optString("description3"));
        if(prefs.getString("skillName3", "") != "") skillCount = 3;

        editor.putString("skillName4", data.optString("skill4"));
        editor.putString("skillHow4", data.optString("obtained4"));
        editor.putString("skillDesc4", data.optString("description4"));
        if(prefs.getString("skillName4", "") != "") skillCount = 4;

        editor.putString("skillName5", data.optString("skill5"));
        editor.putString("skillHow5", data.optString("obtained5"));
        editor.putString("skillDesc5", data.optString("description5"));
        if(prefs.getString("skillName5", "") != "") skillCount = 5;

        editor.putInt("skillCount", skillCount);

        while(!editor.commit());

        Intent intent = new Intent(this, StudentDefaultView.class);
        startActivity(intent);
    }

}
