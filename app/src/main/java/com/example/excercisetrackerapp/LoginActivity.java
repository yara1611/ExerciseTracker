package com.example.excercisetrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText email = findViewById(R.id.login_email);
        EditText pass = findViewById(R.id.LoginPass);
        Button loginBtn = findViewById(R.id.login_btn);
        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
        /*loginBtn.setOnClickListener(v->{
            boolean check =dbh.LogInCheck(email.getText().toString(),pass.getText().toString());
            if(check){
                Intent intent = new Intent(this, shows.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"Log In Failed",Toast.LENGTH_LONG).show();
            }
        });*/
    }
}