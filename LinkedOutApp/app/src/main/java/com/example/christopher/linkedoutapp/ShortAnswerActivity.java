package com.example.christopher.linkedoutapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class ShortAnswerActivity extends AppCompatActivity {

    String saQ,saA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_answer);

        EditText shortAnswerQ = (EditText) findViewById(R.id.sa_question);
        saQ = shortAnswerQ.getText().toString();

        EditText shortAnswer_A = (EditText) findViewById(R.id.sa_answer);
        saA = shortAnswer_A.getText().toString();

    }

    public void onBackPressed(View view) {

        Question question = new Question();
        question.setQuestion(saQ);
        question.setAnswer(saA);
        question.setQuestionType("MC");

        Log.d("SAValues", question.getQuestion()+question.getAnswer()+question.getQuestionType());

        //Quiz obj = new Quiz();
        //obj.restPOSt(view);

        super.onBackPressed();
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }

}
