package com.mertmat.mertmat_speedquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SpeedQuizSqlite extends SQLiteOpenHelper {

    static String DB_NAME = "SpeedQuiz.db";
    static int DB_VERSION = 1;

    public SpeedQuizSqlite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    /**
     * C'est une base de données de questions qui sera configuré à l'exécution de l'applis
     * Ils contiendront la question et la réponse, 1 étant si c'est vrais et 0 si c'est faux
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDataTable = "CREATE TABLE quiz (idQuiz INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, reponse BOOLEAN)";
        db.execSQL(sqlCreateDataTable);
        // Permet de définir les question et directement donnée sir c'est vrais ou faux 0 - 1 avec boolean
        db.execSQL("INSERT INTO quiz VALUES (null,\"L'ours polaire est brun sous sa fourure\", 1)");
        db.execSQL("INSERT INTO quiz VALUES (null,\"Les requins ne tuent plus de gens que les noix de cocos \", 1)");
        db.execSQL("INSERT INTO quiz VALUES (null,\"La joconde n'a pas de sourcils\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null,\"La Suisse à l'allemand, le français, l'italien et le Suisse comme langues principaux\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null,\"Odin est utile\", 0)");
        db.execSQL("INSERT INTO quiz VALUES (null,\"Xavier n'a pas d'enfant dans sa cave\", 0)");

    }

    /**
     * Permet la modification de la base de donnée une fois l'application publié
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
