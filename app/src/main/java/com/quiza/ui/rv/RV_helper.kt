package com.quiza.ui.rv

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper


class RV_helper(var context: Context, data: String?) : SQLiteAssetHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    var id: Int? = 0
    var option1: String? = null
    var option2: String? = null
    var option3: String? = null
    var explain: String? = null
    var fav: String? = null
    var question_text: String? = null
    var right_answer: Int? = 0
    var tab_name: String? = data
    val allData: ArrayList<Data>?
        get() = try {
            val list = ArrayList<Data>()
            val sqLiteDatabase = writableDatabase
            if (sqLiteDatabase != null) {
                val cursor = sqLiteDatabase.rawQuery("select * from "+ tab_name, null)
                if (cursor.count != 0) {
                    while (cursor.moveToNext()) {
                        id = cursor.getInt(5)
                        question_text = cursor.getString(1)
                        option1 = cursor.getString(2)
                        option2 = cursor.getString(3)
                        option3 = cursor.getString(4)
                        right_answer = cursor.getInt(5)
                        explain = cursor.getString(6)
                        fav = cursor.getString(7)

                        list.add(Data(id!!.toString(),question_text.toString(), option1.toString(), option2.toString(), option3.toString(), right_answer!!.toInt(),explain.toString(),fav!!))
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

    fun insertFav(question_text: String,option1: String,option2: String,option3: String,right_answer: Int,explain: String,fav: String): Boolean{
      val db: SQLiteDatabase = this.getWritableDatabase()
        val cv: ContentValues = ContentValues()
        cv.put("question",question_text)
        cv.put("option1",option1)
        cv.put("option2",option2)
        cv.put("option3",option3)
        cv.put("answer",right_answer)
        cv.put("explain",explain)
        if(fav == "0"){
            cv.put("favorite","1")
        }
        else{
            cv.put("favorite","0")
        }

        val res: Long = db.insert("fav",null,cv)
        return !res.equals(-1)
    }

    fun deleteFavItem(id: Int): Int{
        val db: SQLiteDatabase = this.getWritableDatabase()
        return db.delete("fav", "id =?", null)

    }



}