package com.example.christopher.linkedoutapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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



public class Quiz extends AppCompatActivity{

    private String Server = "http://lempo.d.umn.edu:4531/quizData";
    private String Server2 = "http://10.0.2.2:4321/quizData";
    private TextView textView;
    private RadioButton rb;
    private RadioGroup rg;
    private String question; //question to be stored and sent back along with the users answer
    private String answer;

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

        /** This function passes the json data to a quiz layout and variables for helper function
         * The following executes after the Asynchronous task is finished executing
         *
         * @param result the result from the query
         */
        protected void onPostExecute(String result) {

            //this whole section is basically taking in the jsondata and setting to different buttons to be displayed. Hopefully dynamically.
            JSONObject jsonData = new JSONObject(); //holds the json data pulled from server

            try {
                //This gets the hardcoded quiz question of type "math"
                jsonData = new JSONObject(new JSONObject(result).getString("math"));

                //will grab the format as a string
                String quizFormat = jsonData.getString("quizFormat");

                //This is the radio group id
                rg = (RadioGroup) findViewById(R.id.rGroup);

                //This grabs the hardcoded quiz
                String quizQuestion = jsonData.getString("quizQuestion");
                System.out.println(quizFormat);
                textView.setText(quizQuestion);
                //sets private variable to the string of the current question
                setQuestion(quizQuestion);

                //creates the quiz/answer variable
                String quizAnswer = jsonData.getString("quizAnswer");
                setAnswer(quizAnswer);

                //hardcoded multiple choice question
                isMultipleChoice(jsonData);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }//******************End onPost()*************************************


    }//****************End AsyncTask()****************************************

    /**
     * Acts as the onClick callback for the REST GET Button. The code will generate a REST GET
     * action to the REST Server.
     */
    public void restGET() {
        new HTTPAsyncTask().execute(Server, "GET");
    }

    /**
     * Acts as the onClick callback for the REST POST Button. The code will generate a REST POST
     * action to the REST Server. It is called when the submit button is pressed.
     *
     * @param view
     *
     */
    public void restPOST(View view) {

        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("question", getQuestion());
            jsonParam.put("answer", isCorrect());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG:", jsonParam.toString());
        new HTTPAsyncTask().execute(Server, "POST", jsonParam.toString());
    }

    /** This function will tell if the question was answered correctly or not
     *
     *@Return: A true of false for the answer being correct or incorrect
     *
     */
    public String isCorrect(){
        RadioButton studentAnswer;
        studentAnswer = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        if(studentAnswer.getText().equals(getAnswer())){
            return "true"; //correct
        }
        else {
            return "false"; //incorrect
        }
    }

    /** This function will find out which radio button was clicked and return it
     * @Param: View v
     *
     * @Return: int of radio button clicked
     */
    public int rbClick(View view){
        int radioButtonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radioButtonid);
        return radioButtonid;
    }

    /**This function sets the question
     *
     * @param quizQuestion
     */
    public void setQuestion(String quizQuestion){
            question = quizQuestion;
    }

    /**This function gets the question
     *
     * @return question
     */

    public String getQuestion()
    {
        return question;
    }

    /**This function gets the answer
     *
     * @return answer
     */
    public String getAnswer()
    {
        return answer;
    }

    /**This question sets the answer
     *
     * @param quizAnswer
     */
    public void setAnswer(String quizAnswer){
        answer = quizAnswer;
    }


    /**This function creates a multiple choice question format
     * @param: String quizFormat
     *
     */
    public void isMultipleChoice(JSONObject jsonData){
        //create four buttons
        String quizAnswerChoiceOne = null;
        try {
            quizAnswerChoiceOne = jsonData.getString("quizAnswerChoiceOne");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //RadioButton buttonAnswerChoiceOne = (RadioButton)findViewById();
        textView = (TextView) findViewById(R.id.quizAnswerChoiceOne);
        textView.setText(quizAnswerChoiceOne);

        String quizAnswerChoiceTwo = null;
        try {
            quizAnswerChoiceTwo = jsonData.getString("quizAnswerChoiceTwo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView = (TextView) findViewById(R.id.quizAnswerChoiceTwo);
        textView.setText(quizAnswerChoiceTwo);

        String quizAnswerChoiceThree = null;
        try {
            quizAnswerChoiceThree = jsonData.getString("quizAnswerChoiceThree");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView = (TextView) findViewById(R.id.quizAnswerChoiceThree);
        textView.setText(quizAnswerChoiceThree);

        String quizAnswerChoiceFour = null;
        try {
            quizAnswerChoiceFour = jsonData.getString("quizAnswerChoiceFour");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView = (TextView) findViewById(R.id.quizAnswerChoiceFour);
        textView.setText(quizAnswerChoiceFour);
    }


/*
>>>>>>> origin/Sprint1-Clarence
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        textView = (TextView) findViewById(R.id.quizQuestion);
        restGET();

        rg = (RadioGroup) findViewById(R.id.rGroup);

    }

}