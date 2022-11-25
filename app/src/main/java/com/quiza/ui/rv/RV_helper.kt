package com.quiza.ui.rv

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import android.widget.Toast
import android.database.sqlite.SQLiteQueryBuilder
import java.lang.Exception
import java.util.ArrayList

class RV_helper(var context: Context, data: String?) : SQLiteAssetHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    var tab_name: String? = data
    val allData: ArrayList<Data>?
        get() = try {
            val list = ArrayList<Data>()
            val sqLiteDatabase = writableDatabase
            if (sqLiteDatabase != null) {
                val cursor = sqLiteDatabase.rawQuery("select * from "+ tab_name, null)
                if (cursor.count != 0) {
                    while (cursor.moveToNext()) {
                        val question_text = cursor.getString(1)
                        val option1 = cursor.getString(2)
                        val option2 = cursor.getString(3)
                        val option3 = cursor.getString(4)
                        val right_answer = cursor.getInt(5)
                        val explain = cursor.getString(6)

                        list.add(Data(question_text, option1, option2, option3, right_answer,explain))
                    }
                    list
                } else {
                    Toast.makeText(context, "No data retired", Toast.LENGTH_SHORT).show()
                    null
                }
            } else {
                Toast.makeText(context, "Data is null", Toast.LENGTH_SHORT).show()
                null
            }
        } catch (e: Exception) {
            Toast.makeText(context, "getalldata" + e.message + tab_name , Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            null
        }
    val upgradeVersion: Int
        get() {
            val db = readableDatabase
            val qb = SQLiteQueryBuilder()
            val sqlSelect = arrayOf("MAX (version)")
            val sqlTables = "upgrades"
            qb.tables = sqlTables
            val c = qb.query(
                db, sqlSelect, null, null,
                null, null, null
            )
            var v = 0
            c.moveToFirst()
            if (!c.isAfterLast) {
                v = c.getInt(0)
            }
            c.close()
            return v
        }

    companion object {
        var tab_name: String? = ""
        private const val DATABASE_NAME = "quiz.db"
        private const val DATABASE_VERSION = 1
    }

    init {
        setForcedUpgrade(3)
    }
}