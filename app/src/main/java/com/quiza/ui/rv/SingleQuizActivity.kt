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
    private lateinit var countDownTimer: CountDownTimer
    private var timeLiftInMillis: Long = 0
    private var questionCounter = 0
    private var questionCountTotal = 1
    private var score = 0
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
        rightAnswerBundle = intent.getStringExtra("rightAnswer")
        explainBundle = intent.getStringExtra("explain")
        textViewQuestion = findViewById(R.id.text_question)
        textViewScore = findViewById(R.id.score_quiz)
        textViewQuestionCount = findViewById(R.id.text_qty_count)
        textViewCountDown = findViewById(R.id.timer)
        rbGroup = findViewById(R.id.radio_group)
        bottomBar = findViewById(R.id.bottomNavigationView)

        rb1 = findViewById(R.id.radio_button1)
        rb2 = findViewById(R.id.radio_button2)
        rb3 = findViewById(R.id.radio_button3)



        confirmNext = findViewById(R.id.check_answer)
        textViewCountDown = findViewById(R.id.timer)
        if (savedInstanceState == null) {
            showNextQuestion()
        } else {
            if (!answered) {
                startCountDown()
            } else {
                updateCountDownText()
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
                    val intent: Intent  = Intent(this, QuestionRV::class.java)
                    intent.putExtra("tabName", questionBundle)
                    startActivity(intent)
                    true
                }
                R.id.favorire -> {
                    val intent: Intent  = Intent(this, SingleQuizActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
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
            questionCounter++
            textViewQuestionCount.text = "Вопрос: 1 из 1"
            answered = false
            confirmNext.text = "Проверить"
            startCountDown()

        } else {
            finishQuiz()
        }
    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(timeLiftInMillis, 1000) {
            override fun onTick(l: Long) {
                timeLiftInMillis = l
                updateCountDownText()
            }

            override fun onFinish() {
                timeLiftInMillis = 0
                updateCountDownText()
                checkAnswer()
            }
        }.start()
    }

    private fun updateCountDownText() {
        val minuts = (timeLiftInMillis / 1000).toInt() / 60
        val seconds = (timeLiftInMillis / 1000).toInt() % 60
        val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minuts, seconds)
        textViewCountDown.text = timeFormatted
        if (timeLiftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED)
        } else {
            textViewCountDown.setTextColor(textColorsDefaultCd)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        answered = true
        countDownTimer.cancel()
        val rbSelected = findViewById<RadioButton>(rbGroup.checkedRadioButtonId)
        val answerNr = rbGroup.indexOfChild(rbSelected) + 1
        if (answerNr == rightAnswerBundle?.toInt()) {
            score += 1
            textViewScore!!.text = "Счёт: $score"
        }
        showSolution()
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
                val intent = Intent(this@SingleQuizActivity, QuestionRV::class.java)
                startActivity(intent)
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
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz()
        } else {
            Toast.makeText(this, "Нажмите еще раз назад для выхода", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }


}