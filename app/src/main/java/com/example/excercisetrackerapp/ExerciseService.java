package com.example.excercisetrackerapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExerciseService extends Service {
    public ExerciseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private APIInterface apiInterface;
    private DatabaseHelper databaseHelper;
    private boolean isDataRetrieved = false;

    @Override
    public void onCreate(){
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);
        databaseHelper=new DatabaseHelper(this);
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        if (!isDataRetrieved) {
            fetchDataFromAPIandSaveToDB();
        } else {
            stopSelf(); // Stop the service if data has already been retrieved
        }
        return START_NOT_STICKY;
    }



    private void fetchDataFromAPIandSaveToDB(){
        Call<List<Exercise>> call = apiInterface.getExercise("MIxdrw3H4XZxr93KEo8yfQ==C70tl2cG1ZORQYwQ");

        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful()) {
                    List<Exercise> dataList = response.body();
                    if (dataList != null) {
                        for (Exercise data : dataList) {
                            // Insert data into local database using dbHelper

                            databaseHelper.CreateExercise(data);
                        }
                    }
                    isDataRetrieved = true; // Set the flag to indicate data has been retrieved
                    stopSelf(); // Stop the service once data is fetched and saved
                }else {
                    Log.e("ExerciseService", "Failed to fetch data: " + response.message());
                    stopSelf(); // Stop the service if the request was not successful
                }


            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Log.e("ExerciseService", "Failed to fetch data: " + t.getMessage());
                stopSelf(); // Stop the service in case of failure
            }
        });

    }

}