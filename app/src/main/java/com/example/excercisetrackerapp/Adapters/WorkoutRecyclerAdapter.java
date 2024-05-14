package com.example.excercisetrackerapp.Adapters;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.excercisetrackerapp.DatabaseHelper;
import com.example.excercisetrackerapp.ExerciseInfoActivity;
import com.example.excercisetrackerapp.R;
import com.example.excercisetrackerapp.WorkoutInfoActivity;

public class WorkoutRecyclerAdapter extends RecyclerView.Adapter<WorkoutRecyclerAdapter.ViewHolder> {



    public Cursor cursor;
    public WorkoutRecyclerAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workoutlist_item,parent,false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if(!cursor.moveToPosition(position)){
           return;
       }
       String exName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
       holder.workoutName.setText(exName);

    }

    public void clearData() {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView workoutName;

        String userId;
        WorkoutRecyclerAdapter workoutRecyclerAdapter;
        public ViewHolder(@NonNull View itemView, WorkoutRecyclerAdapter workoutRecyclerAdapter) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workout_item_txt);
            this.workoutRecyclerAdapter = workoutRecyclerAdapter;
            workoutName.clearFocus();


            DatabaseHelper exercises = new DatabaseHelper(itemView.getContext());

            workoutName.setOnClickListener(v->{
                Intent in = new Intent(itemView.getContext(), WorkoutInfoActivity.class);
                in.putExtra("WorkoutName", workoutName.getText().toString());

                itemView.getContext().startActivity(in);

            });
        }



    }


}
