package com.team13.finalproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Trev on 4/25/15.
 */
public class Answer implements TwoItemListable {

    private String value;
    private Calendar date;

    public Answer(String value, Calendar date) {
        this.value = value;
        this.date = date;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        String description = "test";
        /*try {
            description = "Submitted at " + (new SimpleDateFormat("HH:MM on MMMM DD", Locale.US).format(date));
        }
        catch(Throwable t) {
            //Log.e("OHSHIT",t.toString());
        }*/
        return description;
    }
}