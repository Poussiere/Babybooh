package com.poussiere.babybooh.activite1;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;


import com.poussiere.babybooh.MainActivity;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.bdd.Contract;
import com.poussiere.babybooh.objets.Ecoute;
import com.poussiere.babybooh.objets.Lecture;
import com.poussiere.babybooh.objets.Monstre;

import java.io.IOException;
import java.util.Calendar;


public class EcouteActivity extends Activity {

    private static final String ACT2 = "EcouteActivity";
    private static final String APP_NAME = "Veille";

/*
Modification à prévoir: insérer l'heure de début dans la base de données + le nombre de fois où l'enfant s'est réveillé.
 */

    public static final int MY_PERMISSIONS_REQUEST_AUDIO_RECORD = 42;

    String monstreSexy=null; // Car en String = clé pour les sharedpreference

    // Cr�ation d'un thread dans laquelle l'�coute du b�b� sera lanc�e
    Thread background ;

    //Creation d'un objet SharedPreference pour récupérer le seuil de décibels et le son à lire
    SharedPreferences prefs = null ;
    String seuil;
    String sonNom;
    
    //Cr�ation d'un objet Ecoute dont on utilisera les m�thodes pour mesurer le niveau sonore ambiant
    private Ecoute ecoute;
    private Lecture lecture;

    // Variable qui r�cup�re le niveau sonore en d�cibels
    private double resultEcoute, decibels; // le resultcoute arrive en amplitude et se sera converti en décibels dans decibels

    //Seuil d'alerte en d�cibels (MAJ il s'agit d'une amplitude finalement pour plus de sensibilité).
    private double seuilDecibels  ;

    // Cr�ation d'un bol�en pour d�terminer si la thread est lanc�e ou non
    boolean isThreadRunning=false;

    //Creation de deux boolean pour determiner quelle séquence du thread est activée
    boolean ecouteActive = true ;
    boolean lectureActive =false;

    //Création d'un boléen pour savoir si la bdd est active
    boolean isBddRunning=false;


    // Cr�er un ContentResolver sans l'instancier pour d�tecter le mode avion
    private ContentResolver contentResolver ;




    //Creation d'un object calendar pour le calendrier
   private Calendar cal;
    private long dateDebut;
    private long difference;



    //creation d'un int pour enregistrer le nb de fois où l'enfant s'est réveillé
    int xt;

    //
    private  SensorManager sensorManager;

    int lum ;
    long date ;
    int monstre;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force l'activité à rester visible. L'ecran ne s'eteind pas
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        // FOrce l'activité à etre en plein ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ecoute);

