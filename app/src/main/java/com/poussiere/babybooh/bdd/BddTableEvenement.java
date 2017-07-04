package com.poussiere.babybooh.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by poussiere on 11/10/15.
 */
public class BddTableEvenement extends SQLiteOpenHelper {
    public static final String NOM_DE_LA_TABLE="Evenements";

    public static final String DATABASE_NAME="evenementsDb.db";
    private static final int VERSION = 3;

    public static final String TEXTE_DE_CREATION = "CREATE TABLE "+ Contract.Evenements.NOM_DE_LA_TABLE+
            " ( "+Contract.Evenements._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Contract.Evenements.COLUMN_COL2+" INTEGER, "+Contract.Evenements.COLUMN_COL3+
            " INTEGER, "+Contract.Evenements.COLUMN_COL4+" INTEGER, "+ Contract.Evenements.COLUMN_COL5+" INTEGER, "+Contract.Evenements.COLUMN_COL6+" INTEGER, "+
            Contract.Evenements.COLUMN_COL7+" INTEGER, "+Contract.Evenements.COMUMN_COL8+" INTEGER);";

    public static final String CHECK_VERSION="DROP TABLE IF EXISTS "+NOM_DE_LA_TABLE;



    public BddTableEvenement(Context context) {
        super(context, DATABASE_NAME, null, VERSION);}

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
