package com.poussiere.babybooh.mainFragment3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.objets.Monstre;

public class IdMonstreActivity extends Activity implements ParallaxMonster.OnScrollChangedListener {


    private int idMonstre;
    private Intent i;
    private int photoResourceId;
    private ParallaxMonster parallaxMonster;
    private View conteneur;
    private ImageView photoId;

    String monstreSexy = null; //car en String
    SharedPreferences prefs=null;
    Boolean monstreDebloque=null;

    @Override
    public void onScrollChanged(int deltaX, int deltaY) {
        int scrollY = parallaxMonster.getScrollY();
        // Add parallax effect
      //  conteneur.setTranslationY(scrollY * 0.6f);// regler ici entre 0 et 1 l'intensite de l'effet
         conteneur.setTranslationY(scrollY * 1.0F);// regler ici entre 0 et 1 l'intensite de l'effet
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Force l'activité à etre en plein ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_id_monstre);

        //Pour adapeter les dimensions de l'image à la résolution de l'écran:
        photoId = (ImageView)findViewById(R.id.photoId);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        double heightD = width*1.1875; // car les images font toutes 1600x1900 soit un ratio de 1.1875
        int height=(int)heightD;
        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        photoId.setLayoutParams(parms);



        parallaxMonster = (ParallaxMonster)findViewById(R.id.scroll_view);
        parallaxMonster.setOnScrollChangedListener(this);
        // Store the reference of your image container
        conteneur = findViewById(R.id.conteneur);

        i=getIntent();
        idMonstre=i.getIntExtra("idMonstre",1);

        TextView nomTx=(TextView)findViewById(R.id.nomTx);
        TextView aliasTx=(TextView)findViewById(R.id.aliasTx);
        TextView descriptionTx=(TextView)findViewById(R.id.descriptionTx);
        TextView conditionsTx=(TextView)findViewById(R.id.conditionsTx);

       monstreSexy= Monstre.quelMonstreString(idMonstre);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
       boolean monstreDebloque=prefs.getBoolean(monstreSexy, false);

        if (!monstreDebloque)
        {
            photoResourceId=R.drawable.interrogationbig;
            //photoId.setImageResource(R.drawable.interrogationbig);
            nomTx.setText(R.string.inconnu);
            aliasTx.setText(R.string.inconnu);
            descriptionTx.setText(R.string.inconnu_description);
            Glide.with(this).load(photoResourceId).into(photoId);
            }

        else {

            switch (idMonstre) {
                case 1:
                    photoResourceId=R.drawable.monstre1;
                 //   photoId.setImageResource(R.drawable.monstre1);
                    nomTx.setText(R.string.monstre1_nom);
                    aliasTx.setText(R.string.monstre1_alias);
                    descriptionTx.setText(R.string.monstre1_desc);
                    conditionsTx.setText(R.string.monstre1_conditions);
                    break;
                case 2:
                    photoResourceId=R.drawable.monstre2;
                 //   photoId.setImageResource(R.drawable.monstre2);
                    nomTx.setText(R.string.monstre2_nom);
                    aliasTx.setText(R.string.monstre2_alias);
                    descriptionTx.setText(R.string.monstre2_desc);
                    conditionsTx.setText(R.string.monstre2_conditions);
                    break;
                case 3:
                    photoResourceId=R.drawable.monstre3;
                  //  photoId.setImageResource(R.drawable.monstre3);
                    nomTx.setText(R.string.monstre3_nom);
                    aliasTx.setText(R.string.monstre3_alias);
                    descriptionTx.setText(R.string.monstre3_desc);
                    conditionsTx.setText(R.string.monstre3_conditions);
                    break;
                case 4:
                    photoResourceId=R.drawable.monstre4;
                  //  photoId.setImageResource(R.drawable.monstre4);
                    nomTx.setText(R.string.monstre4_nom);
                    aliasTx.setText(R.string.monstre4_alias);
                    descriptionTx.setText(R.string.monstre4_desc);
                    conditionsTx.setText(R.string.monstre4_conditions);
                    break;
                case 5:
                    photoResourceId=R.drawable.monstre5;
                  //  photoId.setImageResource(R.drawable.monstre5);
                    nomTx.setText(R.string.monstre5_nom);
                    aliasTx.setText(R.string.monstre5_alias);
                    descriptionTx.setText(R.string.monstre5_desc);
                    conditionsTx.setText(R.string.monstre5_conditions);
                    break;
                case 6:
                    photoResourceId=R.drawable.monstre6;
                  //  photoId.setImageResource(R.drawable.monstre6);
                    nomTx.setText(R.string.monstre6_nom);
                    aliasTx.setText(R.string.monstre6_alias);
                    descriptionTx.setText(R.string.monstre6_desc);
                    conditionsTx.setText(R.string.monstre6_conditions);
                    break;
                case 7:
                    photoResourceId=R.drawable.monstre7;
                 //   photoId.setImageResource(R.drawable.monstre7);
                    nomTx.setText(R.string.monstre7_nom);
                    aliasTx.setText(R.string.monstre7_alias);
                    descriptionTx.setText(R.string.monstre7_desc);
                    conditionsTx.setText(R.string.monstre7_conditions);
                    break;
                case 8:
                    photoResourceId=R.drawable.monstre8;
                  //  photoId.setImageResource(R.drawable.monstre8);
                    nomTx.setText(R.string.monstre8_nom);
                    aliasTx.setText(R.string.monstre8_alias);
                    descriptionTx.setText(R.string.monstre8_desc);
                    conditionsTx.setText(R.string.monstre8_conditions);
                    break;
                case 9:
                    photoResourceId=R.drawable.monstre9;
                   // photoId.setImageResource(R.drawable.monstre9);
                    nomTx.setText(R.string.monstre9_nom);
                    aliasTx.setText(R.string.monstre9_alias);
                    descriptionTx.setText(R.string.monstre9_desc);
                    conditionsTx.setText(R.string.monstre9_conditions);
                    break;
                case 10:
                    photoResourceId=R.drawable.monstre10;
                  //  photoId.setImageResource(R.drawable.monstre10);
                    nomTx.setText(R.string.monstre10_nom);
                    aliasTx.setText(R.string.monstre10_alias);
                    descriptionTx.setText(R.string.monstre10_desc);
                    conditionsTx.setText(R.string.monstre10_conditions);
                    break;
                case 11:
                    photoResourceId=R.drawable.monstre11;
                  //  photoId.setImageResource(R.drawable.monstre11);
                    nomTx.setText(R.string.monstre11_nom);
                    aliasTx.setText(R.string.monstre11_alias);
                    descriptionTx.setText(R.string.monstre11_desc);
                    conditionsTx.setText(R.string.monstre11_conditions);
                    break;
                case 12:
                    photoResourceId=R.drawable.monstre12;
                 //   photoId.setImageResource(R.drawable.monstre12);
                    nomTx.setText(R.string.monstre12_nom);
                    aliasTx.setText(R.string.monstre12_alias);
                    descriptionTx.setText(R.string.monstre12_desc);
                    conditionsTx.setText(R.string.monstre12_conditions);
                    break;
            }
            Glide.with(this).load(photoResourceId).into(photoId);
        }
    }

}
