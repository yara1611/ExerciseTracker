package com.example.excercisetrackerapp;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExerciseService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ExerciseService() {
        super("ExerciseService"); // Name for the worker thread
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            // Read JSON data from file (e.g., from assets or raw folder)
            String jsonData = readJsonFromFile();

            // Parse JSON using Gson
            Gson gson = new Gson();
            Type exerciseListType = new TypeToken<ArrayList<Exercise>>(){}.getType();
            ArrayList<Exercise> exerciseList = gson.fromJson(jsonData, exerciseListType);

            // Insert exercise data into SQLite database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            for (Exercise exercise : exerciseList) {
                dbHelper.CreateExercise(exercise);
            }

        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., logging)
        }
    }
    private String readJsonFromFile() {
        String json = null;
        try {
            InputStream inputStream;

            // Choose whether to read from assets or raw folder
            boolean readFromAssets = true; // Change this to false to read from raw folder
                inputStream = getResources().openRawResource(R.raw.exercises); // Replace "exercises" with your file name

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
