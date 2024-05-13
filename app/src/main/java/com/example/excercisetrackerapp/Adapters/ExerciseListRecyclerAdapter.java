package com.example.excercisetrackerapp.Adapters;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

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

public class ExerciseListRecyclerAdapter extends RecyclerView.Adapter<ExerciseListRecyclerAdapter.ViewHolder> {



    public Cursor cursor;
    public ExerciseListRecyclerAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exerciselist_item,parent,false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if(!cursor.moveToPosition(position)){
           return;
       }
       String exName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
       holder.exerciseName.setText(exName);

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
        public final TextView exerciseName;

        String userId;
        ExerciseListRecyclerAdapter exerciseListRecyclerAdapter;
        public ViewHolder(@NonNull View itemView, ExerciseListRecyclerAdapter exerciseListRecyclerAdapter) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_list_item);
            this.exerciseListRecyclerAdapter = exerciseListRecyclerAdapter;
            exerciseName.clearFocus();


            DatabaseHelper exercises = new DatabaseHelper(itemView.getContext());

            exerciseName.setOnClickListener(v->{
                Intent in = new Intent(itemView.getContext(), ExerciseInfoActivity.class);
                in.putExtra("ExerciseName",exerciseName.getText().toString());

                itemView.getContext().startActivity(in);

            });
        }



    }


}
