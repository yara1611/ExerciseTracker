package com.example.excercisetrackerapp.Adapters;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.excercisetrackerapp.DatabaseHelper;
import com.example.excercisetrackerapp.ExerciseInfoActivity;
import com.example.excercisetrackerapp.R;
import com.example.excercisetrackerapp.RoutineWorkoutsActivity;

public class RoutineRecyclerAdapter extends RecyclerView.Adapter<RoutineRecyclerAdapter.ViewHolder> {



    public Cursor cursor;
    public RoutineRecyclerAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routinelist_item,parent,false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if(!cursor.moveToPosition(position)){
           return;
       }
       String rouName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
       holder.routineName.setText(rouName);

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
        public final RadioButton routineName;


        RoutineRecyclerAdapter routineListRecyclerAdapter;
        public ViewHolder(@NonNull View itemView, RoutineRecyclerAdapter routineListRecyclerAdapter) {
            super(itemView);
            routineName = itemView.findViewById(R.id.radioButton);

            this.routineListRecyclerAdapter = routineListRecyclerAdapter;
            routineName.clearFocus();


            DatabaseHelper routines = new DatabaseHelper(itemView.getContext());
            routineName.setOnClickListener(v->{
                Intent in = new Intent(itemView.getContext(), RoutineWorkoutsActivity.class);
                in.putExtra("RoutineName", routineName.getText().toString());
                itemView.getContext().startActivity(in);

            });
        }



    }


}
