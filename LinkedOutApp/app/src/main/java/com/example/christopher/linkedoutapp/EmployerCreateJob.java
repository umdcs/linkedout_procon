package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
//array imports
import com.example.christopher.linkedoutapp.QuizModel.QuizModel;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployerCreateJob extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Fragment_SA.OnFragmentInteractionListener_SA, Fragment_TF.OnFragmentInteractionListener_TF, Fragment_MC.OnFragmentInteractionListener_MC{

    Spinner spinner;
    String question,answer,option1,option2,option3, jobName;
    String name;
    String format;
    private String Server = "http://lempo.d.umn.edu:4531/arrayQuizData";
    private String Server2 = "http://10.0.2.2:4321/arrayQuizData";

    ArrayList<String[]> quiz = new ArrayList<String[]>();

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
         *
         * @param result the result from the query
         */
        protected void onPostExecute(String result) {


        }//******************End onPost()*************************************


    }//****************End AsyncTask()****************************************

    public void restPOST() {

        JSONArray finalJSONAraay = new JSONArray(); // the outer most layer of array (for whole quiz)
        JSONObject finalJSONObject = new JSONObject(); //the outer most layer of object (for all quizzes)

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArrayChoices = new JSONArray();
        JSONObject jsonObjectChoice1 = new JSONObject();
        JSONObject jsonObjectChoice2 = new JSONObject();
        JSONObject jsonObjectChoice3 = new JSONObject();


        List<JSONObject> jsonObjectList = new ArrayList<>();

        try {

            //choice objects
            jsonObjectChoice1.put("choice", option1);
            jsonObjectChoice2.put("choice", option2);
            jsonObjectChoice3.put("choice", option3);

            //array for choice objects
            jsonArrayChoices.put(jsonObjectChoice1);
            jsonArrayChoices.put(jsonObjectChoice2);
            jsonArrayChoices.put(jsonObjectChoice3);

            //put array jsonObject

            //one quiz
            jsonObject.put("quizAnswer", answer);
            jsonObject.put("quizQuestion", question);
            jsonObject.put("choiceList", jsonArrayChoices);

            //puts json objects into the array to make up a quiz
            finalJSONAraay.put(jsonObject);

            //puts quiz into final big object
            finalJSONObject.put("quizzes", finalJSONAraay);


            //finalJSONObject.put(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG:", finalJSONObject.toString());
        new HTTPAsyncTask().execute(Server2, "POST", finalJSONObject.toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_create_job);


        EditText name = (EditText) findViewById(R.id.jobName);
        jobName = name.getText().toString();

        //implement question spinner
        spinner = (Spinner) findViewById(R.id.questions_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.questions_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int qv = parent.getSelectedItemPosition();
        String value  = spinner.getSelectedItem().toString();
        Log.d("Debug: ", "position on array: : " + value + "," + "Position: " + qv );

        if (qv == 1)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, Fragment_SA.newInstance())
                    .commit();

            //onFragmentInteraction_SA(Q,A);
        }
        else if (qv == 2)
        {
            Log.d("Debug: ", "2nd fragment " );
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, Fragment_TF.newInstance())
                    .commit();
        }
        else if (qv == 3)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, Fragment_MC.newInstance())
                    .commit();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onFragmentInteraction_SA(String value, String value1) {

        question = value;
        answer = value1;
        Log.d("Debug: ", "question and answer values --> Q:"+ question + " Answer:" + answer);

        //save values


        String[] saq = {"SA",question,answer};
        quiz.add(saq);


        for (String[] strArr : quiz) {
            Log.d("Debug: ", "current array is : " + Arrays.toString(strArr));
        }
        restPOST();
    }

    @Override
    public void onFragmentInteraction_TF(String value, String value1) {
        question = value;
        answer = value1;

        Log.d("Debug: ", "question and answer values --> Q:"+ question + " Answer:" + answer);

        //save values to array
        String[] saq2 = {"TF",question,answer};
        quiz.add(saq2);


        for (String[] strArr : quiz) {
            //Log.d("Debug: ", "current array is : " + Arrays.toString(strArr));
            Log.d("Debug: ", Arrays.toString(strArr) );
        }
        restPOST();
    }

    @Override
    public void onFragmentInteraction_MC(String value, String value1, String value2, String value3) {
        question = value;
        option1 = value1;
        option2 = value2;
        option3 = value3;


        Log.d("Debug: ", "question and answer values --> Q:" + question + " Answer:" + answer);

        //save values to array
        String[] saq3 = {"MC", question, option1, option2, option3};
        quiz.add(saq3);


        for (String[] strArr : quiz) {
            //Log.d("Debug: ", "current array is : " + Arrays.toString(strArr));
            Log.d("Debug: ", Arrays.toString(strArr));
        }
        restPOST();
    }

    public void onClickCancel(View view){
        Intent intent = new Intent(this, EmployerDefaultView.class);
        startActivity(intent);
    }


}
