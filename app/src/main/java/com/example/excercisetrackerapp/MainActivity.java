package com.example.excercisetrackerapp;

import android.os.Bundle;

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

        // Add the ExerciseListFragment to the activity's layout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new ExerciseListFragment()) // Replace R.id.fragment_container with your container ID
                .commit();
    }
}