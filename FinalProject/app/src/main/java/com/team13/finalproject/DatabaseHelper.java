package com.team13.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Trev on 5/2/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String ID_TYPE = "integer primary key autoincrement";
    public  static final String COLUMN_DATE = "date";

    private static final String SQL_CREATE_DISCUSSIONS =
            "create table " + Discussion.TABLE_NAME + " (" +
            Discussion._ID + " " + ID_TYPE + "," +
            Discussion.COLUMN_DISCUSSION_TEXT + " text," +
            COLUMN_DATE + " integer" +
            ")";

    private static final String SQL_DELETE_DISCUSSIONS =
            "drop table if exists " + Discussion.TABLE_NAME;

    private static final String SQL_CREATE_QUESTIONS =
            "create table " + Question.TABLE_NAME + " (" +
            Question._ID + " " + ID_TYPE + "," +
            Question.COLUMN_QUESTION_TEXT + " text," +
            COLUMN_DATE + " integer," +
            Question.COLUMN_DISCUSSION_ID + " integer," +
            "foreign key(" + Question.COLUMN_DISCUSSION_ID + ") references " + Discussion.TABLE_NAME + "(" + Discussion._ID + ")" +
            ")";


    private static final String SQL_DELETE_QUESTIONS =
            "drop table if exists " + Question.TABLE_NAME;

    private static final String SQL_CREATE_ANSWERS =
            "create table " + Answer.TABLE_NAME + " (" +
            Answer._ID + " " + ID_TYPE + "," +
            Answer.COLUMN_ANSWER_TEXT + " text," +
            COLUMN_DATE + " integer," +
            Answer.COLUMN_QUESTION_ID + " integer," +
            "foreign key(" + Answer.COLUMN_QUESTION_ID + ") references " + Question.TABLE_NAME + "(" + Question._ID + ")" +
            ")";

    private static final String SQL_DELETE_ANSWERS =
            "drop table if exists " + Answer.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FinalProject.db";

    private SQLiteDatabase db = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        db.execSQL(SQL_CREATE_DISCUSSIONS);
        db.execSQL(SQL_CREATE_QUESTIONS);
        db.execSQL(SQL_CREATE_ANSWERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL(SQL_DELETE_DISCUSSIONS);
        db.execSQL(SQL_DELETE_QUESTIONS);
        db.execSQL(SQL_DELETE_ANSWERS);

        onCreate(db);
    }

    public SQLiteDatabase getDb() {
        if( db == null ) {
            return db = getWritableDatabase();
        }
        else {
            return db;
        }
    }

    public long addItem(String table, ContentValues values) {
        return getDb().insert(table, null, values);
    }
}
