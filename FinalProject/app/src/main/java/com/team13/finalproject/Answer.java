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
public class Answer implements BaseColumns {

    // Java object representation
    public long id;
    public String answer;
    public long date;
    public long questionID;

    public Answer(Cursor cursor) {
        this.id = cursor.getLong(0);
        this.answer = cursor.getString(1);
        this.date = cursor.getLong(2);
        this.questionID = cursor.getLong(3);
    }

    // Schema
    public static final String TABLE_NAME = "Answers";
    public static final String COLUMN_ANSWER_TEXT = "answer";
    public static final String COLUMN_QUESTION_ID = "questionID";

    // For inserting Answers into the database
    public static long make(DatabaseHelper db, String answer, long questionID) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANSWER_TEXT, answer);
        values.put(DatabaseHelper.COLUMN_DATE, System.currentTimeMillis());
        values.put(COLUMN_QUESTION_ID, questionID);

        return db.addItem(TABLE_NAME, values);
    }

    // For fetching Answers from the database
    public static Cursor getCursor(SQLiteDatabase db, long questionID) {
        return db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_QUESTION_ID + " = " + questionID, null);
    }

    // For listing Answers on the screen
    public static BindTwoLineCursor getViewBinder() {
        return new BindTwoLineCursor() {
            @Override
            public void bindView(View view, Cursor cursor) {
                TextView valueTextView = (TextView) view.findViewById(R.id.primary_line);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.secondary_line);

                valueTextView.setText(cursor.getString(1));

                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(cursor.getLong(2));

                descriptionTextView.setText(new SimpleDateFormat("'Submitted on' M/d 'at' h:mm").format(date.getTime()));
            }
        };
    }
}