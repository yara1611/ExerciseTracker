package com.example.excercisetrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashbord);

        Button btnCreateRoutine = findViewById(R.id.btn_create_routine);
        btnCreateRoutine.setOnClickListener( v-> {
                showCreateRoutineDialog();

        });

        Button got_to_exercieses_btn =  findViewById(R.id.exercises_button);
        got_to_exercieses_btn.setOnClickListener(v->{
            Intent intent = new Intent(this, ExerciseListActivity.class);
            startActivity(intent);
        });
    }

    private void showCreateRoutineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.create_routine_dialog, null);
        EditText editTextRoutineName = dialogView.findViewById(R.id.edit_text_routine_name);
        Button btnCreate = dialogView.findViewById(R.id.btn_create);

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btnCreate.setOnClickListener(new View.OnClickListener() {
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