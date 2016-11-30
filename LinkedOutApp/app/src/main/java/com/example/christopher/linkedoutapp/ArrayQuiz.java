package com.example.christopher.linkedoutapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

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
import java.util.List;


public class ArrayQuiz extends AppCompatActivity {

    private String Server = "http://lempo.d.umn.edu:4531/arrayQuizData";
    private String Server2 = "http://10.0.2.2:4321/arrayQuizData";
    //private TextView textView;
    //private RadioButton rb;
    //private RadioGroup rg;
    //ArrayAdapter<String> adapter;
    //ArrayList<String> itemList;
    //Spinner spinner;
    private ListView lvQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrayquiz);
        //textView = (TextView) findViewById(R.id.quizQuestion);
        lvQuizzes = (ListView)findViewById(R.id.lvQuizzes);
        restGET();

        //rg = (RadioGroup) findViewById(R.id.rGroup);

    }


    private class HTTPAsyncTask extends AsyncTask<String, Integer, List<QuizModel>> {

        @Override
        protected List<QuizModel> doInBackground(String... params) {

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

                    //start of Isaiah's code

                    String finalJson = sb.toString();
                    //test
                    System.out.println(finalJson);

                    JSONObject parentObject = new JSONObject(finalJson); //holds the json data pulled from server
                    JSONArray parentArray = null;
                    try {
                        parentArray = parentObject.getJSONArray("quizzes");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //this array holds all the data and holds the arrays that hold anser choices
                    List<QuizModel> quizModelList = new ArrayList<>();

                    for(int i = 0; i < parentArray.length(); i++){
                        QuizModel quizModel = new QuizModel(); //create new QuizModel object
                        JSONObject finalObject = null;
                        try {
                            finalObject = parentArray.getJSONObject(i); //grab i object in JSON data from sever
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            quizModel.setName(finalObject.getString("quizzes")); //get name from JSON data in server and put it into the array as QuizModel object for name
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            quizModel.setFormat(finalObject.getString("quizFormat")); //get format from JSON data in server and put it into the array as QuizModel object for format
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            quizModel.setQuestion(finalObject.getString("quizQuestion")); //get question from JSON data in server and put it into the array as QuizModel object for question
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //this array holds the answer choices
                        List<QuizModel.Choices> choicesList = new ArrayList<>();
                        try {
                            for(int j = 0; j < finalObject.getJSONArray("choiceList").length(); j++){
                                //creats a new QuizModel.Choices object to store choices
                                QuizModel.Choices choices = new QuizModel.Choices();
                                choices.setAnswerChoice(finalObject.getJSONArray("choiceList").getJSONObject(j).getString("choice"));
                                choicesList.add(choices);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        quizModel.setChoiceList(choicesList); //sets the ChoiceList using this one we have created
                        quizModelList.add(quizModel);         //adds the quizModel object using this one we have created one at a time, (addin the final objec to the list


                    }//end for
                    return  quizModelList; //this is the List tht is sent to the onPostExecute
                }//end doInBackground


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                serverConnection.disconnect();
            }

            return null;
        }

        /** This function passes the json data to a quiz layout and variables for helper function
         * The following executes after the Asynchronous task is finished executing
         *
         * @param result the result from the query
         */
        protected void onPostExecute(List<QuizModel> result) {


            QuizAdapter adapter = new QuizAdapter(getApplicationContext(), R.layout.row, result);
            lvQuizzes.setAdapter(adapter);

        }//end onPostExecute


    }//****************End AsyncTask()****************************************

    /**
     * Acts as the onClick callback for the REST GET Button. The code will generate a REST GET
     * action to the REST Server.
     */
    public void restGET() {
        new HTTPAsyncTask().execute(Server2, "GET");
    }

    /**
     * Acts as the onClick callback for the REST POST Button. The code will generate a REST POST
     * action to the REST Server. It is called when the submit button is pressed.
     *
     * @param view
     *
     */
    /*
    public void restPOST(View view) {
        QuizModel quizModel = null;
        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("question", quizModel.getQuestion());
            jsonParam.put("answer", isCorrect());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG:", jsonParam.toString());
        new HTTPAsyncTask().execute(Server, "POST", jsonParam.toString());
    }*/

    /** This function will tell if the question was answered correctly or not
     *
     *@Return: A correct of incorrect for the answer being correct or incorrect
     *
     */
    public String isCorrect(){
        EditText etAnswer = (EditText)findViewById(R.id.etAnswer);
        String studentAnswer = etAnswer.toString();
        QuizModel correctAnswer = null;
        if(studentAnswer.equals(correctAnswer.getAnswer())){
            return "Correct";
        }
        else
            return "Incorrect";
    }

    /**This is the QuizAdapter that is used to set the layout of the xml files
     *
     */

    public class QuizAdapter extends ArrayAdapter{

        private List<QuizModel> quizModelList;
        private int resource;
        private LayoutInflater inflater;

        public QuizAdapter(Context context, int resource, List<QuizModel> objects) {
            super(context, resource, objects);
            quizModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);
                holder.tvQuiz = (TextView)convertView.findViewById(R.id.tvQuiz);
                holder.tvChoices = (TextView)convertView.findViewById(R.id.tvChoices);
                holder.etAnswer = (EditText)convertView.findViewById(R.id.etAnswer);
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }

            //holder for the quiz question
            holder.tvQuiz.setText(quizModelList.get(position).getQuestion());
            //for loop for multiple answer choices string buffer adds one to letter to lidst A,B,C for answers
            StringBuffer stringBuffer = new StringBuffer();
            char letter = 'A';
            for(QuizModel.Choices choices : quizModelList.get(position).getChoiceList()){
                stringBuffer.append( letter + ") "  );
                stringBuffer.append(choices.getAnswerChoice());
                letter++;
                stringBuffer.append("\n");
            }
            holder.tvChoices.setText(stringBuffer);
            //holder.etAnswer.setText("Enter Letter Choice Here");

            return convertView;
        }
        class ViewHolder{
            private TextView tvQuiz;
            private TextView tvChoices;
            private EditText etAnswer;
        }
    }//end adapter
}
