package com.mertmat.mertmat_speedquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    // Permet de faire aller question
    Runnable questionRunnable=null;
    // permet de faire programme en parallèle à celui de base
    Handler handler;

    QuestionManager questionManager;
    MainActivity mainActivity;
    TextView questionPlayer1;
    TextView questionPlayer2;
    // Variable global qui va stocker le question en cours
    Question question;
    int pointJoueur1;
    int pointJoueur2;

    private Button bt_vrais_1;
    private Button bt_vrais_2;
    private Button rejouer;
    private Button menu;
    private TextView score1;
    private TextView score2;
    private TextView nom_joueur_1;
    private TextView nom_joueur_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent resultActivity = getIntent();
        String namePlayerOne = resultActivity.getStringExtra("namePlayerOne");
        String namePlayerTwo = resultActivity.getStringExtra("namePlayerTwo");

        // Initialisation des composants de la page activity_game.xml
        questionPlayer1 = findViewById(R.id.question_player_one);
        questionPlayer2 = findViewById(R.id.question_player_two);

        nom_joueur_1 = findViewById(R.id.name_player_one);
        nom_joueur_1.setText(namePlayerOne);

        nom_joueur_2 = findViewById(R.id.name_player_two);
        nom_joueur_2.setText(namePlayerTwo);

        bt_vrais_1 = findViewById(R.id.elevatedButtons);
        bt_vrais_1.setOnClickListener(View -> {
            if (question.isReponse() == 0) {
                pointJoueur1--;
            } else if (question.isReponse() == 1) {
                pointJoueur1++;
            }

            score1.setText(String.valueOf(pointJoueur1));

            unlockPlayer(false);
        });


        bt_vrais_2 = findViewById(R.id.trueButtomsTwo);
        bt_vrais_2.setOnClickListener(View -> {
            if (question.isReponse() == 0) {
                pointJoueur2--;
            } else if (question.isReponse() == 1) {
                pointJoueur2++;
            }

            score2.setText(String.valueOf(pointJoueur2));

            unlockPlayer(false);
        });


        rejouer = findViewById(R.id.bt_restart);
        rejouer.setOnClickListener(view -> {
           this.finish();
           // Permet de relancer le jeu
           Intent gameActivity = new Intent(GameActivity.this, GameActivity.class);
           // Remet les bons pseudos des joueurs
            gameActivity.putExtra("namePlayerOne", namePlayerOne);
            gameActivity.putExtra("namePlayerTwo", namePlayerTwo);
            // Lance l'activitée
           startActivity(gameActivity);
        });

        menu = findViewById(R.id.bt_menu);
        menu.setOnClickListener(view -> {
            menu.setVisibility(View.GONE);
            // Si bouton cliqué, ouvrira la page menu
            Intent gameActivity = new Intent(GameActivity.this, MainActivity.class);
            startActivity(gameActivity);
        });
        score1 = findViewById(R.id.score_player_one);
        score1.setText(getString(R.string.score_player_one));
        score2 = findViewById(R.id.score_player_two);
        score2.setText(getString(R.string.score_player_two));

        questionManager = new QuestionManager(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Rend invisible et bloque les boutons
        rejouer.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        unlockPlayer(false);

        // Démarre le compteur
        startCountDownTimer();

    }

    /**
     * Déclenche la recherche de question/réponse et stop le callback une fois terminé
     */
    private void startQuestionIterative() {
        handler = new Handler();
        questionRunnable = new Runnable() {
            @Override
            public void run(){
                //Détecter si c'est la dernière question
                if(questionManager.hasNext()) {
                    unlockPlayer(true);
                    question = questionManager.getQuestion();

                    // écris la question en cours dans les champs textes des deux joueurs
                    questionPlayer1.setText(question.getQuestion());
                    questionPlayer2.setText(question.getQuestion());

                    handler.postDelayed(this,2000);
                } else {

                    if (pointJoueur1 > pointJoueur2) {

                    }
                    // Rends les boutons visible
                    rejouer.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);

                    questionPlayer1.setText(R.string.game_end_countdown);
                    questionPlayer1.setText(R.string.game_end_countdown);

                    unlockPlayer(false);
                }
            }
        };
        handler.postDelayed(questionRunnable,1000);
    }
    private void startCountDownTimer(){
        unlockPlayer(false);
        new CountDownTimer(6000,1000){
            public void onTick(long millisUntilFinished) {
                //nom edit text et textbox
                questionPlayer1.setText(""+millisUntilFinished / 1000);
                questionPlayer2.setText(""+millisUntilFinished / 1000);
            }
            public void onFinish() {
                //nom chaine caractere xml
                unlockPlayer(false);
                questionPlayer1.setText("C'est parti!");
                questionPlayer2.setText("C'est parti!");
                startQuestionIterative();
            }
        }.start();
    }

    private void unlockPlayer(boolean etat) {
        // false = grisé
        // true = accessible
       bt_vrais_1.setEnabled(etat);
       bt_vrais_2.setEnabled(etat);
    }


}