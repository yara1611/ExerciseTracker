package com.example.excercisetrackerapp.Models;

import com.example.excercisetrackerapp.Models.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIInterface {

    @GET("exercises")
    public Call<List<Exercise>> getExercise(@Query("x-api-key") String key);

}





