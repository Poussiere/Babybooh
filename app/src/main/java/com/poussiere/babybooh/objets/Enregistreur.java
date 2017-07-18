package com.poussiere.babybooh.objets;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

import static java.security.AccessController.getContext;

// Créer le repertoire dans lequel seront sauvegardées les sons lors du premier lancement

public class Enregistreur {

    private MediaRecorder mediaRecorder = null;
    public static final String OBJ_NAME = "objet enregistreur";
    //Fichier unique où est enregistré le message du parent pour le bébé

    private String fichierVoix;
    private MediaPlayer mediaPlayer; //idem que pour Lecture, le rendre privé et ascesseur
    private Context context;


    //Methode permettant d'accéder au mediaplayer depuis l'exterieur
    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    public Enregistreur (Context c){
        context = c;
    }


    // Méthode qui va lancer l'enregistrement du message pour le bébé
    public void demarrerEnregistrement() throws IOException {

        fichierVoix = context.getExternalFilesDir(null).getAbsolutePath() ;
      //  fichierVoix = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
        fichierVoix += "/babyboohSongs/MessagePourBebe.3gpp";

        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();

            mediaRecorder.reset();

            // Définit la source audio utilisée : le micro du téléphone
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            //Définit le format audio d'enregistrement : le .3gpp :
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            //Définit l'encodeur audio : AMR_NB car dédié à l'encodage de la parole
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // Définit le répertoire où le son capturé est enregistré.
            mediaRecorder.setOutputFile(fichierVoix);
            
            //définit la durée maximale de l'enregistrement (3 minutes)
            mediaRecorder.setMaxDuration(180000);

            try {
                mediaRecorder.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaRecorder.start();
            Log.i(OBJ_NAME, "MediaRecorder lancé");


        }
    }

// Méthode qui va arrêter l'enregistrement :

    public void arreterEnregistrement() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            Log.i(OBJ_NAME, "MediaRecorder arreté");
        }

    }
    
    // Méthode qui va lire le fichier enregistré

    public void lireSon ()
    {
    if (fichierVoix!=null)
        {mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(fichierVoix);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();}
    }
    
    // Méthode pour stopper la lecture et libérer la mémoire
    public void arreterLecture() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }


    }}
