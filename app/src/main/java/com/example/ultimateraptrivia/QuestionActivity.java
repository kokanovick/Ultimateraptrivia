package com.example.ultimateraptrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private TextView tvScore;
    private TextView tvQuestionCount;
    private TextView tvTimer;
    private RadioGroup rbGroup;
    private RadioButton rbFirst;
    private RadioButton rbSecond;
    private RadioButton rbThird;
    private RadioButton rbFourth;
    private Button btnConfirm;

    private List<Question> questionList;

    private int questionCounter;
    private int questionTotalCounter;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    private long backPressTime;

    private static final long countdown = 36000;
    private CountDownTimer countDownTimer;
    private long timeLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        tvQuestion = findViewById(R.id.tvQuestions);
        tvScore = findViewById(R.id.tvViewScore);
        tvQuestionCount = findViewById(R.id.tvQuestionCounter);
        tvTimer = findViewById(R.id.tvTime);
        rbGroup = findViewById(R.id.rGroup);
        rbFirst = findViewById(R.id.rbFirst);
        rbSecond = findViewById(R.id.rbSecond);
        rbThird = findViewById(R.id.rbThird);
        rbFourth = findViewById(R.id.rbFourth);
        btnConfirm = findViewById(R.id.btnConfirm);


        QuizDB db = new QuizDB(this);
        questionList = db.getAllQuestions();

        questionTotalCounter = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        btnConfirm.setOnClickListener(v -> {
            if(!answered){
                if(rbFirst.isChecked() || rbSecond.isChecked() || rbThird.isChecked() || rbFourth.isChecked()){
                    checkAnswer();
                }
                else{
                    Toast.makeText(QuestionActivity.this, "Molimo Vas izaberite odgovor.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                showNextQuestion();
            }
        });
    }

    private void showNextQuestion(){
        rbGroup.clearCheck();
        rbFirst.setBackgroundColor(Color.TRANSPARENT);
        rbSecond.setBackgroundColor(Color.TRANSPARENT);
        rbThird.setBackgroundColor(Color.TRANSPARENT);
        rbFourth.setBackgroundColor(Color.TRANSPARENT);

        if(questionCounter < questionTotalCounter){
            currentQuestion = questionList.get(questionCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rbFirst.setText(currentQuestion.getAnswerA());
            rbSecond.setText(currentQuestion.getAnswerB());
            rbThird.setText(currentQuestion.getAnswerC());
            rbFourth.setText(currentQuestion.getAnswerD());
            questionCounter++;
            tvQuestionCount.setText("Pitanje: " + questionCounter + "/" + questionTotalCounter);
            answered = false;
            btnConfirm.setText("Potvrdi");

            timeLeft = countdown;
            startCountdown();
        }
        else{
            moveToSaver();
        }
    }

    private void moveToSaver(){
        Intent intent = new Intent(this, SaverActivity.class);
        intent.putExtra("EXTRA_SCORE", score);
        startActivity(intent);
        finish();
    }

    private void startCountdown(){
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountdown();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                updateCountdown();
                checkAnswer();
            }
        }.start();
    }


    private void updateCountdown(){
        int minutes = (int) (timeLeft/1000)/60;
        int seconds = (int) (timeLeft/1000 )%60;
        String timeInFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        tvTimer.setText(timeInFormat);
    }

    private void checkAnswer(){
        answered = true;
        countDownTimer.cancel();
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNumber = rbGroup.indexOfChild(rbSelected) + 1;

        if(answerNumber == currentQuestion.getAnswerNumber()){
            score++;
            tvScore.setText("Rezultat: " + score);
        }
        showSolution();
    }

    private void showSolution(){
        rbFirst.setBackgroundColor(Color.RED);
        rbSecond.setBackgroundColor(Color.RED);
        rbThird.setBackgroundColor(Color.RED);
        rbFourth.setBackgroundColor(Color.RED);

        switch (currentQuestion.getAnswerNumber()){
            case 1:
                rbFirst.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                rbSecond.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                rbThird.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                rbFourth.setBackgroundColor(Color.GREEN);
                break;
        }

        if(questionCounter < questionTotalCounter){
            btnConfirm.setText("Sljedeće pitanje");
        }
        else{
            btnConfirm.setText("Završi");
        }
    }

    @Override
    public void onBackPressed() {
        if(backPressTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Pritisnite opet ukoliko želite izaći.", Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }
}