package com.team13.finalproject;

/**
 * Created by Trev on 4/25/15.
 */
public class Question implements TwoItemListable {

    private String name;
    private int numAnswers;

    public Question(String name, int numQuestions) {
        this.name = name;
        this.numAnswers = numQuestions;
    }

    @Override
    public String getValue() {
        return name;
    }

    @Override
    public String getDescription() {
        return numAnswers + " answers submitted";
    }
}