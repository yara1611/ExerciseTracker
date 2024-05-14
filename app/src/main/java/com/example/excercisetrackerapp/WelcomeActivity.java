package com.example.excercisetrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button signIn = findViewById(R.id.signin_btn);
        Button signUp = findViewById(R.id.signup_btn);
        VoiceFragment voiceFragment = new VoiceFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.voice_container,voiceFragment).commit();
        signIn.setOnClickListener(v->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        signUp.setOnClickListener(v->{
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }
}