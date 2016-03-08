package com.example.shuweizhao.my2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private int score = 0;
    private int best = 0;
    private TextView tvScore;
    private TextView bestScore;
    private static Button newGame;
    private static MainActivity mainActivity = null;


    public MainActivity() {
        mainActivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.Score);
        tvScore.setBackgroundColor(0xffFFF9C4);
        tvScore.setGravity(Gravity.CENTER);
        bestScore = (TextView) findViewById(R.id.best);
        bestScore.setBackgroundColor(0xffFFF9C4);
        bestScore.setGravity(Gravity.CENTER);
        newGame = (Button)findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScore();
                GameView.getGameView().initView();
                GameView.getGameView().startGame();
            }
        });
    }

    public static Button getButton() {
        return newGame;
    }

    public void clearScore() {
        score = 0;
        showScore();
        showBestScore();
    }

    public void showScore() {
        tvScore.setText("Score\n" + score + "");
        if (score > best) {
            best = score;
            showBestScore();
        }
    }

    public void addScore(int s) {
        score += s;
        showScore();
    }

    public void showBestScore() {
        bestScore.setText("Best\n" + best + "");
    }



}
