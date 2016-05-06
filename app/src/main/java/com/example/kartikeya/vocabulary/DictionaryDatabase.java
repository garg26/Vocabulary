package com.example.kartikeya.vocabulary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DictionaryDatabase extends SQLiteAssetHelper {

    private static final String TAG = SQLiteAssetHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "words.sqlite";
    private static final int DATABASE_VERSION = 1;
    String [] myArray = new String[2];
    public DictionaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public Cursor getWordItem(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqlSelect = {"0 _id","words","defination"};
        String sqlTables = "word";
        qb.setTables(sqlTables);

        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        c.moveToFirst();
        return c;
    }

    public String[] searchWord(String string){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String sqlTables = "word";
        qb.setTables(sqlTables);
        String query=  "SELECT * FROM word where "+"words"+"='"+string+"'" ;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                 myArray[0] = cursor.getString(cursor.getColumnIndex("words"));
                 myArray[1] = cursor.getString(cursor.getColumnIndex("defination"));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return myArray;
    }
}