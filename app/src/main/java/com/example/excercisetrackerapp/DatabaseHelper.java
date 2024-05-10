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
        db.execSQL("CREATE TABLE User (id INTEGER PRIMARY KEY, name TEXT NOT NULL, userName TEXT NOT NULL, email TEXT NOT NULL, password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE Routine (id INTEGER PRIMARY KEY, name TEXT NOT NULL, userId INTEGER NOT NULL, FOREIGN KEY(userId) REFERENCES User(id))");
        db.execSQL("CREATE TABLE Exercise (id INTEGER PRIMARY KEY, name TEXT NOT NULL, routineId INTEGER NOT NULL, sets INTEGER NOT NULL, reps INTEGER NOT NULL, weight INTEGER NOT NULL, FOREIGN KEY(routineId) REFERENCES Routine(id))");
        db.execSQL("CREATE TABLE Workout (id INTEGER PRIMARY KEY, name TEXT NOT NULL, exerciseId INTEGER NOT NULL, sets INTEGER NOT NULL, reps INTEGER NOT NULL, weight INTEGER NOT NULL, notes TEXT NOT NULL, FOREIGN KEY(exerciseId) REFERENCES Exercise(id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS Workout");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Exercise");
        db.execSQL("DROP TABLE IF EXISTS Routines");
        onCreate(db);
    }

    public void CreateRoutine(String name, int userId,int routineId){
        userDB=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name",name);
        row.put("userId",userId);
        row.put("routineId",routineId);
        userDB.insert("Routine",null,row);
        userDB.close();

    }
    public void CreateOneUser(String name, String UserName, String email, String password){
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
    public void CreateExercise(String name, int routineId, int exerciseId, int sets, int reps, int weight){
        userDB=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name",name);
        row.put("routineId",routineId);
        row.put("sets",sets);
        row.put("reps",reps);
        row.put("weight",weight);
        userDB.insert("Exercise",null,row);
        userDB.close();

    }
    public void CreateWorkout(String name, int exerciseId, int sets, int reps, int weight, String notes){
        userDB=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name",name);
        row.put("exerciseId",exerciseId);
        row.put("sets",sets);
        row.put("reps",reps);
        row.put("weight",weight);
        row.put("notes",notes);
        userDB.insert("Workout",null,row);
        userDB.close();
    }
public Cursor GetAllExercises(){
        userDB=getReadableDatabase();
        String[] rowDetails = {"name","sets","reps","weight", "notes"};
        Cursor cursor = userDB.query("Exercise",rowDetails,null,null,null,null,null);
        if(cursor != null ){
            cursor.moveToFirst();

        }
        userDB.close();
        return cursor;
}
public Cursor GetUserRoutine(int userId){
        userDB=getReadableDatabase();
        String[] rowDetails = { "name","id","userId"};
        Cursor cursor = userDB.query("Routine",rowDetails,"userId='"+userId+"'",null,null,null,null);
        if(cursor != null ){
            cursor.moveToFirst();

        }
        userDB.close();
        return cursor;
}

    public boolean LogInCheck(String email,String password){
        userDB=getReadableDatabase();
        String[] rowDetails = {"password"};
        boolean loginSucc = false;
        Cursor cursor = userDB.query("User",rowDetails,"email='"+email+"'",null,null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            String storedPass=cursor.getString(cursor.getColumnIndexOrThrow("password"));
            loginSucc= password.equals(storedPass);
        }
        if (cursor != null) {
            cursor.close();
        }

        userDB.close();
        return loginSucc;
    }

}

