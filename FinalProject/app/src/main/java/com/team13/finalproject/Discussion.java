package com.team13.finalproject;

/**
 * Created by Trev on 4/25/15.
 */
public class Discussion implements TwoItemListable {

    private String name;
    private int numQuestions;

    public Discussion(String name, int numQuestions) {
        this.name = name;
        this.numQuestions = numQuestions;
    }

    @Override
    public String getValue() {
        return name;
    }

    @Override
    public String getDescription() {
        return numQuestions + " questions asked";
    }
}
