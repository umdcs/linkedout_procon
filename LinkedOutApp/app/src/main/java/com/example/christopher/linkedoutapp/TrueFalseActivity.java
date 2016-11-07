package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

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

public class TrueFalseActivity extends AppCompatActivity {
    private EditText editText;

    public void sendMessage(View view) {
        //When the 'Save Question' button is pressed, the data gets posted to the node.js server
        restPOST(view);
    }

    private class HTTPAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection serverConnection = null;
            InputStream is = null;

            Log.d("Debug:", "Attempting to connect to: " + params[0]);

            try {
                URL url = new URL(params[0]);
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
                        JSONObject jsonData = new JSONObject(sb.toString()); //json object
                        return jsonData.toString();                          //returns json data
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


        /**
         * The following executes after the Asynchronous task is finished executing
         *
         * @param result the result from the query
         */
        protected void onPostExecute(String result) {

        }

    }//******************End onPost()*************************************


    //****************End AsyncTask()****************************************


    public void restGET() {
        new HTTPAsyncTask().execute("http://10.0.2.2:4321/quizData", "GET");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);
    }




    public void restPOST(View view){
        editText = (EditText) findViewById(R.id.editText4);
        String question = editText.getText().toString();
        String answer="";
        RadioButton TRUE, FALSE;
        TRUE = (RadioButton) findViewById(R.id.TRUE);
        FALSE = (RadioButton) findViewById(R.id.FALSE);
        if(TRUE.isChecked()){
            answer = "True";
        }
        else if(FALSE.isChecked()){
            answer = "False";
        }
        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("quizFormat", "TrueFalse");
            jsonParam.put("quizQuestion", question);
            jsonParam.put("quizAnswer", answer);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonParam.toString());
        new HTTPAsyncTask().execute("http://10.0.2.2:4321/quizData", "POST", jsonParam.toString());
        super.onBackPressed();
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }
}


