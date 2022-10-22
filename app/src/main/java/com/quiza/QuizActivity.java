package com.quiza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {



    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView textViewQuestion;
    private TextView textViewScore ;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button confirmNext;

    ColorStateList textColorsDefaultRb;
    ColorStateList textColorsDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLiftInMillis;


    private int questionCounter;
    private int questionCountTotal;
    private Question currenrQuestion;
    private int score = 0;
    private boolean answered;

    private ArrayList<Question> questionList;
    Bundle bundle = new Bundle();
    private long backPressedTime;
    private static final long COUNTDOWN_IN_MILLS = 30000;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.text_question);
        textViewScore = findViewById(R.id.score_quiz);
        textViewQuestionCount = findViewById(R.id.text_qty_count);
        textViewCountDown = findViewById(R.id.timer);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        confirmNext = findViewById(R.id.check_answer);
        textViewCountDown = findViewById(R.id.timer);


        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        textColorsDefaultRb = rb1.getLinkTextColors();
        textColorsDefaultCd = textViewCountDown.getLinkTextColors();


        showNextQuestion();

        confirmNext.setOnClickListener(view -> {
            if(!answered){
                if(rb1.isChecked() || rb2.isChecked() ||rb3.isChecked() ){
                    checkAnswer();
                }
                else{
                    Toast.makeText(QuizActivity.this,"Выберите ответ",Toast.LENGTH_LONG).show();
                }
            }
            else{
                showNextQuestion();
            }

        });
    }

    @SuppressLint("SetTextI18n")
    private void showNextQuestion() {
        rb1.setTextColor(textColorsDefaultRb);
        rb2.setTextColor(textColorsDefaultRb);
        rb3.setTextColor(textColorsDefaultRb);
        rbGroup.clearCheck();

        if(questionCounter < questionCountTotal){
            currenrQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currenrQuestion.getQuestion());
            rb1.setText(currenrQuestion.getOption1());
            rb2.setText(currenrQuestion.getOption2());
            rb3.setText(currenrQuestion.getOption3());
            questionCounter++;
            textViewQuestionCount.setText("Вопрос: " + questionCounter + " из " + questionCountTotal);
            answered = false;
            confirmNext.setText("Проверить");

            timeLiftInMillis = COUNTDOWN_IN_MILLS;
            startCountDown();
        }
        else {
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLiftInMillis,1000) {
            @Override
            public void onTick(long l) {
                timeLiftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLiftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minuts = (int)(timeLiftInMillis / 1000) / 60;
        int seconds = (int)(timeLiftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minuts,seconds);
        textViewCountDown.setText(timeFormatted);

        if(timeLiftInMillis < 10000){
            textViewCountDown.setTextColor(Color.RED);
        }
        else{
            textViewCountDown.setTextColor(textColorsDefaultCd);
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer() {
        answered = true;
        countDownTimer.cancel();
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if(answerNr == currenrQuestion.getAnswer_number()){
            score += 1  ;
            textViewScore.setText("Счёт: " + score);
        }

        showSolution();


    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currenrQuestion.getAnswer_number()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Ответ A правильный");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Ответ B правильный");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Ответ C правильный");
                break;
        }
        if(questionCounter < questionCountTotal){
            confirmNext.setText("Следующий");
        }
        else{
            confirmNext.setText("Финиш");
        }
    }



    private void finishQuiz() {
        finish();
    }



    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            finishQuiz();
        }
        else{
            Toast.makeText(this,"Нажмите еще раз назад для выхода",Toast.LENGTH_LONG).show();
        }
        backPressedTime = System.currentTimeMillis();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE,score);
        outState.putInt(KEY_QUESTION_COUNT,questionCounter);
        outState.putLong(KEY_MILLIS_LEFT,timeLiftInMillis);
        outState.putBoolean(KEY_ANSWERED,answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST,questionList);
    }
}