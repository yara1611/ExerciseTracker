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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = Integer.parseInt(sharedPreferences.getString("userID", "0"));
        Log.d("UserID", "onCreate: UserID->" + userId);

        // Initialize DatabaseHelper

        String exerciseName = getIntent().getStringExtra("WorkoutName");

        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
        Cursor cursor = dbh.getExercise(exerciseName);
        String routineN = sharedPreferences.getString("routineName",null);
        Cursor cursorWO = dbh.getRoutineWorkout(sharedPreferences.getInt("RoutineID",0));
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
                    "Instructions: %s\n", Equipment, Difficulty, Type, Muscle, Instructions);
            txt.setText(name);
            txtInfo.setText(info);

        } else {
            txt.setText("Exercise Not Found");
        }
    }
}