package com.example.excercisetrackerapp;

import android.database.Cursor;
import android.os.Bundle;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String exerciseName = getIntent().getStringExtra("ExerciseName");
        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
        Cursor cursor = dbh.getExercise(exerciseName);
        TextView txt = findViewById(R.id.textView3);
        TextView txtInfo = findViewById(R.id.textView4);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String Equipment = cursor.getString(cursor.getColumnIndexOrThrow("equipment"));
        String Type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
        String Difficulty = cursor.getString(cursor.getColumnIndexOrThrow("difficulty"));
        String Muscle = cursor.getString(cursor.getColumnIndexOrThrow("muscle"));
        String Instructions = cursor.getString(cursor.getColumnIndexOrThrow("instructions"));
        String info = String.format("Equipment: %s\nDifficulty: %s\nType: %s\nMuscle: %s\nInstructions: %s\n",Equipment,Difficulty,Type,Muscle,Instructions);
        txt.setText(name);
        txtInfo.setText(info);


    }
}