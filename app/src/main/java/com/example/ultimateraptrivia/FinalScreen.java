package com.example.ultimateraptrivia;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FinalScreen extends AppCompatActivity{

    private RecyclerView recyclerView;
    private CardView cardView;
    private EditText editText;
    private Button btnInput;
    private Button btnNewGame;
    private Button btnExit;
    private ScoreAdapter adapter;
    private DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
        mbase = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.rvResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Score> options = new FirebaseRecyclerOptions.Builder<Score>().setQuery(mbase,Score.class).build();
        adapter = new ScoreAdapter(options);
        recyclerView.setAdapter(adapter);
        cardView = findViewById(R.id.cv);
        editText = findViewById(R.id.et);
        btnInput = findViewById(R.id.btnInput);
        btnNewGame = findViewById(R.id.btnStartNewGame);
        btnExit = findViewById(R.id.btnExit);
        isItSaved();

        btnInput.setOnClickListener(v -> saveName());

        btnNewGame.setOnClickListener(v -> startNewGame());

        btnExit.setOnClickListener(v -> exitGame());
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void writeToDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Rezultati:");
        int total = getIntent().getIntExtra("EXTRA_SCORE", 0);
        reference.push().setValue("IME:" + editText.getText().toString() + ", REZULTAT: " + total);
    }

    private void saveName() {
        if(editText.getText().toString().isEmpty()){
            Toast.makeText(this, "Prvo morate unijeti ime.", Toast.LENGTH_SHORT).show();}
        else{
            writeToDatabase();
        }
    }

    private void isItSaved(){
        Bundle bundle = getIntent().getExtras();
        boolean saved = bundle.getBoolean("BOOLEAN");
        if(!saved){
            cardView.setVisibility(CardView.INVISIBLE);
        }
    }

    private void startNewGame(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void exitGame(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}