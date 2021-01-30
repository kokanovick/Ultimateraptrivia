package com.example.ultimateraptrivia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class QuizDB extends SQLiteOpenHelper {

    private static final String dbName = "UltimateQuiz.db";
    private static final int dbVersion = 1;
    private SQLiteDatabase db;

    public QuizDB(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String sqlCreateQuestionsTable = "CREATE TABLE " +
                QuizContract.QuestionsTable.tableName + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QuizContract.QuestionsTable.columnName + " TEXT, " +
                QuizContract.QuestionsTable.columnAnswer_A + " TEXT, " +
                QuizContract.QuestionsTable.columnAnswer_B + " TEXT, " +
                QuizContract.QuestionsTable.columnAnswer_C + " TEXT, " +
                QuizContract.QuestionsTable.columnAnswer_D + " TEXT, " +
                QuizContract.QuestionsTable.answerNumber + " INTEGER" + ")";

        db.execSQL(sqlCreateQuestionsTable);
        fillTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.tableName);
        onCreate(db);
    }

    private void fillTable(){
        Question one = new Question("Od navedenih izvođača samo je jedan član Wu-Tang Clana. Odaberite ga.", "GZA", "Marky Mark", "Kool Moe Dee", "Eazy-E", 1);
        addQuestion(one);
        Question two = new Question("Odaberite uljeza.", "Only Built 4 Cuban Linx", "Liquid Swords", "Supreme Clientele", "The Chronic", 4);
        addQuestion(two);
        Question three = new Question("Koji od navedenih članova ima isključivo solo karijeru?", "Mos Def", "MF DOOM", "Havoc", "Niti jedan od navedenih", 4);
        addQuestion(three);
        Question four = new Question("Koji DJ nije radio na Illmatic albumu?", "DJ Premier", "DJ Kool Herc", "Q-Tip", "Large Professor", 2);
        addQuestion(four);
        Question five = new Question("Izaberite album čiji autor NIJE Nas.", "Nastradamus", "Illmatic", "Stillmatic", "Kingdom come", 4);
        addQuestion(five);
        Question six = new Question("Koji od navedenih aliasa NIJE koristio MF DOOM?", "Viktor Vaughn", "CZARFACE", "Metal Fingers", "Metal Face", 2);
        addQuestion(six);
        Question seven = new Question("Navedite autora: Hypnotize, Juicy, Big Poppa", "Notorious B.I.G.", "2pac", "50 cent", "Andre 3000", 1);
        addQuestion(seven);
        Question eight = new Question("Izaberite pravo ime autora Biggie Smalls", "Christopher Wallace", "Daniel Dummile", "O'Shea Jackson", "Eric Lynn Wright",1);
        addQuestion(eight);
        Question nine = new Question("Koja se pjesma univerzalno smatra prvim rap hitom?","Afrika Bambaataa", "Rapper's delight","The message", "PSK - What does it mean?", 2);
        addQuestion(nine);
        Question ten = new Question("Odaberite prvi komercijalni gangsta rap hit.", "PSK - What does it mean?", "Boyz N The Hood", "6 in the mornin'", "Who shot ya?", 1);
        addQuestion(ten);
        Question eleven = new Question("Između koga je nastao prvi rap sukob?", "Jay Z i Nas", "Eminem i Dr. Dre", "BDP i MC Shan", "Grandmaster Flash i Schooly D", 3);
        addQuestion(eleven);
        Question twelve = new Question("Koji grad je kolijevka rapa?", "Los Angeles", "Miami", "Detroit", "New York",4);
        addQuestion(twelve);
        Question thirteen = new Question("Izaberite člana NWA grupe.", "Ice Cube", "Big Boi", "Masta Killa","Prodigy" ,1);
        addQuestion(thirteen);
        Question fourteen = new Question("Koja grupa/duet najviše popularizira 'ulični' način odjevanja 80-ih?", "Mobb Deep", "Run-DMC", "Grandmaster Flash and the furious five","DJ Jazzy Jeff & The Fresh Prince",2);
        addQuestion(fourteen);
        Question fifteen = new Question("Grupa koja potiče promjenu političke klime SADa za vrijeme 80ih?", "Public Enemy", "Salt-n-pepa", "LL Cool J", "Ice T",1);
        addQuestion(fifteen);
        Question sixteen = new Question("Izaberite izvođača/e koji nisu bazirani u New Yorku.", "Immortal Technique", "Eric B & Rakim", "Outkast", "Big Daddy Kane",3);
        addQuestion(sixteen);
        Question seventeen = new Question("Koji od navedinih izvođača nije lider svojih grupa?", "RZA", "Snoop Dogg", "Chuck D", "Q-Tip", 2);
        addQuestion(seventeen);
        Question eightteen = new Question("Izaberite jedinog izvođača sa jednim izdanim albumom za vrijeme života.", "Tupac", "Big L", "DMX", "Big Punisher",2);
        addQuestion(eightteen);
        Question nineteen = new Question("Najpoznatiji latino izvođač/i?", "Cypress Hill", "Beastie Boys", "Common", "Kool Moe Dee",1);
        addQuestion(nineteen);
        Question twenty = new Question("Tko je najvažnija figura u revolucioniranju rimovanja i metafora?", "Eazy-E","Rakim","Busta Rhymes","Pete Rock",2);
        addQuestion(twenty);
    }

    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.columnName, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.columnAnswer_A, question.getAnswerA());
        cv.put(QuizContract.QuestionsTable.columnAnswer_B, question.getAnswerB());
        cv.put(QuizContract.QuestionsTable.columnAnswer_C, question.getAnswerC());
        cv.put(QuizContract.QuestionsTable.columnAnswer_D, question.getAnswerD());
        cv.put(QuizContract.QuestionsTable.answerNumber, question.getAnswerNumber());
        db.insert(QuizContract.QuestionsTable.tableName, null, cv);
    }

    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.tableName, null);

        if(cursor.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.columnName)));
                question.setAnswerA(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.columnAnswer_A)));
                question.setAnswerB(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.columnAnswer_B)));
                question.setAnswerC(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.columnAnswer_C)));
                question.setAnswerD(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.columnAnswer_D)));
                question.setAnswerNumber(cursor.getInt(cursor.getColumnIndex(QuizContract.QuestionsTable.answerNumber)));
                questionList.add(question);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }
}
