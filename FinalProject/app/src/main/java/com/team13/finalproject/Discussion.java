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
public class Discussion implements BaseColumns {

    // Java object representation
    public long id;
    public String discussion;
    public long date;

    public Discussion(Cursor cursor) {
        this.id = cursor.getLong(0);
        this.discussion = cursor.getString(1);
        this.date = cursor.getLong(2);
    }

    // Schema
    public static final String TABLE_NAME = "Discussions";
    public static final String COLUMN_DISCUSSION_TEXT = "discussion";

    // For inserting Discussions into the database
    public static long make(DatabaseHelper db, String discussion) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISCUSSION_TEXT, discussion);
        values.put(DatabaseHelper.COLUMN_DATE, System.currentTimeMillis());

        return db.addItem(TABLE_NAME, values);
    }

    // For fetching Discussions from the database
    public static Cursor getCursor(SQLiteDatabase db) {
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }

    // For listing Discussions on the screen
    public static BindTwoLineCursor getViewBinder() {
        return new BindTwoLineCursor() {
            @Override
            public void bindView(View view, Cursor cursor) {
                TextView valueTextView = (TextView) view.findViewById(R.id.primary_line);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.secondary_line);

                valueTextView.setText(cursor.getString(1));

                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(cursor.getLong(2));

                descriptionTextView.setText(new SimpleDateFormat("'Started on' M/d 'at' h:mm").format(date.getTime()));
            }
        };
    }
}