package com.poussiere.babybooh.objets;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class Ecoute {


    private MediaRecorder mediaRecorder = null ;
    public static final String OBJ_NAME="objet ecoute";

    // Méthode qui va lancer l'écoute des cris du bébé :
    public void demarrerEcoute () throws IOException {

        if (mediaRecorder == null)
    {
        mediaRecorder = new MediaRecorder();

        // Définit la source audio utilisée : le micro du téléphone
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        //Définit le format audio d'enregistrement : le .3gpp :
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        //Définit l'encodeur audio : AMR_NB car dédié à l'encodage de la parole et donc potentiellement adapté à un cri de bébé
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        // Définit le répertoire où le son capturé est enregistré. En l'occurence on souhaite juste annalyser le niveau sonore d'un flux sans l'enregistrer. Le répertoire /dev/null permet cela.
        mediaRecorder.setOutputFile("/dev/null");

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

    // Méthode qui va arrêter l'écoute du bébé :

    public void arreterEcoute()
    {

        if (mediaRecorder != null)
        {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder=null;
            Log.i(OBJ_NAME, "MediaRecorder arreté");
        }

    }

    // Méthode qui permettre d'obtenir le niveau sonore du flux capturé en décibelles

    public double obtenirDecibels()
    {
        if (mediaRecorder != null)

        {//On récupère l'amplitude (niveau sonore) du flux capté
            double amplitude = mediaRecorder.getMaxAmplitude();

            //On convertit l'amplitude en décibels grace à la formule
            //double db = 20 * Math.log10(amplitude/ 2700);
            return amplitude;

        }

        else
        {Log.i(OBJ_NAME, "fin de obtenir decibel sans retour");
            return 0;}

    }

    //Méthode qui va permettre de sa voir si le MediaRecorder est actif ou non
    public boolean isRunning()
    {if (mediaRecorder==null)
        return false;
    else return true;}



}