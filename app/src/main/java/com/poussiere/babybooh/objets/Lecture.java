package com.poussiere.babybooh.objets;

import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Nicolas on 20/06/2015.
 */
public class Lecture {

    // Creation d'un objet MEdiaPlayer pour lire le son enregistr� Le rendre privé et mettre un ascesseur
   private MediaPlayer mediaPlayer ;
    private String son;


    // M�thode permettant de lancer la lecture du son enregistr�
    public void lire(String sonNom)

    {if (mediaPlayer==null)

    {son= Environment.getExternalStorageDirectory().getAbsolutePath() ;
        son += "/babyboohSongs/"+sonNom ;

        mediaPlayer = new MediaPlayer();

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(son);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();}}


    //Methode permettant d'accéder au mediaplayer depuis l'exterieur
    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    // M�thode permettant de mettre le son en pause
    public void pause()
    {
        if (mediaPlayer!=null)
        {mediaPlayer.pause();}
    }


    // Méthode permettant de remettre la lecture à 0
    public void reset() {
        if (mediaPlayer !=null)
        {mediaPlayer.reset();}
    }

    // M�thode permettant de relancer la lecture (A VERIFIER)
    public void resume(){
        if (mediaPlayer != null)
        {mediaPlayer.start();}

    }

    //M�thode permettant de stopper d�finitivement la lecture
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.i("ACT2", "mediaplayer stoppé");
        }}

    //M�thode qui va permettre de sa voir si le MediaRecorder est actif ou non
    public boolean isRunning()
    {if (mediaPlayer==null)
        return false;
    else return true;}


}


