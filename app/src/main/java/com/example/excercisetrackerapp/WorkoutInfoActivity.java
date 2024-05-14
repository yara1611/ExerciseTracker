package com.example.excercisetrackerapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WorkoutInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wo_info), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String exerciseName = getIntent().getStringExtra("WorkoutName");

        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
        Cursor cursor = dbh.getExercise(exerciseName);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = Integer.parseInt(sharedPreferences.getString("userID", "0"));
        //int routineId = Integer.parseInt(sharedPreferences.getString("WoRoutineID", "0"));
        Log.d("UserID", "onCreate: UserID->" + userId);

        String routineN = sharedPreferences.getString("routineName", null);
        Log.d("TAG", "onCreate: " + routineN);
        Cursor RoutineID= dbh.GetRoutine(routineN,userId);
        int routineID = RoutineID.getInt(RoutineID.getColumnIndexOrThrow("id"));
        Log.d("RoutineID", "onCreate: ROUTINEID->" + routineID);


        Cursor WorkoutID = dbh.getRoutineWorkout(routineID);
        int reps = WorkoutID.getInt(WorkoutID.getColumnIndexOrThrow("reps"));
        int sets = WorkoutID.getInt(WorkoutID.getColumnIndexOrThrow("sets"));
        int weights = WorkoutID.getInt(WorkoutID.getColumnIndexOrThrow("weight"));
        String Notes = WorkoutID.getString(WorkoutID.getColumnIndexOrThrow("notes"));

        TextView txt = findViewById(R.id.wo_title);
        TextView txtInfo = findViewById(R.id.textView4);


        if (cursor != null) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String Equipment = cursor.getString(cursor.getColumnIndexOrThrow("equipment"));
            String Type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String Difficulty = cursor.getString(cursor.getColumnIndexOrThrow("difficulty"));
            String Muscle = cursor.getString(cursor.getColumnIndexOrThrow("muscle"));
            String Instructions = cursor.getString(cursor.getColumnIndexOrThrow("instructions"));

            String info = String.format("Equipment: %s\n" +
                    "Difficulty: %s\n" +
                    "Type: %s\n" +
                    "Muscle: %s\n" +
                    "Instructions: %s\n"+
                    "Reps: %d\n"+
                    "Sets: %d\n"+
                    "Weight: %d\n"+
                    "Notes: %s\n", Equipment, Difficulty, Type, Muscle, Instructions,reps,sets,weights,Notes);
            txt.setText(name);
            txtInfo.setText(info);

        } else {
            txt.setText("Exercise Not Found");
        }
    }
}