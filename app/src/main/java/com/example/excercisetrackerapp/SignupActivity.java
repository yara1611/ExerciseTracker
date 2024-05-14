package com.example.excercisetrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        Cursor cursor = dbHelper.getUser(inEmail.getText().toString());


        Button addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(v->{

                dbHelper.CreateOneUser(inName.getText().toString(),inUN.getText().toString(),inEmail.getText().toString(),inPass.getText().toString());
                Log.d("TAG", "onCreate: Added User");

        });


        CameraFragment cameraFragment = new CameraFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.camera_fragment_container,cameraFragment).commit();

        Button showBtn = findViewById(R.id.show_btn);
        showBtn.setOnClickListener(v->{
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("userID",cursor.getColumnIndexOrThrow("id"));
            startActivity(intent);
        });

    }
}
