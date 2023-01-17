package com.mertmat.mertmat_speedquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

public class gameActivity extends AppCompatActivity {
    Runnable questionRunnable=null;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }


    /**
     * Déclenche la recherche de question/réponse et stop le callback une fois terminé
     */
    private void startQuestionIterative(){
        handler = new Handler();
        questionRunnable = new Runnable(){
            @Override
            public void run(){
                //Détecter si c'est la dernière question
                if(){
                    //Do last question process
                }else{
                    //Do question iterative
                    handler.postDelayed(this,2000);
                }
            }
        };
        handler.postDelayed(questionRunnable,1000);
    }
    private void startCountDownTimer(){
        new CountDownTimer(6000,1000){
            public void onTick(long millisUntilFinished){
                //nom edit text et textbox
                questionPlayer1.setText(""+millisUntilFinished / 1000);
                questionPlayer2.setText(""+millisUntilFinished / 1000);
            }
            public void onFinish(){
                //nom chaine caractere xml
                questionPlayer1.setText(getText(R.string.game_end_countdown));
                questionPlayer2.setText(getText(R.string.game_end_countdown));
                startQuestionIterative();
            }
        }.start();
    }
}