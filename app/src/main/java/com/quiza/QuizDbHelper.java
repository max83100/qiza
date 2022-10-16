package com.quiza;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDB.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE "+
                QuizContact.QuestionTable.TABLE_NAME + " ( " +
                QuizContact.QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContact.QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuizContact.QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContact.QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContact.QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContact.QuestionTable.COLUMN_ANSWER_NUMBER + " INTEGER" + ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();


    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Вариант А правильный","A","B","C",1);
        addQuestion(q1);
        Question q2 = new Question("Вариант B правильный","A","B","C",2);
        addQuestion(q2);
        Question q3 = new Question("Вариант C правильный","A","B","C",3);
        addQuestion(q3);
        Question q4 = new Question("Вариант А правильный снова","A","B","C",1);
        addQuestion(q4);
        Question q5 = new Question("Вариант А правильный снова","A","B","C",2);
        addQuestion(q5);
    }

    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuizContact.QuestionTable.COLUMN_QUESTION,question.getQiuestion());
        cv.put(QuizContact.QuestionTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuizContact.QuestionTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuizContact.QuestionTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuizContact.QuestionTable.COLUMN_ANSWER_NUMBER,question.getAnswer_number());
        db.insert(QuizContact.QuestionTable.TABLE_NAME,null,cv);
    }

    @SuppressLint("Range")
    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContact.QuestionTable.TABLE_NAME,null);

        if(c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQiuestion(c.getString(c.getColumnIndex(QuizContact.QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContact.QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContact.QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContact.QuestionTable.COLUMN_OPTION3)));
                question.setAnswer_number(c.getInt(c.getColumnIndex(QuizContact.QuestionTable.COLUMN_ANSWER_NUMBER)));
                questionList.add(question);

            }
            while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContact.QuestionTable.TABLE_NAME);
        onCreate(db);
    }
}
