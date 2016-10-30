package com.example.christopher.linkedoutapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.net.HttpURLConnection;



/** This activity will include how the students will grab questions from the employers and also
 *  how the students will post their answers back to the server to be reviewed. It will include
 *  how the json data will be formatted.
 */
public class StudentViewActivity extends AppCompatActivity {



    private TextView textView;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private class HTTPAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection serverConnection = null;
            InputStream is = null;

            Log.d("Debug:", "Attempting to connect to: " + params[0]);

            try {
                URL url = new URL( params[0] );
                serverConnection = (HttpURLConnection) url.openConnection();
                serverConnection.setRequestMethod(params[1]);
                if (params[1].equals("POST") ||
                        params[1].equals("PUT") ||
                        params[1].equals("DELETE")) {
                    Log.d("DEBUG POST/PUT/DELETE:", "In post: params[0]=" + params[0] + ", params[1]=" + params[1] + ", params[2]=" + params[2]);
                    serverConnection.setDoOutput(true);
                    serverConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                    // params[2] contains the JSON String to send, make sure we send the
                    // content length to be the json string length
                    serverConnection.setRequestProperty("Content-Length", "" +
                            Integer.toString(params[2].toString().getBytes().length));

                    // Send POST data that was provided.
                    DataOutputStream out = new DataOutputStream(serverConnection.getOutputStream());
                    out.writeBytes(params[2].toString());
                    out.flush();
                    out.close();
                }

                int responseCode = serverConnection.getResponseCode();
                Log.d("Debug:", "\nSending " + params[1] + " request to URL : " + params[0]);
                Log.d("Debug: ", "Response Code : " + responseCode);

                is = serverConnection.getInputStream();

                if (params[1] == "GET" || params[1] == "POST" || params[1] == "PUT" || params[1] == "DELETE") {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    try {
                        JSONObject jsonData = new JSONObject(sb.toString());
                        return jsonData.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverConnection.disconnect();
            }

            return "Should not get to this if the data has been sent/received correctly!";
        }

    }


    /**
     * Acts as the onClick callback for the REST GET Button. The code will generate a REST GET
     * action to the REST Server.
     * This will be the function that pull the quiz data from the server.
     * take data and send to another function to be formatted? not if void.
     *
     * @param view : JsonObject for question string, and type.
     */
    public void restGET(View view) {
        new HTTPAsyncTask().execute("http://10.0.2.2:4321/userData", "GET");
    }

    /** Used when user clicks on a quiz: quizzes are stored on serever not device.
     * Acts as the onClick callback for the REST POST Button. The code will generate a REST POST
     * action to the REST Server.
     * This will be the function that sends out quiz Json data to the server to be corrected.
     * @param: Json data to send
     *
     *
     * @param view
     */
    public void restPOST(View view) {

        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("type", "integerType");       //first integer will be 0-2 for true/false,
                                                        // multiple choice, and other.

            jsonParam.put("answer", "intergerAnswer");  //button answer will be 0-3 for A-B: will
                                                        // watch with button and quiz view
            jsonParam.put("enable", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG:", jsonParam.toString());

        //needs to change IP address automatically
        //add IP address permissiom?
        new HTTPAsyncTask().execute("http://10.0.2.2:4321/userData", "POST", jsonParam.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);
    }

    void buttonSetUp

    private class quiz {

        /**This will be the funciton that sends out quiz Json data to the server to be corrected.
        * @param: Json data to send
        *
        */
        void send(){


        }

        /** This will be the function that pull the quiz data from the server.
        * @param: JsonObject for question string, and type.
        * take data and send to another function to be formatted?
        */
        void pull(){


        }

        /** Checks to see if
        * @Param:Question state : is true/false or is mutliple choice.
        *  @output: int holds question type.
        *          0: is true false
        *          1: is multple choice
        *          2: is short answer
        *
        */
        int isTrueFalse(int state){
            int quizState = state;
            return quizState;
        }

        void format(){
            isTrueFalse();
        }
    }
    }
