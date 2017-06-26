package com.poussiere.babybooh.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poussiere on 11/10/15.
 */
/*

public class BddDAO {

   public static final int NUM_VERSION=2;
    public static final String NOM_FICHIER="evenements.db";


    private SQLiteDatabase maBase;
    private BddTableEvenement helper;
    private String [] toutesColonnes ={BddTableEvenement.COL1, BddTableEvenement.COL2, BddTableEvenement.COL3, BddTableEvenement.COL4, BddTableEvenement.COL5};

    public BddDAO(Context context){

        helper=new BddTableEvenement(context, NOM_FICHIER, null, NUM_VERSION);}

    public void open() {
        maBase = helper.getWritableDatabase();}

    public void close()
    {helper.close();}

    public BddEvenement creerEntree(double decibels, long date, int lumiere, int monstre) {
        ContentValues values = new ContentValues();
        values.put(BddTableEvenement.COL2, decibels);
        values.put(BddTableEvenement.COL3, date);
        values.put(BddTableEvenement.COL4, lumiere);
        values.put(BddTableEvenement.COL5, monstre);

        long insertId = maBase.insert(BddTableEvenement.NOM_DE_LA_TABLE, null, values);

        Cursor cursor = maBase.query(BddTableEvenement.NOM_DE_LA_TABLE, toutesColonnes, BddTableEvenement.COL1 + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        BddEvenement newEvenement = cursorToEvenement(cursor);
        cursor.close();
        return newEvenement;

    }

    // Méthode pour supprimer une entrée à partir d'un objet EVENEMENT
    public void deleteEntree( BddEvenement evenement){

        long id = evenement.getId();
        maBase.delete(BddTableEvenement.NOM_DE_LA_TABLE, BddTableEvenement.COL1 + " = " + id, null);
    }

    // Méthode pour supprimer toutes les entrées
    public void delete (){

        maBase.delete(BddTableEvenement.NOM_DE_LA_TABLE, null, null);
    }


    // Méthode pour renvoyer l'ensemble un cursor afin de creer une custom listview
     public Cursor getCursor()
    {


        Cursor cursor = maBase.query(BddTableEvenement.NOM_DE_LA_TABLE, toutesColonnes, null, null, null, null, BddTableEvenement.COL1+" DESC");


        cursor.moveToFirst();
        return cursor;


    }

    //Méthode pour récupérer l'ensemble des évenements survenus dans un objet List
    public List<BddEvenement> getAllEvenements()
    {
        List <BddEvenement> evenements = new ArrayList<BddEvenement>();

        Cursor cursor = maBase.query(BddTableEvenement.NOM_DE_LA_TABLE, toutesColonnes, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
           BddEvenement evenement=cursorToEvenement(cursor);
            evenements.add(evenement);
            cursor.moveToNext();
        }
        cursor.close();
        return evenements;

    }


    private BddEvenement cursorToEvenement(Cursor cursor) {
        BddEvenement evenement = new BddEvenement();
        evenement.setId(cursor.getInt(0));
        evenement.setDecibels(cursor.getDouble(1));
        evenement.setDate(cursor.getLong(2));
        evenement.setLum(cursor.getInt(3));
        evenement.setMonstre(cursor.getInt(4));
        return evenement ;
    }


}
*/