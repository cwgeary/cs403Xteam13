package com.team13.finalproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Calendar;


public class Answers extends ActionBarActivity {

    private final Answer[] answers = {
            new Answer("It's up to you.", Calendar.getInstance()),
            new Answer("Level 1!", Calendar.getInstance()),
            new Answer("No later than 19, really", Calendar.getInstance()),
            new Answer("Check the Android version usage stats", Calendar.getInstance())
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setTitle(extras.getString(Questions.QUESTION_SELECTED));
        }

        ListAdapter adapter = new ListAdapter(this, answers);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_add:
                return true;
            case R.id.action_refresh:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
