package com.example.christopher.linkedoutapp.QuizModel;

import java.util.List;

/**
 * Created by kaliscuba on 11/15/16.
 */

public class QuizModel {

    private String question;
    private String answer;
    private String format;
    private String name;
    private List<Choices> choiceList;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Choices> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<Choices> choiceList) {
        this.choiceList = choiceList;
    }

    public static class Choices{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
