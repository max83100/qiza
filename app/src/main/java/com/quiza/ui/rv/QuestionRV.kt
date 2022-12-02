package com.quiza.ui.rv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quiza.R

class QuestionRV : AppCompatActivity() {
    var data: String? = ""
    var myDB: RV_helper? = null
    var list: ArrayList<Data>? = null
    var customAdapter: QuestionAdapter? = null
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_rv)

        data = intent.getStringExtra("tabName")
        recyclerView = findViewById(R.id.recycler)
        myDB = RV_helper(this,data)
        RV_helper.tab_name = data.toString()
        list = ArrayList()
        showData(recyclerView)
//
    }

    fun showData(view: View?) {
        try {
            list = myDB!!.allData
            customAdapter = list?.let { QuestionAdapter(it,this) }
            recyclerView!!.hasFixedSize()
            recyclerView!!.layoutManager = LinearLayoutManager(this)
            recyclerView!!.adapter = customAdapter
        } catch (e: Exception) {
            Toast.makeText(this, "show data" + e.message + data, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}