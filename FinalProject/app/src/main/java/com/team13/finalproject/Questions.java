package com.team13.finalproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class Questions extends ActionBarActivity {

    public static final String QUESTION_SELECTED = "com.team13.FinalProject.questionSelected";

    private final Question[] questions = {
            new Question("Minimum required SDK version?", 3),
            new Question("How are apps localized?", 1)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setTitle(extras.getString(Discussions.DISCUSSION_SELECTED));
        }

        ListAdapter adapter = new ListAdapter(this, questions);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Trust me, I'm a JavaScript developer
        final Questions that = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Intent intent = new Intent(that, Answers.class);
                TwoItemListable item = (TwoItemListable) adapterView.getItemAtPosition(index);
                intent.putExtra(QUESTION_SELECTED, item.getValue());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questions, menu);
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
