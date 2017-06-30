package com.poussiere.babybooh.mainFragment2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.poussiere.babybooh.R;

import java.util.Calendar;

import static java.security.AccessController.getContext;

/**
 * Created by poussiere on 28/06/17.
 */


public class CarnetRecyclerView extends RecyclerView.Adapter< CarnetRecyclerView.CarnetViewHolder> {

    private int tabIcones [] = {R.drawable.ic_monstre1, R.drawable.ic_monstre2,
            R.drawable.ic_monstre3, R.drawable.ic_monstre4, R.drawable.ic_monstre5,
            R.drawable.ic_monstre6, R.drawable.ic_monstre7, R.drawable.ic_monstre8,
            R.drawable.ic_monstre9, R.drawable.ic_monstre10, R.drawable.ic_monstre11,
            R.drawable.ic_monstre12 };


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
    private final CarnerOnClickHandler mClickHandler;
    private Cursor cursor;
    private Context context;



    public interface CarnerOnClickHandler {

        void onEventClick(int index);
    }


    //Constructor
    public CarnetRecyclerView (Context c, CarnerOnClickHandler coc)
    {
        mClickHandler=coc;
        context=c;
    }


    @Override
    public CarnetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, null, false);
        CarnetViewHolder viewHolder = new CarnetViewHolder(layoutView);
        return viewHolder;

    }



    @Override
    public void onBindViewHolder(CarnetViewHolder holder, int position) {

    cursor.moveToPosition(position);
        quelMonstre=cursor.getInt(4);

        holder.ic.setImageResource(tabIcones[quelMonstre-1]);


        // Récupération de la date et de l'heure
        date=cursor.getLong(2);
      /*  cal=Calendar.getInstance();
        cal.setTimeInMillis(date);
        annee = cal.get(Calendar.YEAR);
        mois = cal.get(Calendar.MONTH)+1;
        jour = cal.get (Calendar.DAY_OF_MONTH);
        heure = cal.get (Calendar.HOUR_OF_DAY);
        minutes = cal.get (Calendar.MINUTE);

       
        
        dateText=""+jour+"/"+mois+"/"+annee;

        if (minutes==0) heureText=""+heure+":00";
        else heureText=""+heure+":"+minutes;
        holder.dateTv.setText(dateText);
        holder.heureTv.setText(heureText);
*/
         String formattedDate = String.valueOf(DateUtils.getRelativeTimeSpanString(date));
        holder.dateTv.setText(formattedDate);
        
        String internationalHour = DateUtils.formatDateTime(context, date, DateUtils.FORMAT_SHOW_TIME);
        holder.heureTv.setText(internationalHour);
        
        //Récupération des lux
        lum=cursor.getInt(3);
        l = context.getString(R.string.lux);
        holder.luxTv.setText(String.valueOf(lum)+" "+ l);


        // Récupération des décibels et transformation en String
        db=String.valueOf(Math.round(cursor.getDouble(1)));
        d = context.getString(R.string.decibels);
        holder.decTv.setText(db+" "+d);



    }


    // Retourne la taille du cursor ou 0 s'il est null + Affiche le message correspondant s'il n'y a pas de données
    @Override
    public int getItemCount() {
       
        int cursorCount;
        if (cursor==null) cursorCount=0;
        else cursorCount=cursor.getCount;

        textViewNoData.setVisibility(cursorCount > 0 ? View.GONE : View.VISIBLE);
        
        return cursorCount;
    }


    //Method to pass the array with the links of movies posters images paths to the adapter when user switch between sort by popularity and sort by top rated
    public void setEventsCursor ( Cursor c)
    {
        cursor = c;
        notifyDataSetChanged();
    }





    public class CarnetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {


        private ImageView ic;
        private TextView dateTv;
        private TextView heureTv;
        private TextView luxTv;
        private TextView decTv;

        public CarnetViewHolder(View itemView) {
            super(itemView);
            ic=(ImageView)itemView.findViewById(R.id.imageMonstre);
            dateTv = (TextView) itemView.findViewById(R.id.dateView);
            heureTv = (TextView) itemView.findViewById(R.id.heureView);
            luxTv = (TextView) itemView.findViewById(R.id.luxView);
            decTv = (TextView) itemView.findViewById(R.id.decibelsView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int adapterPosition=getAdapterPosition();
            mClickHandler.onEventClick(adapterPosition);

        }
    }






}
