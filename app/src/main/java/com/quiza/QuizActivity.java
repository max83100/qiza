package com.quiza;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extra_score";

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
    private int questionCounter;
    private int questionCountTotal;
    private Question currenrQuestion;
    private int score = 0;
    private boolean answered;

    private List<Question> questionList;


    @Override
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
        }
        else {
            finishQuiz();
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer() {
        answered = true;
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
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE,score)
        setResult(RESULT_OK);
        finish();
    }
}