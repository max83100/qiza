package com.quiza.ui.rv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quiza.R

class QuestionRV : AppCompatActivity() {
    var favBtn: ImageButton = findViewById(R.id.favorite_rv)
    var data: String? = ""
    var myDB: RV_helper? = null
    var list: ArrayList<Data>? = null
    var customAdapter: QuestionAdapter? = null
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_rv)

        //favBtn = findViewById(R.id.favorite_rv)
        data = intent.getStringExtra("tabName")
        recyclerView = findViewById(R.id.recycler)
        myDB = RV_helper(this,data)
        RV_helper.tab_name = data.toString()
        list = ArrayList()
        showData(recyclerView)

    }

    fun showData(view: View?) {
        favBtn.setOnClickListener{
            myDB?.question_text?.let { it1 -> myDB!!.option1?.let { it2 ->
                myDB!!.option2?.let { it3 ->
                    myDB!!.option3?.let { it4 ->
                        myDB!!.right_answer?.let { it5 ->
                            myDB!!.explain?.let { it6 ->
                                myDB!!.fav?.let { it7 ->
                                    myDB!!.insertFav(it1,
                                        it2, it3, it4, it5, it6, it7
                                    )
                                }
                            }
                        }
                    }
                }
            } }
        }

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