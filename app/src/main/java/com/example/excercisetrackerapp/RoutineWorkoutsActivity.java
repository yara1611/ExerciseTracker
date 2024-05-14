package com.example.excercisetrackerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.excercisetrackerapp.Adapters.ExerciseListRecyclerAdapter;
import com.example.excercisetrackerapp.Adapters.WorkoutRecyclerAdapter;

public class RoutineWorkoutsActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_routine_workouts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        DatabaseHelper workouts = new DatabaseHelper(getApplicationContext());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String routineN = preferences.getString("routineName",null);
        int userId = Integer.parseInt(preferences.getString("userID","0"));
        Cursor RoutineID= workouts.GetRoutine(routineN,userId);
        int routineID = RoutineID.getInt(RoutineID.getColumnIndexOrThrow("id"));



        Cursor cursor = workouts.getRoutineWorkout(routineID);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView_workouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        recyclerView.setAdapter(new WorkoutRecyclerAdapter(cursor));
    }

    @Override
    protected  void  onRestart() {
        super.onRestart();
        System.out.println("onRestart() Called");

        RecyclerView recyclerView=findViewById(R.id.recyclerView_workouts);
        ExerciseListRecyclerAdapter adapter = (ExerciseListRecyclerAdapter) recyclerView.getAdapter();

        DatabaseHelper workouts = new DatabaseHelper(getApplicationContext());
        Cursor cursor = workouts.GetAllExercises();
        adapter.cursor = cursor;
        adapter.notifyDataSetChanged();
    }

    public void onDestroy(){
        super.onDestroy();
        final RecyclerView recyclerView = findViewById(R.id.recyclerView_workouts);
        ExerciseListRecyclerAdapter adapter = (ExerciseListRecyclerAdapter) recyclerView.getAdapter();
        adapter.clearData();
    }



}