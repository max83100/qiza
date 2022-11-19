package com.quiza.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.quiza.R
import com.quiza.data.Db_helper
import com.quiza.data.Question
import java.util.*
import kotlin.collections.ArrayList

class QuizActivity : AppCompatActivity() {
    private lateinit var textViewQuestion: TextView
    private lateinit var textViewScore: TextView
    private lateinit var textViewQuestionCount: TextView
    private lateinit var textViewCountDown: TextView
    private lateinit var rbGroup: RadioGroup
    private lateinit var rb1: RadioButton
    private var explain: FloatingActionButton? = null
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var confirmNext: Button
    lateinit var textColorsDefaultRb: ColorStateList
    lateinit var textColorsDefaultCd: ColorStateList
    private lateinit var countDownTimer: CountDownTimer
    private var timeLiftInMillis: Long = 0
    private var questionCounter = 0
    private var questionCountTotal = 0
    private lateinit var currenrQuestion: Question
    private var score = 0
    private var answered = false
    private var questionList: ArrayList<Question>? = ArrayList()
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        textViewQuestion = findViewById(R.id.text_question)
        textViewScore = findViewById(R.id.score_quiz)
        textViewQuestionCount = findViewById(R.id.text_qty_count)
        textViewCountDown = findViewById(R.id.timer)
        rbGroup = findViewById(R.id.radio_group)

        rb1 = findViewById(R.id.radio_button1)
        rb2 = findViewById(R.id.radio_button2)
        rb3 = findViewById(R.id.radio_button3)


        confirmNext = findViewById(R.id.check_answer)
        textViewCountDown = findViewById(R.id.timer)
        if (savedInstanceState == null) {
            val dbHelper = Db_helper(this)
            questionList = dbHelper.allData
            questionCountTotal = questionList!!.size
            questionList?.let { Collections.shuffle(it) }
            showNextQuestion()
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST)
            questionCountTotal = questionList!!.size
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT)
            currenrQuestion = questionList!![questionCounter - 1]
            score = savedInstanceState.getInt(KEY_SCORE)
            timeLiftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT)
            answered = savedInstanceState.getBoolean(KEY_ANSWERED)
            if (!answered) {
                startCountDown()
            } else {
                updateCountDownText()
                showSolution()
            }
        }
        textColorsDefaultRb = rb1.getLinkTextColors()
        textColorsDefaultCd = textViewCountDown.getLinkTextColors()
        confirmNext.setOnClickListener { view: View? ->
            if (!answered) {
                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                    checkAnswer()
                } else {
                    Toast.makeText(this@QuizActivity, "Выберите ответ", Toast.LENGTH_LONG).show()
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
            currenrQuestion = questionList!![questionCounter]
            textViewQuestion.text = currenrQuestion.question
            rb1.text = currenrQuestion.option1
            rb2.text = currenrQuestion.option2
            rb3.text = currenrQuestion.option3
            questionCounter++
            textViewQuestionCount.text = "Вопрос: $questionCounter из $questionCountTotal"
            answered = false
            confirmNext.text = "Проверить"
            timeLiftInMillis = COUNTDOWN_IN_MILLS
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
        if (answerNr == currenrQuestion.answer_number) {
            score += 1
            textViewScore!!.text = "Счёт: $score"
        }
        showSolution()
    }

    private fun showSolution() {
        rb1.setTextColor(Color.RED)
        rb2.setTextColor(Color.RED)
        rb3.setTextColor(Color.RED)
        when (currenrQuestion.answer_number) {
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
            confirmNext.text = "Следующий"
            explain = findViewById(R.id.explain)
            explain!!.setVisibility(View.VISIBLE)
            explain?.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@QuizActivity, ExplainActivity::class.java)
                intent.putExtra("explainText", currenrQuestion.explain)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SCORE, score)
        outState.putInt(KEY_QUESTION_COUNT, questionCounter)
        outState.putLong(KEY_MILLIS_LEFT, timeLiftInMillis)
        outState.putBoolean(KEY_ANSWERED, answered)
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList)
    }

    companion object {
        private const val KEY_SCORE = "keyScore"
        private const val KEY_QUESTION_COUNT = "keyQuestionCount"
        private const val KEY_MILLIS_LEFT = "keyMillisLeft"
        private const val KEY_ANSWERED = "keyAnswered"
        private const val KEY_QUESTION_LIST = "keyQuestionList"
        private const val COUNTDOWN_IN_MILLS: Long = 30000
    }
}