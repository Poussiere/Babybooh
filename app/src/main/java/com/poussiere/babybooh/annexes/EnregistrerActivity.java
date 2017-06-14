package com.poussiere.babybooh.annexes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.poussiere.babybooh.R;
import com.poussiere.babybooh.objets.Enregistreur;

import java.io.File;
import java.io.IOException;


// Faire apparataitre un dialogue proposant d'�couter l'enregistrement et de le modifier si n�cessaire
//Enregistrer sonNom dans une sharepreference...



public class EnregistrerActivity extends AppCompatActivity {

    public static final String ACT3 = "activite 3";
    TextView tv1;
    Enregistreur enregistreur ;
    boolean enregistrementEnCours ;
    Button bouton3;
    Thread background1, background2 ;
    GridLayoutManager lLayout;
    EnregistrerRecyclerViewAdapter rcAdapter;
    RecyclerView rView;
    FloatingActionButton fb;
    String sn;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    public static final int MY_PERMISSIONS_REQUEST_AUDIO_RECORD = 42;
    public static final int MY_PERMISSIONS_REQUEST_WRITE = 43

;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(AudioManager.STREAM_MUSIC); // Pour la seekbar
        setContentView(R.layout.activity_enregistrer);

        // couleur de la barre de statuts pour Lolipo et +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.gris_material_700));
        }
        //////////////////////////////////////////////////////////////


        tv1=(TextView)findViewById(R.id.tvMerci);
        fb=(FloatingActionButton)findViewById(R.id.boutonFlottantEnregistrer);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.salle_enregistrement);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lLayout = new GridLayoutManager(EnregistrerActivity.this, 1);

        rView = (RecyclerView)findViewById(R.id.recycler_view_enregistrer);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        ////////////////////////////////////////////////////////////////////////////////
        //Création de la seekbar pour le volume

        volumeSeekbar = (SeekBar) findViewById(R.id.seek_volume);
        try
        {
            volumeSeekbar = (SeekBar)findViewById(R.id.seek_volume);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        /////////////////////////////////////////////////////////////////////////
        //Creation de la swipe to delete de la recyclerView

       ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove (RecyclerView r, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder vh2)
            {return true;}




            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                TextView nds= (TextView) viewHolder.itemView.findViewById(R.id.son_nom_tv);
                sn = nds.getText().toString();


                AlertDialog.Builder supConfirm = new AlertDialog.Builder(
                        EnregistrerActivity.this);
                supConfirm.setMessage(R.string.supConfirm);

                supConfirm.setPositiveButton(R.string.confirmer,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File (Environment.getExternalStorageDirectory().getAbsolutePath()+"/babyboohSongs/"+sn);
                                file.delete();
                                rcAdapter = new EnregistrerRecyclerViewAdapter(EnregistrerActivity.this);
                                rView.setAdapter(rcAdapter);
                                dialog.dismiss();
                            }
                        });
                supConfirm.setNegativeButton(R.string.annuler,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                rcAdapter = new EnregistrerRecyclerViewAdapter(EnregistrerActivity.this);
                                rView.setAdapter(rcAdapter);
                                dialog.dismiss();
                            }
                        });

               supConfirm.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rView);

        /////////////////////////////////////////////////////////////////////////////


        rcAdapter = new EnregistrerRecyclerViewAdapter(EnregistrerActivity.this);
        rView.setAdapter(rcAdapter);

        enregistrementEnCours=false;
        enregistreur = new Enregistreur();


    }


    public void onClick3 (View view)
    {
        //Voir si on a bien la permission d'enregistrer en accédant au micro (persmission dangeureuse!!!)
        int permissionCheckAudio = ContextCompat.checkSelfPermission(this,
        Manifest.permission.RECORD_AUDIO);

        int permissionChecWrite= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        
        
        if (!enregistrementEnCours) {
           
        if (permissionCheckAudio== PackageManager.PERMISSION_GRANTED && permissionChecWrite==PackageManager.PERMISSION_GRANTED){

            
            enregistrementEnCours = true;

            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
            animation.setDuration(1000); // duration - half a second
            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
            animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
            animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

            fb.startAnimation(animation);
            tv1.setText(R.string.consigneRecord2);
            fb.setContentDescription(getString(R.string.content_description_arreter_enregistreent));
            background1 = new Thread (new Runnable(){

                public void run () {
                    Log.i(ACT3, "debut de l'execution du thread enregistreur");


                    try {
                        enregistreur.demarrerEnregistrement();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            //bouton3.setText(R.string.stopRecord);
            background1.start();
        }else {
            //On va demander l'autorisation
           if (permissionCheckAudio== PackageManager.PERMISSION_DENIED){
               ActivityCompat.requestPermissions(this,
                       new String[]{Manifest.permission.RECORD_AUDIO},
                       MY_PERMISSIONS_REQUEST_AUDIO_RECORD);
           }
            if (permissionChecWrite==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE);
            }
        }
                  
        
        }

        else
        {

            enregistreur.arreterEnregistrement();
            enregistrementEnCours = false;
            fb.clearAnimation();
            fb.setContentDescription(getString(R.string.content_description_demarrer_enregistreent));
            tv1.setText(R.string.consigneRecord);


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
            filters[0] = new InputFilter(){
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (end > start) {

                        char[] acceptedChars = new char[]{'a', 'b', 'c', 'd', 'e','f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
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

            final AlertDialog.Builder alerteNom = new AlertDialog.Builder(EnregistrerActivity.this);

            alerteNom.setMessage(R.string.alerte5);
            alerteNom.setView(sonNom);
            alerteNom.setPositiveButton(R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //Ajouter une condition pour etre sur que le nom ait été rentré et que ce ne soit pas un caractere interdit pour nommer un fichier
                            //On récupère le nom du bébé dans l'editText
                            String nom = sonNom.getText().toString();

                            String cheminFichierTx = Environment.getExternalStorageDirectory().getAbsolutePath();
                            cheminFichierTx += "/babyboohSongs/";
                            File repertoire = new File(cheminFichierTx);
                            File from = new File(repertoire, "messagePourBebe.mp4");
                            File to = new File(repertoire, nom.trim() + ".mp4");
                            from.renameTo(to);



                            // Apres il faut mettre à jour la vue. Il y a surement un moyen plus optimal que de recreer une instance de l'adapter commee ci dessous
                            rcAdapter = new EnregistrerRecyclerViewAdapter(EnregistrerActivity.this);
                            rView.setAdapter(rcAdapter);
                        }
                    });
            ////////////////////////////////////////////////////////////////////////////////////////////////////

            // Affichage de la premi�re fenetre de dialogue

            // Cr�ation d'une fen�tre de dialogue demandant si l'utlisateur veut �couter le son qu'il vien d'enregistrer


            final AlertDialog.Builder alerteReecoute = new AlertDialog.Builder(
                    EnregistrerActivity.this);


            alerteReecoute.setMessage(R.string.alerte1);


            // D�finition du bouton oui
            alerteReecoute.setPositiveButton(R.string.oui,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            background2 = new Thread (new Runnable(){

                                public void run () {
                                    Log.i(ACT3, "debut de l'execution du thread de lecture");

                                      enregistreur.lireSon();
                                        Log.i(ACT3,"lecture du son lancé");

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



    }}

//////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void onResume() {

        super.onResume();
    }

    protected void onPause()  {


        if (enregistrementEnCours)
        {enregistrementEnCours=false;}

        enregistreur.arreterEnregistrement();
        enregistreur.arreterLecture();



        super.onPause();}



}
