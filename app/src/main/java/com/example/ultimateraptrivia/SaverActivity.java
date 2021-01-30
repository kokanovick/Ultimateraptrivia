package com.example.ultimateraptrivia;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SaverActivity extends AppCompatActivity{

    private boolean saved = false;

    private Button btnYes;
    private Button btnNo;

    private TextView tvHighscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saver);

        tvHighscore = findViewById(R.id.tvTotalScore);
        tvHighscore.setText("UKUPAN REZULTAT: " + getIntent().getIntExtra("EXTRA_SCORE", 0));

        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> {
            saved = true;
            moveToFinalScreen();
        });
        btnNo.setOnClickListener(v -> moveToFinalScreen());
    }


    private void moveToFinalScreen(){
        Intent intent = new Intent(this, FinalScreen.class);
        Bundle total = getIntent().getExtras();
        if(total != null){
            intent.putExtras(total);
        }
        intent.putExtra("BOOLEAN", saved);
        startActivity(intent);
        finish();
    }
}