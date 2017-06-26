package com.poussiere.babybooh.annexes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poussiere.babybooh.R;
import com.poussiere.babybooh.objets.Enregistreur;

import java.io.File;
import java.io.IOException;

public class EnregistrerActivity2 extends AppCompatActivity {

  private  Enregistreur enregistreur ;
   private boolean enregistrementEnCours ;
   private Thread background1, background2 ;
    private TextView tv1;
   private FloatingActionButton fb;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer2);

        // couleur de la barre de statuts pour Lolipo et +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color_dark));
        }


        tv1=(TextView)findViewById(R.id.tv_lancer_enregistrement);
        fb=(FloatingActionButton) findViewById(R.id.boutonFlottantEnregistrer2);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar_enr2);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.salle_enregistrement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        enregistrementEnCours=false;
        enregistreur = new Enregistreur(this);

    }

    public void onClick (View v) {
        if (!enregistrementEnCours) {

            int permissionCheckAudio = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO);
            if (permissionCheckAudio == PackageManager.PERMISSION_GRANTED) {


                enregistrementEnCours = true;

                final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                animation.setDuration(1000); // duration - half a second
                animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
                animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
                animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

                fb.startAnimation(animation);
                tv1.setText(R.string.stop_enregistrement);
                fb.setContentDescription(getString(R.string.content_description_arreter_enregistreent));
                background1 = new Thread(new Runnable() {

                    public void run() {



                        try {
                            enregistreur.demarrerEnregistrement();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


                //bouton3.setText(R.string.stopRecord);
                background1.start();
            } else {

                }
         /*   if (permissionChecWrite==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE);
            } */



        } else {

            enregistreur.arreterEnregistrement();
            enregistrementEnCours = false;
            fb.clearAnimation();
            fb.setContentDescription(getString(R.string.content_description_demarrer_enregistreent));
            tv1.setText(R.string.lancer_enregistrement);


            //////////////////////////////////////////////////////////////////////////////////////////////////
            //Construction d'une alertedialog pour demander de nommer le fichier son

            //Construction de l'EditText pour le nom
            final EditText sonNom = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);


            InputFilter[] FilterArray = new InputFilter[1]; // Filtre pour limiter la taille du texte saisi
            FilterArray[0] = new InputFilter.LengthFilter(10);
            sonNom.setFilters(FilterArray);

            InputFilter[] filters = new InputFilter[1];


            //Liste des charactères autorisés pour l'edittext
            filters[0] = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (end > start) {

                        char[] acceptedChars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' '};

                        for (int index = start; index < end; index++) {
                            if (!new String(acceptedChars).contains(String.valueOf(source.charAt(index)))) {
                                return "";
                            }
                        }
                    }
                    return null;
                }

            };
            sonNom.setFilters(filters);

            sonNom.setLayoutParams(lp);

            final AlertDialog.Builder alerteNom = new AlertDialog.Builder(EnregistrerActivity2.this);

            alerteNom.setMessage(R.string.alerte5);
            alerteNom.setView(sonNom);
            alerteNom.setPositiveButton(R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //Ajouter une condition pour etre sur que le nom ait été rentré et que ce ne soit pas un caractere interdit pour nommer un fichier
                            //On récupère le nom du bébé dans l'editText
                            String nom = sonNom.getText().toString();

                            String cheminFichierTx = getExternalFilesDir(null).getAbsolutePath();
                            cheminFichierTx += "/babyboohSongs/";
                            File repertoire = new File(cheminFichierTx);
                            File from = new File(repertoire, "MessagePourBebe.mp4");
                            File to = new File(repertoire, nom.trim() + ".mp4");
                            from.renameTo(to);


                            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            prefs.edit().putString("nomDuSon", nom+".mp4").apply();
                            finish();

                        }
                    });
            ////////////////////////////////////////////////////////////////////////////////////////////////////

            // Affichage de la premi�re fenetre de dialogue

            // Cr�ation d'une fen�tre de dialogue demandant si l'utlisateur veut �couter le son qu'il vien d'enregistrer


            final AlertDialog.Builder alerteReecoute = new AlertDialog.Builder(
                    EnregistrerActivity2.this);


            alerteReecoute.setMessage(R.string.alerte1);


            // D�finition du bouton oui
            alerteReecoute.setPositiveButton(R.string.oui,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            background2 = new Thread(new Runnable() {

                                public void run() {

                                    enregistreur.lireSon();

                                    enregistreur.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        public void onCompletion(MediaPlayer mp) {
                                            enregistreur.arreterLecture();
                                            alerteReecoute.show();

                                        }

                                    });


                                }
                            });

                            background2.start();

                        }
                    });

            // D�finition du bouton non
            alerteReecoute.setNegativeButton(R.string.non,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            dialog.cancel();
                            alerteNom.show();
                        }
                    });
            alerteReecoute.show();



        }
    }

    protected void onPause()  {


        if (enregistrementEnCours) {
            enregistrementEnCours = false;

            enregistreur.arreterEnregistrement();
            enregistreur.arreterLecture();
            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            prefs.edit().putString("nomDuSon", "MessagePourBebe.mp4").apply();

        }

        super.onPause();}
}
