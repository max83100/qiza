package com.quiza.ui.rv

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.quiza.R
import com.quiza.ui.ExplainActivity
import java.util.*

class SingleQuizActivity : AppCompatActivity() {
    private lateinit var textViewQuestion: TextView
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var textViewScore: TextView
    private lateinit var textViewQuestionCount: TextView
    private lateinit var textViewCountDown: TextView
    private lateinit var rbGroup: RadioGroup
    private lateinit var rb1: RadioButton
    private var explain: FloatingActionButton? = null
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var confirmNext: Button
    private lateinit var textColorsDefaultRb: ColorStateList
    private lateinit var textColorsDefaultCd: ColorStateList
    private var questionCounter = 0
    private var questionCountTotal = 1
    private var answered = false
    private var backPressedTime: Long = 0
    private var questionBundle: String? = null
    private var option1Bundle: String? = null
    private var option2Bundle: String? = null
    private var option3Bundle: String? = null
    private var rightAnswerBundle: String? = null
    private var explainBundle: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        questionBundle = intent.getStringExtra("question")
        option1Bundle = intent.getStringExtra("option1")
        option2Bundle = intent.getStringExtra("option2")
        option3Bundle = intent.getStringExtra("option3")
        rightAnswerBundle = intent.getIntExtra("rightAnswer",0).toString()
        explainBundle = intent.getStringExtra("explain")
        textViewQuestion = findViewById(R.id.text_question)
        textViewScore = findViewById(R.id.score_quiz)
        textViewQuestionCount = findViewById(R.id.text_qty_count)
        textViewCountDown = findViewById(R.id.timer)
        rbGroup = findViewById(R.id.radio_group)
        bottomBar = findViewById(R.id.bottomNavigationView)
        textViewScore.setVisibility(View.GONE)
        textViewCountDown.setVisibility(View.GONE)
        bottomBar.setVisibility(View.GONE)

        rb1 = findViewById(R.id.radio_button1)
        rb2 = findViewById(R.id.radio_button2)
        rb3 = findViewById(R.id.radio_button3)

        confirmNext = findViewById(R.id.check_answer)
        textViewCountDown = findViewById(R.id.timer)
        if (savedInstanceState == null) {
            showNextQuestion()
        } else {
            if (!answered) {
                checkAnswer()
            } else {
                showSolution()
            }
        }
        textColorsDefaultRb = rb1.linkTextColors
        textColorsDefaultCd = textViewCountDown.getLinkTextColors()
        confirmNext.setOnClickListener { view: View? ->
            if (!answered) {
                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                    checkAnswer()
                } else {
                    Toast.makeText(this@SingleQuizActivity, "Выберите ответ", Toast.LENGTH_LONG).show()
                }
            } else {
                showNextQuestion()
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showNextQuestion() {
        explain?.setVisibility(View.GONE)
        rb1.setTextColor(Color.BLACK)
        rb2.setTextColor(Color.BLACK)
        rb3.setTextColor(Color.BLACK)
        rbGroup.clearCheck()
        if (questionCounter < questionCountTotal) {
            textViewQuestion.text = questionBundle
            rb1.text = "A: " + option1Bundle
            rb2.text = "B: " + option2Bundle
            rb3.text = "C: " + option3Bundle
            textViewQuestionCount.text = "Вопрос: 1 из 1"
            answered = false
            confirmNext.text = "Проверить"

        } else {
            //finishQuiz()
        }
    }





    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        showSolution()
        answered = true
    }

    private fun showSolution() {
        rb1.setTextColor(Color.RED)
        rb2.setTextColor(Color.RED)
        rb3.setTextColor(Color.RED)
        when (rightAnswerBundle?.toInt()) {
            1 -> {
                rb1.setTextColor(Color.GREEN)
                textViewQuestion.text = "Ответ A правильный"
            }
            2 -> {
                rb2.setTextColor(Color.GREEN)
                textViewQuestion.text = "Ответ B правильный"
            }
            3 -> {
                rb3.setTextColor(Color.GREEN)
                textViewQuestion.text = "Ответ C правильный"
            }
        }
        if (questionCounter < questionCountTotal) {
            confirmNext.text = "Вернуться к списку вопросов"
            confirmNext.setOnClickListener({
                onBackPressed()
            }
            )
            explain = findViewById(R.id.explain)
            explain!!.setVisibility(View.VISIBLE)
            explain?.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@SingleQuizActivity, ExplainActivity::class.java)
                intent.putExtra("explainText", explainBundle)
                startActivity(intent)
            })


        } else {
            confirmNext.text = "Финиш"
        }
    }

    private fun finishQuiz() {
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}