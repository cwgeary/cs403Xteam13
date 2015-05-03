package com.team13.finalproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class Answers extends ActionBarActivity {

    private DataAdapter adapter;

    private long questionID;

    private final DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            questionID = extras.getLong(Questions.QUESTION_ID);
            setTitle(extras.getString(Questions.QUESTION_SELECTED));
        }

        adapter = new DataAdapter(this, Answer.getCursor(helper.getDb(), questionID), Answer.getViewBinder());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    private void onRefresh() {

    }

    private void onUpdate() {
        adapter.changeCursor(Answer.getCursor(helper.getDb(), questionID));
        adapter.notifyDataSetChanged();
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
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add:

                adapter.showAddDialog(this, "Enter your answer", new DataAdapter.OnAddItemListener() {
                    public void onAddItem(String input) {
                        Answer.make(helper, input, questionID);
                        onUpdate();
                        adapter.notifyDataSetChanged();
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
