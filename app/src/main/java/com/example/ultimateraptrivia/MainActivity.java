package com.example.ultimateraptrivia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonQuizStart = findViewById(R.id.btnStart);
        Button buttonQuizExit = findViewById(R.id.btnExit);

        buttonQuizStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        buttonQuizExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitQuiz();
            }
        });
    }

    private void startQuiz(){
        Intent intent = new Intent(MainActivity.this,QuestionActivity.class);
        startActivity(intent);
        finish();
    }

    private void exitQuiz(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}