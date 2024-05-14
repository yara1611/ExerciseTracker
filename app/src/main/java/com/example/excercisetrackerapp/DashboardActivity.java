package com.example.excercisetrackerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.excercisetrackerapp.Adapters.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

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
        ViewPager2 viewPager = findViewById(R.id.Pager);
        List<Fragment> fragments = new ArrayList<>();
        RoutinesFragments routinesFragments = new RoutinesFragments();
        fragments.add(routinesFragments);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(fragments.indexOf(routinesFragments));
    }

    private void showCreateRoutineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.create_routine_dialog, null);
        EditText editTextRoutineName = dialogView.findViewById(R.id.edit_text_routine_name);
        Button btnCreate = dialogView.findViewById(R.id.btn_save);

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
                    DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    int userId = Integer.parseInt(preferences.getString("userID","0"));
                    dbh.CreateRoutine(routineName,userId);
                    Intent intent = new Intent(DashboardActivity.this, ExerciseListActivity.class);
                    intent.putExtra("routineName", routineName);
                    startActivity(intent);
                    dialog.dismiss();
                } else {
                    editTextRoutineName.setError("Please enter routine name");
                }
            }
        });
    }
}