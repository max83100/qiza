package com.quiza

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.quiza.R
import android.content.Intent
import android.view.View
import android.widget.Button
import com.quiza.QuizActivity

class StartingScreenActivity : AppCompatActivity() {
    private var textHighscore: TextView? = null
    private var highscore = 0
    var newscore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)
        val start = findViewById<Button>(R.id.start_quiz)
        textHighscore = findViewById(R.id.text_score)
        loadHighscore()
        start.setOnClickListener { view: View? -> startQuiz() }
    }

    private fun startQuiz() {
        val intent = Intent(this@StartingScreenActivity, QuizActivity::class.java)
        startActivity(intent)
    }

    private fun loadHighscore() {
        textHighscore!!.text = "Общий счёт: $newscore"
    }

    private fun updateHighScore(newHighScore: Int) {
        highscore = newHighScore
        textHighscore!!.text = "Общий счёт: $newscore"
    }
}