package com.team13.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Trev on 4/25/15.
 */
public class Question implements BaseColumns {

    // Java object representation
    public long id;
    public String question;
    public long date;
    public long discussionID;

    public Question(Cursor cursor) {
        this.id = cursor.getLong(0);
        this.question = cursor.getString(1);
        this.date = cursor.getLong(2);
        this.discussionID = cursor.getLong(3);
    }

    // Schema
    public static final String TABLE_NAME = "Questions";
    public static final String COLUMN_QUESTION_TEXT = "question";
    public static final String COLUMN_DISCUSSION_ID = "discussionID";

    // For inserting Questions into the database
    public static long make(DatabaseHelper db, String question, long discussionID) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_TEXT, question);
        values.put(DatabaseHelper.COLUMN_DATE, System.currentTimeMillis());
        values.put(COLUMN_DISCUSSION_ID, discussionID);

        return db.addItem(TABLE_NAME, values);
    }

    // For fetching Questions from the database
    public static Cursor getCursor(SQLiteDatabase db, long discussionID) {
        return db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_DISCUSSION_ID + " = " + discussionID, null);
    }

    // For listing Questions on the screen
    public static BindTwoLineCursor getViewBinder() {
        return new BindTwoLineCursor() {
            @Override
            public void bindView(View view, Cursor cursor) {
                TextView valueTextView = (TextView) view.findViewById(R.id.primary_line);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.secondary_line);

                valueTextView.setText(cursor.getString(1));

                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(cursor.getLong(2));

                descriptionTextView.setText(new SimpleDateFormat("'Asked on' M/d 'at' h:mm").format(date.getTime()));
            }
        };
    }
}