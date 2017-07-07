package com.poussiere.babybooh.mainFragment2;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poussiere.babybooh.R;
import com.poussiere.babybooh.bdd.Contract;

import java.util.Calendar;

/**
 * Created by poussiere on 2/2/16.
 */
public class MyCursorAdapter extends CursorAdapter {

    private int tabIcones [] = {R.drawable.ic_monstre1, R.drawable.ic_monstre2,
            R.drawable.ic_monstre3, R.drawable.ic_monstre4, R.drawable.ic_monstre5,
            R.drawable.ic_monstre6, R.drawable.ic_monstre7, R.drawable.ic_monstre8,
            R.drawable.ic_monstre9, R.drawable.ic_monstre10, R.drawable.ic_monstre11,
            R.drawable.ic_monstre12 };

    private ImageView ic;
    private TextView dateTv;
    private TextView heureTv;
    private TextView luxTv;
    private TextView decTv;
    private int quelMonstre;
    private long date;
    private Calendar cal;
    private int annee;
    private int mois;
    private int jour;
    private int heure;
    private int minutes;
    private String dateText;
    private  String heureText;
    private int lum;
    private String l;
    private String db;
    private String d;

    public MyCursorAdapter (Context context, Cursor cursor, int flags)
    {super(context, cursor, 0);}



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {return LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);}


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ic=(ImageView)view.findViewById(R.id.imageMonstre);
        dateTv = (TextView) view.findViewById(R.id.dateView);
        heureTv = (TextView) view.findViewById(R.id.heureView);
        luxTv = (TextView) view.findViewById(R.id.luxView);
        decTv = (TextView) view.findViewById(R.id.decibelsView);


        //Pour faire plus simple (??) il y a également la possibilité d'utiliser la méthode cursorToEvenement de BddDAO

        //Récupération du monstre pour l'image
        quelMonstre=cursor.getInt(Contract.Evenements.POSITION_COL5);

        ic.setImageResource(tabIcones[quelMonstre-1]);


       // Récupération de la date et de l'heure
        date=cursor.getLong(Contract.Evenements.POSITION_COL3);
        cal=Calendar.getInstance();
        cal.setTimeInMillis(date);
        annee = cal.get(Calendar.YEAR);
        mois = cal.get(Calendar.MONTH)+1;
        jour = cal.get (Calendar.DAY_OF_MONTH);
        heure = cal.get (Calendar.HOUR_OF_DAY);
        minutes = cal.get (Calendar.MINUTE);

        dateText=""+jour+"/"+mois+"/"+annee;

        if (minutes==0) heureText=""+heure+":00";
        else heureText=""+heure+":"+minutes;
        dateTv.setText(dateText);
        heureTv.setText(heureText);

        //Récupération des lux
        lum=cursor.getInt(Contract.Evenements.POSITION_COL4);
        l = context.getString(R.string.lux);
        luxTv.setText(String.valueOf(lum)+" "+ l);


        // Récupération des décibels et transformation en String
        db=String.valueOf(Math.round(cursor.getDouble(1)));
        d = context.getString(R.string.decibels);
        decTv.setText(db+" "+d);



    }





}
