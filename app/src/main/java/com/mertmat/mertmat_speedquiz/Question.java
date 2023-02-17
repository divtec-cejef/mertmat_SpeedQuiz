package com.mertmat.mertmat_speedquiz;

import android.database.Cursor;

/**
 * Classe qui représente une question
 */
public class Question {
    //Une question à poser
    private String question;
    //La réponse oui/non à la question
    private int reponse;
    public Question(){};

    /**
     * Constructeur de la classe Question
     * @param cursor cursor
     */
    public Question(Cursor cursor){
        question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
        reponse = cursor.getInt(cursor.getColumnIndexOrThrow("reponse"));
    }

    /**
     * Retourne la question
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Retourne la réponse
     * @return reponse
     */
    public int isReponse() {
        return reponse;
    }
}
