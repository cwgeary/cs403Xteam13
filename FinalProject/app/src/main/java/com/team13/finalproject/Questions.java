package com.team13.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class Questions extends ActionBarActivity {

    public static final String QUESTION_ID = "com.team13.FinalProject.questionID";
    public static final String QUESTION_SELECTED = "com.team13.FinalProject.questionSelected";

    private DataAdapter adapter;

    private long discussionID;

    private final DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            discussionID = extras.getLong(Discussions.DISCUSSION_ID);
            setTitle(extras.getString(Discussions.DISCUSSION_SELECTED));
        }

        adapter = new DataAdapter(this, Question.getCursor(helper.getDb(), discussionID), Question.getViewBinder());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Trust me, I'm a JavaScript developer
        final Questions that = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Intent intent = new Intent(that, Answers.class);
                Question item = new Question((Cursor) adapterView.getItemAtPosition(index));
                intent.putExtra(QUESTION_ID, item.id);
                intent.putExtra(QUESTION_SELECTED, item.question);
                startActivity(intent);
            }
        });
    }

    private void onRefresh() {

    }

    private void onUpdate() {
        adapter.changeCursor(Question.getCursor(helper.getDb(), discussionID));
        adapter.notifyDataSetChanged();
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

                adapter.showAddDialog(this, "Enter your question", new DataAdapter.OnAddItemListener() {
                    public void onAddItem(String input) {
                        Question.make(helper, input, discussionID);
                        onUpdate();
                    }
                });

                return true;
            case R.id.action_refresh:
                onRefresh();
                onUpdate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
