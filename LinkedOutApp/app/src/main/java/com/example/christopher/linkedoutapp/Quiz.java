package com.example.christopher.linkedoutapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

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
import java.util.Random;

public class Quiz extends AppCompatActivity {

    private TextView textView;
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

        /**
         * The following executes after the Asynchronous task is finished executing
         *
         * @param result the result from the query
         */
        protected void onPostExecute(String result) {
            JSONObject giraffe = new JSONObject();
            try {
                giraffe = new JSONObject(new JSONObject(result).getString("math"));
                String question = giraffe.getString("question");
                textView.setText(question);
                Random j = new Random();
                int answer = 1+j.nextInt(4);
                String radio = "radioButton" + answer;
                int resID = getResources().getIdentifier(radio, "id", getPackageName());
                RadioButton b = (RadioButton)findViewById(resID);
                b.setText(giraffe.getString("answer"));
                for(int i = 1; i <= 4; i++){
                    if(i!=answer){
                        resID = getResources().getIdentifier("radioButton"+i, "id", getPackageName());
                        int response = 1+j.nextInt(10);
                        while(giraffe.getString("answer").equals(""+response)){
                            response = 1+j.nextInt(10);
                        }
                        b = ((RadioButton)findViewById(resID));
                        //For some reason the setText doesn't work with pure integers, so that's why I'm adding empty string
                        b.setText("" + response);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
        /**
         * Acts as the onClick callback for the REST GET Button. The code will generate a REST GET
         * action to the REST Server.
         */
        public void restGET() {
            new HTTPAsyncTask().execute("http://10.0.2.2:4321/quizData", "GET");
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        textView = (TextView) findViewById(R.id.question);
        restGET();
    }
}
