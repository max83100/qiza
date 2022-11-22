package com.quiza.ui.rv

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quiza.R
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
        var questionTxt: TextView
        var option1Txt: TextView
        var option2Txt: TextView
        var option3Txt: TextView
        var rightAnswerTxt: String = ""
        var explainTxt: TextView



        init {
            questionTxt = itemView.findViewById(R.id.name_quiz)
            option1Txt = itemView.findViewById(R.id.radio_button1)
            option2Txt = itemView.findViewById(R.id.radio_button2)
            option3Txt = itemView.findViewById(R.id.radio_button3)
            explainTxt = itemView.findViewById(R.id.explainText)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val currentItem = exampleList[position]
        holder.questionTxt.text = currentItem.question
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, SingleQuizActivity::class.java)
            intent.putExtra("question", currentItem.question)
            intent.putExtra("option1", currentItem.option1)
            intent.putExtra("option2", currentItem.option2)
            intent.putExtra("option3", currentItem.option3)
            intent.putExtra("rightAnswer", currentItem.rightAnswer)
            intent.putExtra("explain", currentItem.explain)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }




    }


