package com.example.kartikeya.vocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by kartikeya on 25/3/16.
 */
public class database extends SQLiteOpenHelper {

    private static final String TAG = database.class.getSimpleName();

    private static final String DATABASE_NAME = "word_info";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "words";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG,"database tables created");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + KEY_ID
                + "INTEGER PRIMARY KEY," + KEY_NAME + "TEXT," + KEY_EMAIL + "TEXT UNIQUE," + KEY_UID + "TEXT," + KEY_CREATED_AT + "TEXT," + ")";
        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "Database table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME);
        onCreate(db);


    }
    public void addUser(String name, String email, String uid, String created_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_EMAIL,email);
        values.put(KEY_UID,uid);
        values.put(KEY_CREATED_AT, created_at);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "NEW USER IS ADDED INTO DATABASE" + id);
    }
    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM" + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            user.put("name",cursor.getString(1));
            user.put("email",cursor.getString(2));
            user.put("uid",cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        Log.d(TAG,"Fetching user information" + user.toString());

        return user;
    }
    public void deleteUser(String name, String email, String uid, String created_at){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();

        Log.d(TAG, "DELETE ALL USER FROM TABLE");
    }

}
