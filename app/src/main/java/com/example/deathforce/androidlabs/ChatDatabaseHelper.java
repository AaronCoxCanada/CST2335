package com.example.deathforce.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DeathForce on 2/14/2018.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_CLASS = "ChatDatabaseHelper";
    private static final String DATABASE_NAME = "Messages.db";
    public static final String TABLE_NAME  = "ChatMessages";
    public static final String KEY_ID  = "_id";
    public static final String KEY_MESSAGE  = "Messages";
    private static final int DATABASE_VERSION = 4;



    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(DATABASE_CLASS, "Calling onCreate()");
        db.execSQL("CREATE TABLE " + TABLE_NAME  + "( "+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_MESSAGE + " text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(DATABASE_CLASS, "Calling onUpgrade(), oldVersion=" + oldVersion + ", newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
