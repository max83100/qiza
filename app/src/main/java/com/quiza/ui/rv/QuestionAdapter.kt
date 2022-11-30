package com.quiza.ui.rv
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quiza.R


class QuestionAdapter(exampleList: ArrayList<Data>,context: Context) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    private val exampleList: List<Data>
    private val exampleListFull: List<Data>
    lateinit var rvHelper: RV_helper
     var context: Context



    init {
        this.exampleList = exampleList
        exampleListFull = ArrayList(exampleList)
        this.context = context
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var questionTxt: TextView


        init {
            questionTxt = itemView.findViewById(R.id.name_quiz)



        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.quiza.R.layout.card_view, parent, false)
        rvHelper = RV_helper(context,"") //may be a problem
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)
        if (firstStart) {
            createTableOnFirstStart()
        }
        return ViewHolder(v)
    }

    private fun createTableOnFirstStart() {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()
    }

    @SuppressLint("Range")
    private fun readCursorData(dataItem: Data, viewHolder: ViewHolder) {
        val cursor: Cursor? = dataItem.getid()?.let { rvHelper.read_all_data(it) }
        val db: SQLiteDatabase = rvHelper.getReadableDatabase()
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val item_fav_status: String =
                        cursor.getString(cursor.getColumnIndex(dataItem.fav))
                    dataItem.setFavStatus(item_fav_status)

                    //check fav status
                    if (item_fav_status == "1") {
                        btnFav.setBackgroundResource(com.quiza.R.drawable.favorite2)
                    } else if (item_fav_status == "0") {
                       btnFav.setBackgroundResource(com.quiza.R.drawable.favorite1)
                    }
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed()) cursor.close()
            db.close()
        }
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
        btnFav.setOnClickListener{
            likeClick(dataItem, favBtn = ImageButton(context))
        }

    }
    private fun likeClick (dataItem: Data, favBtn: ImageButton) {


        if (dataItem.getFavor().equals("0")) {
            dataItem.setFavStatus("1");
            rvHelper.insertIntoTheDatabase(
                dataItem.getid(), dataItem.getFavor()
            );
            favBtn.setBackgroundResource(R.drawable.favorite2);
            favBtn.setSelected(true);

        } else if (dataItem.getFavor().equals("1")) {
            dataItem.setFavStatus("0");
            dataItem.getid()?.let { rvHelper.removeFav(it) };
            favBtn.setBackgroundResource(R.drawable.favorite1);
            favBtn.setSelected(false);

        }
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }




    }


