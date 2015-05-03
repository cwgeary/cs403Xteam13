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


public class Discussions extends ActionBarActivity {

    public static final String DISCUSSION_ID = "com.team13.FinalProject.discussionID";
    public static final String DISCUSSION_SELECTED = "com.team13.FinalProject.discussionSelected";

    private DataAdapter adapter;

    private final DatabaseHelper helper = new DatabaseHelper(this);

    private void initDbForDemo() {
        long[] discussionIds = {
                Discussion.make(helper, "Mobile & Ubiquitous Computing"),
                Discussion.make(helper, "Systems Programming")
        };

        long[] questionIds = {
                Question.make(helper, "Minimum required SDK version?", discussionIds[0]),
                Question.make(helper, "How do pointers work!?", discussionIds[1]),
                Question.make(helper, "Can't compile with readline?", discussionIds[1]),
                Question.make(helper, "Difference between calloc and malloc?", discussionIds[1])
        };

        Answer.make(helper, "It's up to you.", questionIds[0]);
        Answer.make(helper, "Level 1!", questionIds[0]);
        Answer.make(helper, "No later than 19, really", questionIds[0]);
        Answer.make(helper, "Check the Android version usage stats", questionIds[0]);
        Answer.make(helper, "I don't know!", questionIds[1]);
        Answer.make(helper, "So confusing!", questionIds[1]);
        Answer.make(helper, "You need to add -lncurses and -lreadline to your link command", questionIds[2]);
        Answer.make(helper, "calloc takes two arguments, duh", questionIds[3]);
        Answer.make(helper, "Calloc is for arrays?", questionIds[3]);
        Answer.make(helper, "Calloc is easier to use with arrays and zeroes out the memory", questionIds[3]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        onRefresh();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussions);

        adapter = new DataAdapter(this, Discussion.getCursor(helper.getDb()), Discussion.getViewBinder(helper.getDb()));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Trust me, I'm a JavaScript developer
        final Discussions that = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Intent intent = new Intent(that, Questions.class);
                Discussion item = new Discussion((Cursor) adapterView.getItemAtPosition(index));
                intent.putExtra(DISCUSSION_ID, item.id);
                intent.putExtra(DISCUSSION_SELECTED, item.discussion);
                startActivity(intent);
            }
        });
    }

    private void onRefresh() {
        helper.onUpgrade(helper.getDb(), 0, 0);
        initDbForDemo();
    }

    private void onUpdate() {
        adapter.changeCursor(Discussion.getCursor(helper.getDb()));
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discussions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_add:

                adapter.showAddDialog(this, "Name your new discussion", new DataAdapter.OnAddItemListener() {
                    public void onAddItem(String input) {
                        Discussion.make(helper, input);
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
