package com.example.excercisetrackerapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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



    }

    private void showAddToRoutineDialog() {
        //Add Radio Buttons with Names of Existing Routines
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.create_routine_dialog, null);
        EditText editTextRoutineName = dialogView.findViewById(R.id.edit_text_routine_name);
        Button btnSave = dialogView.findViewById(R.id.btn_save);

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String routineName = editTextRoutineName.getText().toString().trim();
                if (!TextUtils.isEmpty(routineName)) {
                    // Here you can perform any action with the routineName, such as creating a new routine
                    // For example, you can call a method to insert the routine into the database
                    // insertRoutine(routineName);
                    //Intent intent = new Intent(DashboardActivity.this, .class);
                    //startActivity(intent);
                    dialog.dismiss();
                } else {
                    editTextRoutineName.setError("Please enter routine name");
                }
            }
        });
    }
}