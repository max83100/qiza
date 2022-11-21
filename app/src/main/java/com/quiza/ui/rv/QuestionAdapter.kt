package com.quiza.ui.rv

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quiza.R
import com.quiza.ui.QuizActivity
import java.util.*
import kotlin.collections.ArrayList

class QuestionAdapter(exampleList: ArrayList<Data>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    private val exampleList: List<Data>
    private val exampleListFull: List<Data>

    init {
        this.exampleList = exampleList
        exampleListFull = ArrayList(exampleList)
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var questionDb: TextView


        init {
            questionDb = itemView.findViewById(R.id.name_quiz)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val currentItem = exampleList[position]
        holder.questionDb.text = currentItem.question
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, QuizActivity::class.java)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }




    }


