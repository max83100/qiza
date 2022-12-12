package com.quiza.ui.rv
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.quiza.R


class QuestionAdapter(exampleList: ArrayList<Data>,context: Context) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    private val exampleList: List<Data>
    private val exampleListFull: List<Data>


     var context: Context
    var rvHelper: RV_helper = RV_helper(context,"")



    init {
        this.exampleList = exampleList
        exampleListFull = ArrayList(exampleList)
        this.context = context
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var questionTxt: TextView
        var btnFav: ImageButton


        init {
            questionTxt = itemView.findViewById(R.id.name_quiz)
            btnFav = itemView.findViewById(R.id.favorite_rv)



        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.quiza.R.layout.card_view, parent, false)
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
            intent.putExtra("explain", currentItem.fav)
            v.context.startActivity(intent)
        }
        holder.btnFav.setOnClickListener{
            rvHelper.insertFav(currentItem.question,currentItem.option1,currentItem.option2,currentItem.option3,currentItem.rightAnswer,currentItem.explain,currentItem.fav)
            notifyDataSetChanged()
           Toast.makeText(context,"added in favorite",Toast.LENGTH_LONG).show()
//            if(currentItem.fav == "1") {
//                holder.btnFav.setImageResource(R.drawable.favorite2)
//                rvHelper.deleteFavItem(currentItem.question)
//                Toast.makeText(context,"deleted favorite",Toast.LENGTH_LONG).show()
//                notifyDataSetChanged()
//            }
//            else{
//                currentItem.fav = "0"
//                rvHelper.insertFav(currentItem.question,currentItem.option1,currentItem.option2,currentItem.option3,currentItem.rightAnswer,currentItem.explain,currentItem.fav)
//                holder.btnFav.setImageResource(R.drawable.favorite1)
//                notifyDataSetChanged()
//            }
        }

    }


    override fun getItemCount(): Int {
        return exampleList.size
    }




    }


