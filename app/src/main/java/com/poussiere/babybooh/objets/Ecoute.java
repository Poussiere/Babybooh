package com.poussiere.babybooh.objets;

import android.content.Context;
import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

public class Ecoute {


    private Context mContext;
    private MediaRecorder mediaRecorder = null;

    public Ecoute(Context context) {
        mContext = context;
    }

    // Méthode qui va lancer l'écoute des cris du bébé :
    public void demarrerEcoute() throws IOException {

        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();

            // Définit la source audio utilisée : le micro du téléphone
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            //Définit le format audio d'enregistrement : le .3gpp :
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            //Définit l'encodeur audio : AMR_NB car dédié à l'encodage de la parole et donc potentiellement adapté à un cri de bébé
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            File tempFile = new File(mContext.getExternalCacheDir().getAbsolutePath() + "cachefile.3gp");

            tempFile.createNewFile();

            // Définit le répertoire où le son capturé est enregistré. En l'occurence on souhaite juste analyser le niveau sonore d'un flux sans l'enregistrer. Le répertoire /dev/null permet cela.
            mediaRecorder.setOutputFile(tempFile.getAbsolutePath());

            mediaRecorder.prepare();
            mediaRecorder.start();
        }
    }

    // Méthode qui va arrêter l'écoute du bébé :

    public void arreterEcoute() {

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }

    }

    // Méthode qui permettre d'obtenir le niveau sonore du flux capturé en décibelles

    public double obtenirDecibels(double amplitudeReference) {
        if (mediaRecorder != null) {//On récupère l'amplitude (niveau sonore) du flux capté
            double amplitude = mediaRecorder.getMaxAmplitude();

            //On convertit l'amplitude en décibels grace à la formule
            double db = 20 * Math.log10(amplitude / amplitudeReference);

            return db;

        } else {
            return 0;
        }

    }

    //Méthode qui retourne l'amplitude max
    public double obtenirAmplitude() {
        if (mediaRecorder != null) {//On récupère l'amplitude (niveau sonore) du flux capté
            return mediaRecorder.getMaxAmplitude();
        } else {
            return 0;
        }

    }


    //Méthode qui va permettre de savoir si le MediaRecorder est actif ou non
    public boolean isRunning() {
        return mediaRecorder != null;
    }


}