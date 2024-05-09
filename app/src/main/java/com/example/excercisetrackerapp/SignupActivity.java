package com.example.excercisetrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        EditText inName = findViewById(R.id.editTextName);
        EditText inUN = findViewById(R.id.editTextUserName);
        EditText inEmail = findViewById(R.id.editTextEmail);
        EditText inPass = findViewById(R.id.editTextPassword);

        // Instantiate the helper class
        DatabaseHelper dbHelper = new DatabaseHelper(this);



        Button addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(v->{
            dbHelper.CreateOne(inName.getText().toString(),inUN.getText().toString(),inEmail.getText().toString(),inPass.getText().toString());
        });
        Button showBtn = findViewById(R.id.show_btn);
        /*showBtn.setOnClickListener(v->{
            Intent intent = new Intent(this,shows.class);
            startActivity(intent);
        });*/
        // Get a readable database

    }
}
