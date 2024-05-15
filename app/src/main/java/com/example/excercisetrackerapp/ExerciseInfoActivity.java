package com.example.excercisetrackerapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExerciseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wo_info), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = Integer.parseInt(sharedPreferences.getString("userID", "0"));
        Log.d("UserID", "onCreate: UserID->" + userId);
        String exerciseName = getIntent().getStringExtra("ExerciseName");
        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
        Cursor cursor = dbh.getExercise(exerciseName);
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


        EditText Reps = findViewById(R.id.Reps_Text);
        EditText Weight = findViewById(R.id.Weight_Text);
        EditText Sets = findViewById(R.id.Sets_Text);
        EditText Notes = findViewById(R.id.Notes_Text);


        Cursor Exerciseid = dbh.getExercise(exerciseName);
        String routineN = sharedPreferences.getString("routineName", null);
        Log.d("TAG", "onCreate: " + routineN);
        Cursor RoutineID = dbh.GetRoutine(routineN, userId);
        Button addToRou = findViewById(R.id.button);
        int exerciseId = Exerciseid.getInt(Exerciseid.getColumnIndexOrThrow("id"));
        SharedPreferences.Editor edit = sharedPreferences.edit();
        int routineId;

        if (RoutineID != null && RoutineID.moveToFirst()) {
            routineId = RoutineID.getInt(RoutineID.getColumnIndexOrThrow("id"));
            edit.putInt("RoutineID", routineId);
            edit.apply();
            Log.d("TAG", "onCreate: RoutineID->" + routineId);
            addToRou.setOnClickListener(v -> {

                dbh.CreateWorkout(exerciseName, exerciseId, Integer.parseInt(Reps.getText().toString()), Integer.parseInt(Weight.getText().toString()), Integer.parseInt(Sets.getText().toString()), Notes.getText().toString(), routineId);
                Log.d("Routine", "Added to Routine");
            });
            // Proceed with further operations using routineId
        } else {
            Log.e("ExerciseInfoActivity", "Routine ID not found for the given routine name and user ID");
        }


    }
    }

