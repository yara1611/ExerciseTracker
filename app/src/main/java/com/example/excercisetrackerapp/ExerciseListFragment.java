package com.example.excercisetrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Currency;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView exerciseTextView;
    private DatabaseHelper dbHelper;
    private Cursor exerciseList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExerciseListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseListFragment newInstance(String param1, String param2) {
        ExerciseListFragment fragment = new ExerciseListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ... (inflate layout, initialize dbHelper)
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        exerciseTextView = view.findViewById(R.id.exercisetextview); // Get reference to TextView
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext()); // Initialize database helper
        // Retrieve exercises from database and display
        exerciseList = dbHelper.GetAllExercises();
        startService();
        if(exerciseList!=null&&exerciseList.moveToFirst()){
            int col = exerciseList.getColumnIndexOrThrow("name");
            String data=exerciseList.getString(col);
            exerciseTextView.setText(data);
        }
        else {
            exerciseTextView.setText("No Data Found");
        }
        return view;
    }
    private void startService() {
        Intent serviceIntent = new Intent(getActivity().getApplicationContext(), ExerciseService.class);
        getActivity().startService(serviceIntent);
    }
}