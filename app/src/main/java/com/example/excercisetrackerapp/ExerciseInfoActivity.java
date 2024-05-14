package com.example.excercisetrackerapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.excercisetrackerapp.Adapters.RoutineRecyclerAdapter;

public class ExerciseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Note), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //get the variable received from prev activity
        String exerciseName = getIntent().getStringExtra("ExerciseName");
        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
        Cursor cursor = dbh.getExercise(exerciseName);
        TextView txt = findViewById(R.id.textView3);
        TextView txtInfo = findViewById(R.id.textView4);
        if(cursor!=null){
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
                    "Instructions: %s\n",Equipment,Difficulty,Type,Muscle,Instructions);
            txt.setText(name);
            txtInfo.setText(info);
        }else{
            txt.setText("Exercise Not Found");
        }

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = Integer.parseInt(sharedPreferences.getString("userID","0"));
        int Reps= Integer.parseInt(((EditText)findViewById(R.id.Reps_Text)).getText().toString());
        int Weight= Integer.parseInt(((EditText)findViewById(R.id.Weight_Label)).getText().toString());
        int Sets= Integer.parseInt(((EditText)findViewById(R.id.Sets_Text)).getText().toString());
        String Notes= ((EditText)findViewById(R.id.Notes_Text)).getText().toString();

       Cursor Exerciseid = dbh.getExercise(exerciseName);
        Button addToRou = findViewById(R.id.button);
        addToRou.setOnClickListener(v->{
            Cursor RoutineID= dbh.GetRoutine(getIntent().getStringExtra("routineName"),userId );
            dbh.CreateWorkout(exerciseName,Exerciseid.getInt(Exerciseid.getColumnIndexOrThrow("id")),Reps,Weight,Sets,Notes,RoutineID.getInt(RoutineID.getColumnIndexOrThrow("id")));
            Log.d ("Routine" ,"Added to Routine");
        });

    }

//    private void showAddToRoutineDialog() {
//        //Add Radio Buttons with Names of Existing Routines
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View dialogView = getLayoutInflater().inflate(R.layout.add_to_routine_dialog, null);
//        RadioButton routine = findViewById(R.id.radioButton);
//        Button btnSave = dialogView.findViewById(R.id.btn_save);
//        DatabaseHelper routines = new DatabaseHelper(getApplicationContext());
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        int userId = Integer.parseInt(preferences.getString("userID","0"));
//
//        Cursor cursorRou = routines.GetUserRoutine(userId);
//        final RecyclerView recyclerView = dialogView.findViewById(R.id.rdBtn_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new RoutineRecyclerAdapter(cursorRou));
//
//        builder.setView(dialogView);
//        final AlertDialog dialog = builder.create();
//        dialog.show();

//        btnSave.setOnClickListener(new View.OnClickListener() {
//
//
//
//
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
