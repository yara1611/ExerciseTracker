package com.example.excercisetrackerapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.excercisetrackerapp.Adapters.RoutineRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RoutinesFragments extends Fragment {

    private SharedPreferences preferences;
    private int userId;
    private DatabaseHelper db;
    private RoutineRecyclerAdapter adapter;

    public RoutinesFragments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize SharedPreferences
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        // Retrieve userId
        userId = Integer.parseInt(preferences.getString("userID","0"));
        // Initialize DatabaseHelper
        db = new DatabaseHelper(getContext().getApplicationContext());
        // Retrieve Cursor for user's routines
        Cursor cursor = db.GetUserRoutine(userId);
        // Initialize RoutineRecyclerAdapter with the Cursor
        adapter = new RoutineRecyclerAdapter(cursor);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routines_fragments, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.Routines_Recycler_Fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}