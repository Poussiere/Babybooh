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
import android.os.Handler;
import android.os.Message;
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
import android.widget.Button;
import android.widget.TextView;


import com.poussiere.babybooh.MainActivity;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.bdd.Contract;
import com.poussiere.babybooh.objets.Ecoute;
import com.poussiere.babybooh.objets.Lecture;
import com.poussiere.babybooh.objets.Monstre;

import java.io.IOException;
import java.util.Calendar;


public class EcouteActivity extends Activity {

    public static final String ACT2 = "EcouteActivity";
    public static final int EVENEMENT_INTERROMPU = 1; // l'utilisateur a arrêté la veille après le reveil de bébé
    public static final int EVENEMENT_TERMINE = 0; // Bebe s'est rendormi après l'évenement

/*
Modification à prévoir: insérer l'heure de début dans la base de données + le nombre de fois où l'enfant s'est réveillé.
 */

/*
Tant qu'il n'y a pas eu au moins 5 min (ou 3 minutes) de silence, on est toujours sur le même évenement. On reste donc dans la boucle lecture active et on enregistre l'ensemble des variables en sortant de cette boucle.
Il faudra ajouter dans la base de données des colonnes pour le temps pendant lequel le bébé a été réveillé, + pour le décibel le plus élevé + moyenne des décibels sur la période, ect.
Préciser si le bébé s'est rendormi ou si la veille a été interrompue par quelqu'un.
Faire des textview en conséquence dans la détail acitivity.
Il va falloir lancer un thread dans le onPause pour enregistrer la veille si jamais la veille est interrompue pendant la lectureActive.
 + Proposer au user de selectionner le laps de temps entre 2 répétions du message qu'il souhaite (Dès que le bébé se réveille, 5s, 10s, 15s, 30s, 1 minute, 2 minute, 3 minute

 */
    public static final int MY_PERMISSIONS_REQUEST_AUDIO_RECORD = 42;

    private TextView introText;
    private long dateDebutReveil;
    private boolean awakeBaby = false;
    
    String monstreSexy=null; // Car en String = clé pour les sharedpreference

    // Cr�ation d'un thread dans laquelle l'�coute du b�b� sera lanc�e
    private Thread background, backgroundSave ;

    //Creation d'un objet SharedPreference pour récupérer le seuil de décibels et le son à lire
    SharedPreferences prefs = null ;
    String seuil;
    String sonNom;
    
    //Cr�ation d'un objet Ecoute dont on utilisera les m�thodes pour mesurer le niveau sonore ambiant
    private Ecoute ecoute;
    private Lecture lecture;

    private int heure; //heure du début de l'évenement
    private long timeInMillis; //date de l'évenement (heure comprise = timestamp)
    private long duree; //duree de l'évenement

    // Variable qui r�cup�re le niveau sonore en d�cibels du cri initial qui a déclenché le détecteur
    private double resultEcoute, decibels, amplitudeRef; // le resultcoute arrive en amplitude et se sera converti en décibels dans decibels

    //Variable permettant de stocker la décibels la plus haute de l'évenement
    private double highestDecibel, decibelTemp ;
    
    // Variables qui determinent l'heure à laquelle le son s'est déclenché la dernière foiset qui stocke temporairement l'heure du lancement du son précédent
    long heureDernierDeclenchement;


    //Seuil d'alerte en d�cibels
    private double seuilDecibels ;
    private double sensibilite ;
    
    //Délais de réveil au bout duquel le message peut-être déclenché. Par défaut c'est immédiatement
    private long delaisDeclenchement ; 
    

    // Cr�ation d'un bol�en pour d�terminer si la thread est lanc�e ou non
    boolean isThreadRunning=false ;

    //Creation de deux boolean pour determiner quelle séquence du thread est activée
    boolean ecouteActive = true ;
    boolean lectureActive =false ;


    // Cr�er un ContentResolver sans l'instancier pour d�tecter le mode avion
    private ContentResolver contentResolver ;


    //Creation d'un object calendar pour le calendrier
    private Calendar cal;
    private long dateDebut;
    private long difference;



    //creation d'un int pour enregistrer le nb de fois où l'enfant s'est réveillé
   private int xt = 0;

    //
    private  SensorManager sensorManager;

