package com.example.christopher.linkedoutapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
//array imports
import com.example.christopher.linkedoutapp.QuizModel.QuizModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployerCreateJob extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Fragment_SA.OnFragmentInteractionListener_SA, Fragment_TF.OnFragmentInteractionListener_TF, Fragment_MC.OnFragmentInteractionListener_MC{

    Spinner spinner;
    String question,answer,option1,option2,option3;

    ArrayList<String[]> quiz = new ArrayList<String[]>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_create_job);


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

        String[] saq = {question,answer};
        quiz.add(saq);


        for (String[] strArr : quiz) {
            Log.d("Debug: ", "current array is : " + Arrays.toString(strArr));
        }
    }

    @Override
    public void onFragmentInteraction_TF(String value, String value1) {
        question = value;
        answer = value1;

        Log.d("Debug: ", "question and answer values --> Q:"+ question + " Answer:" + answer);

        //save values to array
        String[] saq2 = {question,answer};
        quiz.add(saq2);


        for (String[] strArr : quiz) {
            //Log.d("Debug: ", "current array is : " + Arrays.toString(strArr));
            Log.d("Debug: ", Arrays.toString(strArr) );
        }
    }

    @Override
    public void onFragmentInteraction_MC(String value, String value1, String value2, String value3) {
        question = value;
        option1 = value1;
        option2 = value2;
        option3 = value3;

        Log.d("Debug: ", "question and answer values --> Q:"+ question + " Answer:" + answer);

        //save values to array
        String[] saq3 = {question,option1,option3,option3};
        quiz.add(saq3);


        for (String[] strArr : quiz) {
            //Log.d("Debug: ", "current array is : " + Arrays.toString(strArr));
            Log.d("Debug: ", Arrays.toString(strArr) );
        }
    }
    public void onClickCancel(View view){
        Intent intent = new Intent(this, EmployerDefaultView.class);
        startActivity(intent);
    }

    /**
     * Acts as the onClick callback for the REST POST Button. The code will generate a REST POST
     * action to the REST Server. It is called when the submit button is pressed.
     *
     * @param view
     *
     */
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
        new ArrayQuiz.HTTPAsyncTask().execute(Server, "POST", jsonParam.toString());
    }
}
