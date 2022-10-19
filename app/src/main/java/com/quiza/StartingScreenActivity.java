package com.quiza;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartingScreenActivity extends AppCompatActivity {


    private TextView textHighscore;
    private int highscore;
//    Bundle bundle = getIntent().getExtras();
//    int newscore = bundle.getInt("key");
    int newscore = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);
        Button start = findViewById(R.id.start_quiz);

        textHighscore = findViewById(R.id.text_score);
        loadHighscore();
        start.setOnClickListener(view -> startQuiz());
    }

    private void startQuiz() {
        Intent intent = new Intent(StartingScreenActivity.this,QuizActivity.class);
        startActivity(intent);


    }



    private void loadHighscore(){
        textHighscore.setText("Общий счёт: " + newscore);
    }

    private void updateHighScore(int newHighScore) {
        highscore = newHighScore;
        textHighscore.setText("Общий счёт: " + newscore);



    }
}