package com.team13.finalproject;

/**
 * Created by Alyssa on 5/4/2015.
 */
public interface Observable {
    public void addObserver(Observer obs);
    public void deleteObserver(Observer obs);
}
