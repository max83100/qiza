package com.quiza.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.quiza.Question;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class Db_helper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;
    Context context;


    public Db_helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        setForcedUpgrade(3);
    }
    public ArrayList<Question> getAllData(){
        try {
            ArrayList<Question> list = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            if(sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from quiz",null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        String question_text = cursor.getString(1);
                        String option1 = cursor.getString(2);
                        String option2 = cursor.getString(3);
                        String option3 = cursor.getString(4);
                        int right_answer = cursor.getInt(5);

                        list.add(new Question(question_text,option1,option2,option3,right_answer));
                    }
                    return list;
                }
                else {
                    Toast.makeText(context, "No data retired", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else {
                Toast.makeText(context, "Data is null", Toast.LENGTH_SHORT).show();
                return null;
            }


        } catch (Exception e) {
            Toast.makeText(context, "getalldata" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }
    public int getUpgradeVersion() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"MAX (version)"};
        String sqlTables = "upgrades";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        int v = 0;
        c.moveToFirst();
        if (!c.isAfterLast()) {
            v = c.getInt(0);
        }
        c.close();
        return v;
    }
}
