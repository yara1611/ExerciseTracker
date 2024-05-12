package com.example.excercisetrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {



    public Cursor cursor;
    public RecyclerAdapter(Cursor cursor) {
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

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView exerciseName;


        RecyclerAdapter recyclerAdapter;
        public ViewHolder(@NonNull View itemView, RecyclerAdapter recyclerAdapter) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_list_item);

            this.recyclerAdapter= recyclerAdapter;
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