        //On va demander la permission d'acceder au micro
        int permissionCheckAudio = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);


        if (permissionCheckAudio != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

                //Lancer un alertDialog ici
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_AUDIO_RECORD);


            }
        }


        
        
        
        //Récuperation du seuilDécibel dans le sharedPreference (transformation du string en double)
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        seuil= prefs.getString("sensibilite_micro", "400");
        seuilDecibels =Double.parseDouble(seuil);
        
        //Récupération du nom du son à lire lorsque la veille est déclenchée
        sonNom=prefs.getString("nomDuSon","MessagePourBebe.mp4");
        
        ecoute=new Ecoute();
        lecture=new Lecture(this);

        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(1000); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        fab= (FloatingActionButton) findViewById(R.id.boutonFlottant);
        fab.startAnimation(animation);



    }


    @Override
    protected void onResume()
    {
        Log.i(ACT2, "activite 2 resum�e");

        if (!isBddRunning)
        {isBddRunning=true;
            Log.i(ACT2, "Boolean de Base de donnees activee mis sur true dans le onResume");
        }

//Séquence d'activation du thread d'écoute

        background = new Thread (new Runnable() {


            public void run() {
                Log.i(ACT2, "debut de l'execution du thread");

                while (isThreadRunning) {

                    if (!ecoute.isRunning())                   // Si le mediarecorder n'est pas encore actif,
                    {
                        try {

                            ecoute.demarrerEcoute();                            //alors il sera lanc�
                            Log.i(ACT2, "ecoute démarrée");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //On determine ici l'heure de début de l'écoute
                        cal=Calendar.getInstance();
                        dateDebut = cal.getTimeInMillis();

                        //Et on instancie ici le compteur d'éveil
                        xt=0;
                    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                    if (ecouteActive) {
                        resultEcoute = ecoute.obtenirDecibels();  //on r�cup�re le niveau sonore en d�cibels
                        Log.i(ACT2, "test des décibels1");
                        if (resultEcoute > seuilDecibels)                 // Si le r�sultat de l'�coute est sup�rieur au seuil fix� en d�cibels, alors on enverra un message � l'UI thread
                        {
                            Log.i(ACT2, "le seuil des décibels est dépassé (essai 1");
                            try {
                                background.sleep(300);//lon fait une pause pour s'assurer qu'il ne s'agit pas seulement d'un bruit bref
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            resultEcoute = ecoute.obtenirDecibels();

                            Log.i(ACT2, "test des décibels1");
                            if (resultEcoute > seuilDecibels) { // recuperation des decibels
                                Log.i(ACT2, "le seuil des décibels est dépassé (essai 2");
                                // On fait en sorte de ne pas relancer la phase d'écoute tout de suite
                                ecouteActive = false;

                                Log.i(ACT2, "captation des paramettres de l'évènement");
                                decibels = 20 * Math.log10(resultEcoute / 10);

                                Log.i(ACT2, "instantiation du cal");
                                // Obtenir un objet calendar
                                cal = Calendar.getInstance();
                                int heure = cal.get(Calendar.HOUR_OF_DAY); // On isole l'heure pour déterminer quel monstre est apparu

                                // Pour la date au Handler on va transformer l'objet Calendar en long (ou directement ins�rer cet objet long dans la base de donn�es quand celle-ci aura �t� cr�e)
                                long timeInMillis = cal.getTimeInMillis();

                                //On calcule la différence entre l'heure de l'evenement et l'heure du debut
                                difference=timeInMillis-dateDebut;

                                //on ajoute 1 au compteur d'évènements et on met dans un shared preference le fait que le bebe ait été réveillé une ou plusieurs fois
                                xt++;

                               if (xt==1) prefs.edit().putBoolean("unReveil",true).apply();
                                else if (xt>1)
                               {
                                   prefs.edit().putBoolean("unReveil",false).apply();
                                   prefs.edit().putBoolean("plusieursReveil",true).apply();
                               }


                                // Et maintenant pour la lumiere :
                                sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                                Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                                if (lightSensor != null) {

                                    sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                                }


                                // Cr�er une nouvelle entr�e dans la base de donn�es avec timeInMillis, resultEcoute et lum.
                                monstre = Monstre.quelMonstre(lum, heure, decibels, difference, xt);

                                // Débloquer le monstre dans la sharedpreference
                                monstreSexy= Monstre.quelMonstreString(monstre);
                                prefs.edit().putBoolean(monstreSexy,true).apply();



                                //Insertion des données dans la base de données
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(Contract.Evenements.COLUMN_COL2, decibels);
                                contentValues.put(Contract.Evenements.COLUMN_COL3, timeInMillis);
                                contentValues.put(Contract.Evenements.COLUMN_COL4, lum);
                                contentValues.put(Contract.Evenements.COLUMN_COL5, monstre);
                                Log.i(ACT2, "nouvelle entree cree dans la base de données");

                                // Insert the content values via a ContentResolver
                                Uri uri = getContentResolver().insert(Contract.Evenements.URI, contentValues);



                                // Cr�er une notification avec le monstre qui a r�veill� le b�b�.
                                Log.i(ACT2, "Lecture a partir de la sequence ecoute");


                                if (lecture.isRunning())
                                {   Log.i(ACT2, "Lecture du son résumée");

                                    lecture.resume();
                                } else {
                                    Log.i(ACT2, "Lecture du son pour la premiere fois");
                                    lecture.lire(sonNom);

                                }


                                lecture.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    public void onCompletion(MediaPlayer mp) {
                                        Log.i(ACT2, "declenchement du listener");

                                       Log.i(ACT2, "Lecture du son terminée");
                                        lectureActive = true;

                                    }

                                });

                                // On cree un listener pour surveiller la fin du morceau


                                Log.i(ACT2, "fin de la sequence ecoute");
                            }
                        }
                }


                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                if (lectureActive) {
                    // quand le son est terminé,  on ecoute le nombre de d�cibels ambiants

                    //On fait en sorte de ne pas relancer la phase de lecture avant le moment voulu

                    Log.i(ACT2, "SOn terminé, on réécoute pour voir si bébé pleure toujours");


                    lectureActive = false;

                    // On fait une petite pause sinon l'echo du son lu déclenche à nouveau le capteur
                    try {
                        background.sleep(1000);//le thread background sera relanc� toutes les 300 millisecondes tant que la valeur seuil n'aura pas �t� d�pass�e.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.i(ACT2, "Test des decibels 3");
                    resultEcoute = ecoute.obtenirDecibels();

                    // Si le nombre de d�cibels est inf�rieur au seuil (si le b�b� ne crie plus
                    // la lecture du son ne sera pas relanc�e
                    // Sinon la lecture est relancée

                    if (resultEcoute > seuilDecibels) {
                        Log.i(ACT2, "Le seuil est toujours dépassé 3, la lecture du son est relancée");
                        lecture.resume();

                    } else {
                        Log.i(ACT2, "Il n'y plus de bruit, on ne relance pas la lecture et on relance l'écoute");
                        //le thread d'écoute principal est relancé
                        lectureActive=false;
                        ecouteActive = true;
                        
                    }


                }


                if (isThreadRunning) {
                    Log.i(ACT2, "relance du thread a venir");
                    try {
                        background.sleep(300);//le thread background sera relanc� toutes les 300 millisecondes tant que la valeur seuil n'aura pas �t� d�pass�e.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            } // fin de la parenthèse du if (isThreadRunning)


        } // fin de la parenthèse du run



        }); // fin de la parenthèse du thread






        if (!isThreadRunning)
        { isThreadRunning=true ;
            Log.i(ACT2, " boolean mis sur true");
            background.start();}



        super.onResume();

    }

    @Override
    protected void onPause()
    {
        // Arret du thread d'écoute

        if (isThreadRunning)
        {

            isThreadRunning = false;
            if (ecoute.isRunning()) ecoute.arreterEcoute();
            if (lecture.isRunning()) lecture.stop();
        }

        isBddRunning = false;
        Log.i(ACT2, "Boolean de Base de donnees activee mis sur false dans le onPause");
       super.onPause();
        Log.i(ACT2, "onPause terminé");

        finish(); // force l'activity à se detruire pour que l home ne puisse pas y réaccéder
    }




    private final SensorEventListener lightSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                lum = (int)event.values[0];
            }
        }

    };


    // M�thode permettant de retourner � l'activit� principale
    public void retourActivitePrincipale() {
        Intent i = new Intent(EcouteActivity.this, MainActivity.class);
        startActivity(i);
    }

    // Lorsque l'utilisateur appuie sur le bouton, cela stope l'�coute et retourne � l'activit� principale
    public void onClick2 (View v)
    {


        if (isThreadRunning)
        {isThreadRunning=false;

            if (ecoute.isRunning()) ecoute.arreterEcoute();
            if (lecture.isRunning()) lecture.stop();
        }
        retourActivitePrincipale();
    }

    //  Fonction qui détermine quel est le monstre qui est apparu
    //  Pour le moment on considère que l'heure est un entier etc. On ne cree pas d'objet date
    //  On cree un entier qui servira a identifier le monstre




    public boolean estEnModeAvion ()
    {

        contentResolver = this.getContentResolver();
        if (Settings.System.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) == 0)
        {return false;}

        else return true;

    }





    ///////////////////////////////////////////////////////////////////


 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
