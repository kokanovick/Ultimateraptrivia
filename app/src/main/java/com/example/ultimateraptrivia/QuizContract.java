package com.example.ultimateraptrivia;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract(){}

    public static class QuestionsTable implements BaseColumns {
        public static final String tableName = "quiz_questions";
        public static final String columnName = "question";
        public static final String columnAnswer_A = "answerA";
        public static final String columnAnswer_B= "answerB";
        public static final String columnAnswer_C = "answerC";
        public static final String columnAnswer_D = "answerD";
        public static final String answerNumber = "answer_number";
    }

}
