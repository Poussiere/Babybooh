package com.poussiere.babybooh.mainFragment3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.poussiere.babybooh.R;
import com.poussiere.babybooh.objets.Monstre;

public class ChasseFullScreenActivity extends Activity {

    String monstreSexy = null; //car en String
    SharedPreferences prefs=null;
    Boolean monstreDebloque=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chasse_full_screen);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        MyImageAdapter imageAdapter = new MyImageAdapter(this);


        monstreSexy= Monstre.quelMonstreString(position);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        monstreDebloque=prefs.getBoolean(monstreSexy, false);
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);

        if (monstreDebloque)

        {

            imageView.setImageResource(imageAdapter.tabImages[position-1]);

        }

        else imageView.setImageResource(R.drawable.interrogation);
    }



}
