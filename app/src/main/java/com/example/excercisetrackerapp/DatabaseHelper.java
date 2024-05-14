package com.example.excercisetrackerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.excercisetrackerapp.Models.Exercise;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ComponentDB";
    private static final int DATABASE_VERSION = 2;
    SQLiteDatabase userDB;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User table
        String createUserTableQuery = "CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "userName TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL)";
        db.execSQL(createUserTableQuery);

        // Create Routine table
        String createRoutineTableQuery = "CREATE TABLE Routine (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "userId INTEGER NOT NULL," +
                "FOREIGN KEY(userId) REFERENCES User(id))";
        db.execSQL(createRoutineTableQuery);

        // Create Exercise table
        String createExerciseTableQuery = "CREATE TABLE Exercise (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "type TEXT," +
                "muscle TEXT," +
                "equipment TEXT," +
                "difficulty TEXT," +
                "instructions TEXT)";
        db.execSQL(createExerciseTableQuery);

                // ... other fields


                // ... and so on for other fields


        // Create Workout table
        String createWorkoutTableQuery = "CREATE TABLE Workout (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "exerciseId INTEGER NOT NULL," +
                "sets INTEGER NOT NULL," +
                "reps INTEGER NOT NULL," +
                "weight INTEGER NOT NULL," +
                "notes TEXT NOT NULL," +
                "routineId INTEGER NOT NULL," +
                "FOREIGN KEY(routineId) REFERENCES Routine(id)," +
                "FOREIGN KEY(exerciseId) REFERENCES Exercise(id))";
        db.execSQL(createWorkoutTableQuery);
        // Create Muscle table
        String createMuscleTableQuery = "CREATE TABLE Muscle (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL)";
        db.execSQL(createMuscleTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS Workout");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Exercise");
        db.execSQL("DROP TABLE IF EXISTS Routines");
        db.execSQL("DROP TABLE IF EXISTS Muscle");
        onCreate(db);
    }
// crud createeee
    public void CreateRoutine(String name, int userId){
        userDB=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name",name);
        row.put("userId",userId);
        userDB.insert("Routine",null,row);
        userDB.close();

    }
    public void CreateMuscle(String name,int id){
        userDB=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name",name);
        row.put("id",id);

        userDB.insert("Muscle",null,row);
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
        Log.d("TAG", "CreateOneUser: Created user ");
        userDB.close();
    }
    public void CreateExercise(Exercise exercise){
        userDB=getWritableDatabase();
        ContentValues rows = new ContentValues();
        rows.put("name",exercise.getName());
        rows.put("type",exercise.getType());
        rows.put("difficulty",exercise.getDifficulty());
        rows.put("equipment",exercise.getEquipment());
        rows.put("muscle",exercise.getMuscle());
        rows.put("instructions",exercise.getInstructions());
        userDB=getWritableDatabase();
        userDB.insert("Exercise",null,rows);
        userDB.close();

    }
    public void CreateWorkout(String name, int exerciseId, int sets, int reps, int weight, String notes, int routineId){
        userDB=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name",name);
        row.put("exerciseId",exerciseId);
        row.put("sets",sets);
        row.put("reps",reps);
        row.put("weight",weight);
        row.put("notes",notes);
        row.put("routineId",routineId);
        userDB.insert("Workout",null,row);
        userDB.close();
    }

    // crud gettttt
    public Cursor GetAllExercises() {
        userDB = getReadableDatabase();
        Cursor cursor = userDB.query("Exercise", new String[]{"name"}, null, null, null, null, null);

        // Log the query result
        if (cursor != null) {
            Log.d("DatabaseHelper", "Number of exercises found: " + cursor.getCount());
        } else {
            Log.e("DatabaseHelper", "Error: Cursor is null");
        }

        if (cursor != null) {
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
    public Cursor GetRoutine(String routineName,int userId){
        userDB=getReadableDatabase();
        String[] rowDetails = { "name","id"};
        Cursor cursor = userDB.query("Routine",rowDetails,"userId='"+userId+"'and name='"+routineName+"'",null,null,null,null);
        if (cursor != null&&cursor.moveToFirst()) {
            Log.i("Cursor Status", "GetRoutine: DONE");
            return cursor;
        }
        else {
            cursor.close();
        }
        userDB.close();
        return cursor;
    }


public Cursor getRoutineWorkout(int routineId){
        userDB=getReadableDatabase();
        String[] rowDetails = { "name","sets","reps","weight","notes"};
        Cursor cursor = userDB.query("Workout",rowDetails,"routineId='"+routineId+"'",null,null,null,null);
    if (cursor != null&&cursor.moveToFirst()) {
        Log.i("Cursor Status", "GetWorkout: DONE");
        return cursor;
    }
    else {
        cursor.close();
    }
        userDB.close();
        return cursor;


}

    public Cursor getExercise(String exName){
        userDB=getReadableDatabase();
        String[] rowDetails = { "name","type","difficulty","equipment","instructions","muscle","id"};
        Cursor cursor = userDB.query("Exercise",rowDetails,"name='"+exName+"'",null,null,null,null);
        if(cursor != null ){
            cursor.moveToFirst();
        }
        userDB.close();
        return cursor;


    }
public Cursor getMuscles(){
        userDB=getReadableDatabase();
        Cursor cursor = userDB.query("Muscle", new String[]{"name"},null,null,null,null,null);
         if(cursor != null ){
             cursor.moveToFirst();
         }
        userDB.close();
        return cursor;
    }
// crud deleteeee
    public void DeleteRoutine(int id){
        userDB=getWritableDatabase();
        userDB.delete("Routine","id='"+id+"'",null);
        userDB.close();
    }
    public void DeleteWorkout(int id){
        userDB=getWritableDatabase();
        userDB.delete("Workout","id='"+id+"'",null);
        userDB.close();
    }
    // crud update
    public void updateWorkoutInRoutine(int workoutId, int routineId, int sets, int reps, float weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sets", sets);
        values.put("reps", reps);
        values.put("weight", weight);
        // Assuming you also have a column for the date of the workout, you can update it if needed
        // values.put("date", updatedDate);

        // Update the workout in the Workout table based on workoutId and routineId
        int rowsAffected = db.update("Workout", values, "id = ? AND routineId = ?", new String[]{String.valueOf(workoutId), String.valueOf(routineId)});

        db.close();
        // Optionally, you can check if the update was successful
        if (rowsAffected > 0) {
            // Update successful
        } else {
            // Update failed (workoutId or routineId not found)
        }
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
    public Cursor getUser(String email){
        userDB=getReadableDatabase();
        String[] rowDetails = {"email","id"};

        Cursor cursor = userDB.query("User",rowDetails,"email='"+email+"'",null,null,null,null);

        if (cursor != null&&cursor.moveToFirst()) {
            return cursor;
        }
        else {
            cursor.close();
        }

        userDB.close();
        return cursor;
    }




}

