package com.quiza.ui.fav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quiza.R
import com.quiza.ui.rv.Data
import com.quiza.ui.rv.QuestionAdapter
import com.quiza.ui.rv.RV_helper

class Favorite : AppCompatActivity() {
    var myDB: RV_helper? = null
    var list: ArrayList<Data>? = null
    var customAdapter: QuestionAdapter? = null
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_rv)


        recyclerView = findViewById(R.id.recycler)
        myDB = RV_helper(this,"fav")
        RV_helper.tab_name = "fav"
        list = ArrayList()
        showData(recyclerView)

    }

    fun showData(view: View?) {

        try {
            list = myDB!!.allData
            customAdapter = list?.let { QuestionAdapter(it,this) }
            recyclerView!!.hasFixedSize()
            recyclerView!!.layoutManager = LinearLayoutManager(this)
            recyclerView!!.adapter = customAdapter
        } catch (e: Exception) {
            Toast.makeText(this, "show data" + e.message + "fav", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        customAdapter = null
        myDB?.close()
        list = null
        super.onDestroy()
    }
}