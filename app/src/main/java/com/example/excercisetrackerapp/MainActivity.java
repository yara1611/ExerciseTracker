package com.example.excercisetrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Create a layout for this activity

        Intent intent = new Intent(this, ExerciseService.class);
        startService(intent);
        // Add the ExerciseListFragment to the activity's layout
        // In MainActivity
        new Handler().postDelayed(() -> {
            // Add the ExerciseListFragment to the activity's layout after a delay
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new ExerciseListFragment())
                    .commit();
        }, 1000);
    }
}