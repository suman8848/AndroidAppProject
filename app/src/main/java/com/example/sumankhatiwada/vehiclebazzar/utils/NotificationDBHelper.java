package com.example.sumankhatiwada.vehiclebazzar.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Krishna on 4/25/2018.
 */
public class NotificationDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "notification_table";
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";


    public NotificationDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE" + TABLE_NAME + "("+ TITLE + "TEXT" + MESSAGE + "TEXT" +")";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String title, String message){
        SQLiteDatabase db = this.getWritableDatabase();

        //Putting the values in the table
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(MESSAGE, message);

        Log.d(TAG, "addData : Adding" + "Title --->>" + title + "\n" +"Message --->>" + message);

        Long result =db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) {
            return false;
        } else{
            return true;
        }
    }

    /**
     * Returns all the data from database
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query,null);

    }
}

