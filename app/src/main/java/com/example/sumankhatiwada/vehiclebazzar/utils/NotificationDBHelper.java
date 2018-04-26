package com.example.sumankhatiwada.vehiclebazzar.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.NotificationFragment;

/**
 * Created by Krishna on 4/25/2018.
 */
public class NotificationDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME = "notification_table.db";

    private static final String ID = "ID";
    private static final String MESSAGE = "message";


    public NotificationDBHelper(Context context) {

        super(context, DB_NAME, null, 2);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE notificationlist " + "("+
                ID + " integer PRIMARY KEY AUTOINCREMENT," +
                MESSAGE + " TEXT)";

        System.out.println("query -->> " + createTable);
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notificationlist");
        onCreate(db);
    }

    public boolean addData(String title, String message){

        String notifMessage = title + " " + message;

        System.out.println("Message ==== >>" + notifMessage);

        SQLiteDatabase db = this.getWritableDatabase();

        //Putting the values in the table
        ContentValues contentValues = new ContentValues();
        //contentValues.put(TITLE, title);
        contentValues.put(MESSAGE, notifMessage);

        System.out.println("Adding :: Message --->>" + notifMessage);

        Log.d(TAG, "addData : Adding :: Message --->>" + notifMessage);

        Long result =db.insert("notificationlist", null, contentValues);

        System.out.println("RESULT =======>>>" + result);

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
        String query = "SELECT * FROM notificationlist";
        return db.rawQuery(query,null);

    }
}

