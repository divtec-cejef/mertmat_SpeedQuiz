package com.mertmat.mertmat_speedquiz;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/**
 * Fournit des questions l'une après l'autre
 */
public class QuestionManager {
    // Crée une ArrayList objet
    ArrayList<Question> questionQuizz = new ArrayList<>();
    //on initialise à -1 pour qu'ils prennent directement la premiere question
    private int currentQuestion = -1;


    /**
     * Constructeur de la classe
     * @param context
     */
    public QuestionManager(Context context) { questionQuizz = initQuestionList(context); }

    /**
     * Charge une liste de question depuis la DB.
     * @param context Le contexte de l'application pour passer la query
     * @return Une arraylist charger de Question
     */
    private ArrayList<Question> initQuestionList(Context context){
        ArrayList<Question> listQuestion = new ArrayList<>();
        SpeedQuizSqlite helper = new SpeedQuizSqlite(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(true,"quiz",new String[]{"idQuiz","question","reponse"},null,null,null,null,"idquiz",null);
        while(cursor.moveToNext()){
            listQuestion.add(new Question(cursor));
        }
        cursor.close();
        db.close();
        return listQuestion;
    }


    /**
     * Va retourner calculer un nombre randoms
     * dont on se servira pour la liste
     * @return retourne un nombre randoms
     */
    private int nbreAleatoireListe() {
        int max = questionQuizz.size();
        double randoms;
        randoms = Math.random() * max;
        return (int) Math.floor(randoms);
    }

    /**
     * Vérifie s'il y a encore un élément dans la liste
     * @return boolean
     */
    public boolean hasNext() {
        return questionQuizz.size() > 0;
    }

    /**
     * Renvoie une question aléatoire de la liste
     * et l'enlève en même temps
     * @return Qustion question aléatoire
     */
    public Question getQuestion() {
        // retire de la liste et retourne une question aléatoire
        return questionQuizz.remove(nbreAleatoireListe());
    }
}
