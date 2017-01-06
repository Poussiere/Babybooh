package com.poussiere.babybooh.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by poussiere on 11/10/15.
 */
public class BddTableEvenement extends SQLiteOpenHelper {
    public static final String NOM_DE_LA_TABLE="Evenements";


    public static final String COL1="_id";
    public static final String COL2="decibels";
    public static final String COL3="date";
    public static final String COL4="lumiere";
    public static final String COL5="monstre";

    public static final String TEXTE_DE_CREATION = "CREATE TABLE "+ NOM_DE_LA_TABLE+
            " ( "+COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL2+" INTEGER, "+COL3+
            " INTEGER, "+COL4+" INTEGER, "+ COL5+" INTEGER);";

    public static final String CHECK_VERSION="DROP TABLE IF EXISTS "+NOM_DE_LA_TABLE;

    public BddTableEvenement(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {super(context,name,factory,version);}

    @Override
    public void onCreate(SQLiteDatabase db)

    {
        db.execSQL(TEXTE_DE_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(CHECK_VERSION);
        onCreate(db);
    }




}
