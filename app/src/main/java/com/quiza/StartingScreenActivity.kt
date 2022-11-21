package com.quiza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quiza.ui.CategoryActivity
import com.quiza.ui.ExplainActivity
import com.quiza.ui.QuizActivity

class StartingScreenActivity : AppCompatActivity() {
    private var textHighscore: TextView? = null
    private var highscore = 0
    private lateinit var bottomBar: BottomNavigationView
    var newscore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)
        val start = findViewById<Button>(R.id.start_quiz)
        textHighscore = findViewById(R.id.text_score)
        bottomBar = findViewById(R.id.bottom_main)
        loadHighscore()
        start.setOnClickListener { view: View? -> startQuiz() }
        bottomBar()
    }

    private fun bottomBar() {
        bottomBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.quiz -> {
                    val intent: Intent  = Intent(this, ExplainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.questionList -> {
                    val intent: Intent  = Intent(this, StartingScreenActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.favorire -> {
                    val intent: Intent  = Intent(this, QuizActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun startQuiz() {
        val intent = Intent(this@StartingScreenActivity, CategoryActivity::class.java)
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
