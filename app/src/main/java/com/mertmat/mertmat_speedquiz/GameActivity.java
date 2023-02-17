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
    int pointPlayer1;
    int pointPlayer2;


    // Variable composé des éléments composants la page
    private Button bt_True_1;
    private Button bt_True_2;
    private Button restart;
    private Button menu;
    private TextView score1;
    private TextView score2;
    private TextView name_Player_1;
    private TextView name_Player_2;
    private TextView winner_Game;


    /**
     * Exécutera ce que contient la fonction à la première exécution
     * de l'applis
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Permet l'ouverture d'un chemin pour récupérer ce que le joueur a saisit
        // Dans l'editText situé dans MainActivity
        Intent resultActivity = getIntent();
        String namePlayerOne = resultActivity.getStringExtra("namePlayerOne");
        String namePlayerTwo = resultActivity.getStringExtra("namePlayerTwo");


        // Initialisation des composants de la page activity_game.xml
        questionPlayer1 = findViewById(R.id.question_player_one);
        questionPlayer2 = findViewById(R.id.question_player_two);

        winner_Game = findViewById(R.id.winnerGame);

        name_Player_1 = findViewById(R.id.name_player_one);
        name_Player_1.setText(namePlayerOne);

        name_Player_2 = findViewById(R.id.name_player_two);
        name_Player_2.setText(namePlayerTwo);

        bt_True_1 = findViewById(R.id.elevatedButtons);

        // calcul si joueur à cliqué au bon moment
        // si non, on lui enlève un point, si oui on lui en ajoute un
        bt_True_1.setOnClickListener(View -> {
            if (question.isReponse() == 0) {
                pointPlayer1--;
            } else if (question.isReponse() == 1) {
                pointPlayer1++;
            }

            // Va afficher le score du joueur un
            score1.setText(String.valueOf(pointPlayer1));
            //Bloque le bouton "vrais" du joueur 1
            unlockPlayer(false);
        });


        bt_True_2 = findViewById(R.id.trueButtomsTwo);
        bt_True_2.setOnClickListener(View -> {
            if (question.isReponse() == 0) {
                pointPlayer2--;
            } else if (question.isReponse() == 1) {
                pointPlayer2++;
            }
            // Va afficher le score du joueur deux
            score2.setText(String.valueOf(pointPlayer2));
            //Bloque le bouton "vrais" du joueur 2
            unlockPlayer(false);
        });


        restart = findViewById(R.id.bt_restart);
        restart.setOnClickListener(view -> {
           this.finish();
            // Permet de relancer le jeu
            Intent thisElements = new Intent(GameActivity.this, GameActivity.class);
            // Réinitialise le gagnant
            winner_Game.setText("");
           // Remet les bons pseudos des joueurs
            thisElements.putExtra("namePlayerOne", namePlayerOne);
            thisElements.putExtra("namePlayerTwo", namePlayerTwo);
            // Lance l'activitée
           startActivity(thisElements);
        });

        menu = findViewById(R.id.bt_menu);
        menu.setOnClickListener(view -> {
            menu.setVisibility(View.GONE);
            // Si bouton cliqué, ouvrira la page du menu
            Intent gameActivity = new Intent(GameActivity.this, MainActivity.class);
            startActivity(gameActivity);
        });
        score1 = findViewById(R.id.score_player_one);
        score1.setText(getString(R.string.score_player_one));
        score2 = findViewById(R.id.score_player_two);
        score2.setText(getString(R.string.score_player_two));

        questionManager = new QuestionManager(this);

    }

    /**
     * Au lancement de la page, exécutera ce qui a dans la foncton
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Rend invisible et bloque les boutons
        restart.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        unlockPlayer(false);

        // Démarre le compteur
        startCountDownTimer();

    }

    /**
     * Déclenche la recherche de question/réponse et
     * stop le callback une fois terminé
     */
    private void startQuestionIterative() {
        handler = new Handler();
        questionRunnable = new Runnable() {
            @Override
            public void run(){
                // Détecter si c'est la dernière question de la liste
                // si non va afficher la question et permettre au joueur de cliquer
                // sur le bouton
                if(questionManager.hasNext()) {
                    unlockPlayer(true);
                    question = questionManager.getQuestion();

                    // écris la question en cours dans les champs textes des deux joueurs
                    questionPlayer1.setText(question.getQuestion());
                    questionPlayer2.setText(question.getQuestion());

                    handler.postDelayed(this,2000);
                    // Si oui, va lancer la procédure de fin de jeu
                } else {
                    // Vérifie quel joueur a gagné
                    if (pointPlayer1 > pointPlayer2) {
                        winner_Game.setText("Winner:" + String.valueOf(name_Player_1.getText()));
                    } else {
                        winner_Game.setText("Winner: " + String.valueOf(name_Player_2.getText()));
                    }
                    // Rends les boutons "menu" et "Rejouer" visible
                    restart.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);

                    // Affiche à la place de la question, que le jeu est terminé
                    questionPlayer1.setText(R.string.game_end_countdown);
                    questionPlayer2.setText(R.string.game_end_countdown);

                    unlockPlayer(false);
                }
            }
        };
        handler.postDelayed(questionRunnable,1000);
    }

    /**
     * Lance le timeur avant le début du jeu et
     * appel la variabe qui lance le jeu
     */
    private void startCountDownTimer(){
        unlockPlayer(false);
        new CountDownTimer(6000,1000){
            public void onTick(long millisUntilFinished) {
                //nom edit text et textbox
                questionPlayer1.setText(""+millisUntilFinished / 1000);
                questionPlayer2.setText(""+millisUntilFinished / 1000);
            }
            public void onFinish() {
                // grise les boutons "vrais"
                unlockPlayer(false);
                questionPlayer1.setText("C'est parti!");
                questionPlayer2.setText("C'est parti!");

                // lance le jeu
                startQuestionIterative();
            }
        }.start();
    }

    /**
     * Permet de déterminer si les boutons "vrais" peuvent être
     * pressé ou non
     * @param etat
     */
    private void unlockPlayer(boolean etat) {
        // false = grisé
        // true = accessible
       bt_True_1.setEnabled(etat);
       bt_True_2.setEnabled(etat);
    }


}