package com.example.ultimateraptrivia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.List;

public class ScoreAdapter extends FirebaseRecyclerAdapter<Score, ScoreAdapter.scoreViewHolder>{

    public ScoreAdapter(@NonNull FirebaseRecyclerOptions<Score> scores){
        super(scores);
    }

    @Override
    protected void onBindViewHolder(@NonNull scoreViewHolder holder, int position, @NonNull Score model) {
        holder.score.setText(model.getHigschore());
        holder.name.setText(model.getName());
    }

    @NonNull
    @Override
    public scoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_score,parent,false);
        return new ScoreAdapter.scoreViewHolder(view);
    }

    public class scoreViewHolder extends RecyclerView.ViewHolder {
        TextView score, name;
        public scoreViewHolder(@NonNull View itemView)
        {
            super(itemView);
            score = itemView.findViewById(R.id.finalScore);
            name = itemView.findViewById(R.id.finalName);
        }
    }
}

