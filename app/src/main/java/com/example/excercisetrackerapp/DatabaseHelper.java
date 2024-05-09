package com.example.excercisetrackerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ComponentDB";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase userDB;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table
        db.execSQL("CREATE TABLE User (id INTEGER PRIMARY KEY, name TEXT NOT NULL, UserName TEXT NOT NULL, email TEXT NOT NULL, password TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        db.execSQL("drop table if exists User");
        onCreate(db);
    }

    public void CreateOne(String name, String UserName, String email, String password){
        userDB = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name",name);
        row.put("UserName",UserName);
        row.put("email",email);
        row.put("password",password);

        // NullColumnHack here means that if the ContentValues is empty, the database will insert a row with NULL values.
        userDB.insert("User", null, row);

        userDB.close();
    }

    public boolean LogInCheck(String email,String password){
        userDB=getReadableDatabase();
        String[] rowDetails = {"password"};
        boolean loginSucc = false;
        Cursor cursor = userDB.query("User",rowDetails,"email='"+email+"'",null,null,null,null);
        if(cursor!=null){
            String storedPass=cursor.getString(cursor.getColumnIndexOrThrow("password"));
            loginSucc= password.equals(storedPass);
        }

        userDB.close();
        return loginSucc;
    }
}

