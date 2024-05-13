package com.example.excercisetrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exerciselist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DatabaseHelper exercises = new DatabaseHelper(getApplicationContext());

        Cursor cursor = exercises.GetAllExercises();
        startService();
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new RecyclerAdapter(cursor));

        /*Button goToAddBtn = findViewById(R.id.go_to_add_btn);
        goToAddBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, AddEmpActivity.class);
            startActivity(intent);
        });*/



    }

    @Override
    protected  void  onRestart() {
        super.onRestart();
        System.out.println("onRestart() Called");

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();

        DatabaseHelper exercises = new DatabaseHelper(getApplicationContext());
        Cursor cursor = exercises.GetAllExercises();
        adapter.cursor = cursor;
        adapter.notifyDataSetChanged();
    }

    public void onDestroy(){
        super.onDestroy();
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        adapter.clearData();
    }


    private void startService() {
        Intent serviceIntent = new Intent(getApplicationContext(), ExerciseService.class);
        startService(serviceIntent);
    }
}