    private int lum ;
    private int monstre;
    private Button fab;
    private Handler handler;
    private View conteneur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force l'activité à rester visible. L'ecran ne s'eteind pas
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        // FOrce l'activité à etre en plein ecran
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_ecoute);

        conteneur= (View)findViewById(R.id.conteneur_ecoute);
        conteneur.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);






        introText=(TextView)findViewById(R.id.text_ecoute);
        //On va demander la permission d'acceder au micro

        //Récuperation du seuilDécibel dans le sharedPreference (transformation du string en double)
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        seuil= prefs.getString("sensibilite_micro", "10");

        sensibilite = Double.parseDouble(seuil); //Il faudra ajouter ce chiffre aux décibels obtenus pour augmenter la sensibilité du micro

        seuilDecibels = 35 ; //Le détecteur se déclenchera à 35 décibels


        String ar = prefs.getString("amplitudeRef", "0.7");
        amplitudeRef = Double.parseDouble(ar);

        delaisDeclenchement=Long.parseLong(prefs.getString("delais_declenchement", "0"));

        //Récupération du nom du son à lire lorsque la veille est déclenchée
        sonNom=prefs.getString("nomDuSon","Music box 1.3gpp");

        ecoute=new Ecoute();
        lecture=new Lecture(this);

        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(2000); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        fab= (Button) findViewById(R.id.boutonFlottant);
        fab.startAnimation(animation);


        handler=new Handler(){

            public void handleMessage(Message msg){
                super.handleMessage(msg);
            int display = msg.what;

                switch (display){



                    case 1 :
                        introText.setText(R.string.ecoute_active);
                        break;
                    case 2 :
                        introText.setText(R.string.sortir);
                        break;
                    case 3 :
                        introText.setText("");
                        break;
                }
            }

        };

        //Thread d'écoute qui sera lancé dans le onResume :
        background = new Thread (new Runnable() {


            public void run() {
                Log.i(ACT2, "debut de l'execution du thread");

                while (isThreadRunning) {

                //On determine ici l'heure de début de l'écoute
                    if (xt==0){
                        cal=Calendar.getInstance();
                        dateDebut = cal.getTimeInMillis();
                    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                    if (ecouteActive) {




                        if (!ecoute.isRunning())                   // Si le mediarecorder n'est pas encore actif,
                        {
                            //Phase d'introduction

                            try {

                                ecoute.demarrerEcoute();//alors il sera lanc�


                                Log.i(ACT2, "ecoute démarrée");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            handler.sendEmptyMessage(1);
                            handler.sendEmptyMessageDelayed(2, 2000);
                            handler.sendEmptyMessageDelayed(3, 5000); // Effacer le textView


                            resultEcoute = ecoute.obtenirDecibels(amplitudeRef)+sensibilite ;  //on r�cup�re le niveau sonore en d�cibels beta
                            Log.i(ACT2, "test des décibels beta");
                            Log.i(ACT2, "resultEcoute en dB : "+resultEcoute+" ");


                            try {
                                background.sleep(5000);//On laisse le temps de poser le téléphone
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                        resultEcoute = ecoute.obtenirDecibels(amplitudeRef)+sensibilite;  //on r�cup�re le niveau sonore en d�cibels
                        Log.i(ACT2, "test des décibels1");

                        Log.i(ACT2, "resultEcoute en dB : "+resultEcoute+" ");


                        if (resultEcoute > seuilDecibels)

                        {
                            Log.i(ACT2, "le seuil des décibels est dépassé (essai 1");
                            try {
                                background.sleep(300);//on fait une pause pour s'assurer qu'il ne s'agit pas seulement d'un bruit bref
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            resultEcoute = ecoute.obtenirDecibels(amplitudeRef)+sensibilite;

                            Log.i(ACT2, "test des décibels2");
                            if (resultEcoute > seuilDecibels) {
                                Log.i(ACT2, "le seuil des décibels est dépassé (essai 2");
                                Log.i(ACT2, "resultEcoute en dB : "+resultEcoute+" ");
                                
                                awakeBaby=true;
                                ecouteActive = false;

                                if (xt==0) prefs.edit().putBoolean("unReveil",true).apply();
                                else if (xt>0)
                                {
                                    prefs.edit().putBoolean("unReveil",false).apply();
                                    prefs.edit().putBoolean("plusieursReveil",true).apply();
                                }
                                xt=xt+1;

                                cal=Calendar.getInstance();
                                dateDebutReveil=cal.getTimeInMillis();
                                heure = cal.get(Calendar.HOUR_OF_DAY); // On isole l'heure pour déterminer quel monstre est apparu

                                // Pour la date au Handler on va transformer l'objet Calendar en long (ou directement ins�rer cet objet long dans la base de donn�es quand celle-ci aura �t� cr�e)
                                timeInMillis = cal.getTimeInMillis();

                                //On calcule la différence entre l'heure de l'evenement et l'heure du debut
                                difference=timeInMillis-dateDebut;
                                //on ajoute 1 au compteur d'évènements et on met dans un shared preference le fait que le bebe ait été réveillé une ou plusieurs fois


                                // Onrécupère le niveau en décibels de cet évenement déclencheur
                                Log.i(ACT2, "captation des paramettres de l'évènement");
                              //  decibels = 20 * Math.log10(resultEcoute / 10);

                                decibels=resultEcoute;
                                //On donne sa première valeur au highestDecibels
                                highestDecibel = decibels;

                                // On observe la lumière ambiante au réveil
                                sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                                Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                                if (lightSensor != null) {

                                    sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                                   

                                    
                                }

                                Log.i(ACT2, "Lecture a partir de la sequence ecoute");
                               cal=Calendar.getInstance();
                                heureDernierDeclenchement =cal.getTimeInMillis();
                                //Si le temps écoulé depuis le premier cri du bébé est supérieur au délais défini par l'utilisateur, on lance la lecture du son
                                if ((heureDernierDeclenchement -timeInMillis)>=delaisDeclenchement){

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
                                }// fin de la condtion si le temps écoulé depuis le premier cri du bébé est supérieur au délais défini par l'utilisateur...
                            
                                //si le temps écoulé est inférieur au délais choisi par l'utilisateur, on entre dans la phase de lectureActive, mais sans déclencher le son pour le moment
                                else{
                                    lectureActive = true;}


                                Log.i(ACT2, "fin de la sequence ecoute");
                            }
                        }
                    }


                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                    if (lectureActive) {
                        // quand le son est terminé,  on ecoute le nombre de d�cibels ambiants

                        //On fait en sorte de ne pas relancer la phase de lecture avant le moment voulu


                        Log.i(ACT2, "Son terminé, on réécoute pour voir si bébé pleure toujours");


                        Log.i(ACT2, "Test des decibels 3");
                        resultEcoute = ecoute.obtenirDecibels(amplitudeRef)+sensibilite;
                        Log.i(ACT2, resultEcoute + " réécoute");

                        // Si le nombre de d�cibels est inf�rieur au seuil (si le b�b� ne crie plus
                        // la lecture du son ne sera pas relanc�e
                        // Sinon la lecture est relancée
                        cal=Calendar.getInstance();
                        if (resultEcoute > seuilDecibels) {

                            // On fait une petite pause sinon l'echo du son lu déclenche à nouveau le capteur
                            try {
                                background.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            resultEcoute = ecoute.obtenirDecibels(amplitudeRef)+sensibilite;

                          

                                if (resultEcoute > seuilDecibels) // Double vérification pour voir si le bruit est persistant
                                {
                                    lectureActive = false;

                                    cal=Calendar.getInstance();
                                      heureDernierDeclenchement = cal.getTimeInMillis();

                                    // detetection pour voir si décibels plus importantes que précédemment
                                   // decibelTemp = 20 * Math.log10(resultEcoute / 10);
                                    decibelTemp=resultEcoute;
                                    if (decibelTemp > highestDecibel) {
                                        highestDecibel = decibelTemp;
                                    }

                                    Log.i(ACT2, "Le seuil est toujours dépassé 3, la lecture du son est relancée");
                                    Log.i(ACT2, resultEcoute + " réécoute");


                                  
                                       // lecture.resume();
                                         
                                //Si le temps écoulé depuis le premier cri du bébé est supérieur au délais défini par l'utilisateur, on lance la lecture du son
                                if ((heureDernierDeclenchement -timeInMillis)>=delaisDeclenchement){

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
                                }
                                else{
                                    lectureActive=true;
                                }
                                    }




                        } else if ((resultEcoute <= seuilDecibels) && ((cal.getTimeInMillis() - heureDernierDeclenchement) > 180000)) {



                            Log.i(ACT2, "Il n'y a plus de bruit depuis 3 minutes, on ne relance pas la lecture et on relance l'écoute");
                            //L'évenenement réveil est terminé, le thread d'écoute principal est relancé
                            //C'est ici qu'on va insérer les nouvelles variables dans la base de données : Durée du réveil
                            // Cri le plus fort, Evenement interrompu ou non, etc... Il faudra en faire de même dans le onPause en lancant un nouveau thread.
                            Log.i(ACT2, "captation des paramettres de l'évènement");
                            awakeBaby=false;
                            //On récupère en decibels le niveau sonore de ce dernier cri déclencheur et on check pour voir si c'est le cri le plus puissant
                          //  decibelTemp = 20 * Math.log10(resultEcoute / 10);
                            decibelTemp=resultEcoute;
                            if (decibelTemp > highestDecibel) {
                                highestDecibel = decibelTemp;
                            }



                            //Calcul de la durée totale de l'évenement
                            duree= heureDernierDeclenchement -dateDebutReveil;
                            Log.i(ACT2, "duree en millis ="+duree);

                            // Cr�er une nouvelle entr�e dans la base de donn�es avec timeInMillis, resultEcoute et lum.

                            //savoir quel monstre a réveillé bébé
                            monstre = Monstre.quelMonstre(lum, heure, highestDecibel, difference, xt);

                            // Débloquer le monstre dans la sharedpreference
                            monstreSexy = Monstre.quelMonstreString(monstre);
                            prefs.edit().putBoolean(monstreSexy, true).apply();


                            //Insertion des données dans la base de données
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(Contract.Evenements.COLUMN_COL2, decibels);
                            contentValues.put(Contract.Evenements.COLUMN_COL3, dateDebutReveil);
                            contentValues.put(Contract.Evenements.COLUMN_COL4, lum);
                            contentValues.put(Contract.Evenements.COLUMN_COL5, monstre);
                            contentValues.put(Contract.Evenements.COLUMN_COL6, highestDecibel);
                            contentValues.put(Contract.Evenements.COLUMN_COL7, EVENEMENT_TERMINE);
                            contentValues.put(Contract.Evenements.COMUMN_COL8, duree );

                            Log.i(ACT2, "nouvelle entree cree dans la base de données");

                            // Insert the content values via a ContentResolver
                            Uri uri = getContentResolver().insert(Contract.Evenements.URI, contentValues);

                            lectureActive = false ;
                            ecouteActive = true ;


                        }
                        else {
                            lectureActive=true; // pour relancer la phase de lectureactive
                        }



                    }// fin du iflectureactive

                        if (isThreadRunning) {
                            Log.i(ACT2, "relance du thread a venir");
                            try {
                                background.sleep(300);//le thread background sera relanc� toutes les 300 millisecondes tant que la valeur seuil n'aura pas �t� d�pass�e ou que le silence n'aura pas duré plus de 3 minutes
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }



                }// fin de la parenthèse du if (isThreadRunning)

            } // fin de la parenthèse du run




        }); // fin de la parenthèse du thread

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Thread d'enregistrement de l'évenement dans la bdd lancé dans le onPause lorsque l'utilisateur quitte la veille pendant un évenement


        backgroundSave = new Thread (new Runnable() {


            public void run() {

                //mettre ça dans un new thread



                    //Calcul de la durée totale de l'évenement
                    cal=Calendar.getInstance();
                    duree = cal.getTimeInMillis() - dateDebutReveil;
                 Log.i(ACT2, "heure actuelle : "+cal.getTimeInMillis());
                 Log.i(ACT2, "timeInMillis : "+timeInMillis);
                 Log.i(ACT2, "duree : "+duree);

                    //savoir quel monstre a réveillé bébé
                    monstre = Monstre.quelMonstre(lum, heure, highestDecibel, difference, xt);


                    //Insertion des données dans la base de données
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Contract.Evenements.COLUMN_COL2, decibels);
                    contentValues.put(Contract.Evenements.COLUMN_COL3, dateDebutReveil);
                    contentValues.put(Contract.Evenements.COLUMN_COL4, lum);
                    contentValues.put(Contract.Evenements.COLUMN_COL5, monstre);
                    contentValues.put(Contract.Evenements.COLUMN_COL6, highestDecibel);
                    contentValues.put(Contract.Evenements.COLUMN_COL7, EVENEMENT_INTERROMPU);
                    contentValues.put(Contract.Evenements.COMUMN_COL8, duree);

                    Log.i(ACT2, "nouvelle entree cree dans la base de données");


                    // Insert the content values via a ContentResolver
                    Uri uri = getContentResolver().insert(Contract.Evenements.URI, contentValues);

                    // Débloquer le monstre dans la sharedpreference
                    monstreSexy = Monstre.quelMonstreString(monstre);
                    prefs.edit().putBoolean(monstreSexy, true).apply();

                //////////////////////////////////////////////////




            } // fin de la parenthèse du run




        }); // fin de la parenthèse du thread
    }


    @Override
    protected void onResume()
    {
        Log.i(ACT2, "activite 2 resum�e");


//Séquence d'activation du thread d'écoute




        if (!isThreadRunning)
        { isThreadRunning=true ;
            Log.i(ACT2, " boolean mis sur true");
            background.start();}



        super.onResume();

        }


        @Override
            protected void onPause()
        {
              //  Toast.makeText(this, R.string.quitte_veille_avion, Toast.LENGTH_LONG).show();
                
            if (awakeBaby) {
                awakeBaby=false;
                backgroundSave.start();
             
            }



        // Arret du thread d'écoute

        if (isThreadRunning)
        {

            isThreadRunning = false;
            if (ecoute.isRunning()) ecoute.arreterEcoute();
            if (lecture.isRunning()) lecture.stop();
            lectureActive=false;
            ecouteActive=true;
        }

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
