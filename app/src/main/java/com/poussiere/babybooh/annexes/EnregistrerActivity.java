package com.poussiere.babybooh.annexes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
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

    Button bouton3;

    GridLayoutManager lLayout;
    EnregistrerRecyclerViewAdapter rcAdapter;
    RecyclerView rView;
    FloatingActionButton fb;
    String sn;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    public static final int MY_PERMISSIONS_REQUEST_AUDIO_RECORD = 42;

;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(AudioManager.STREAM_MUSIC); // Pour la seekbar
        setContentView(R.layout.activity_enregistrer);

        //Demander autorisation d'acceder au micro
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


        // couleur de la barre de statuts pour Lolipo et +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color_dark));
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
                if (!sn.equals("Music box 1")){

                AlertDialog.Builder supConfirm = new AlertDialog.Builder(
                        EnregistrerActivity.this);
                supConfirm.setMessage(R.string.supConfirm);

                supConfirm.setPositiveButton(R.string.confirmer,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File (getExternalFilesDir(null).getAbsolutePath()+"/babyboohSongs/"+sn+".3gpp");
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
                    rcAdapter.notifyDataSetChanged();
                }// fin du if (!sn.equals...)
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rView);

        /////////////////////////////////////////////////////////////////////////////


        rcAdapter = new EnregistrerRecyclerViewAdapter(EnregistrerActivity.this);
        rView.setAdapter(rcAdapter);




    }


    public void onClick3 (View view)
    {
        //Voir si on a bien la permission d'enregistrer en accédant au micro (persmission dangeureuse!!!)
        int permissionCheckAudio = ContextCompat.checkSelfPermission(this,
        Manifest.permission.RECORD_AUDIO);

        Intent intent = new Intent (EnregistrerActivity.this, EnregistrerActivity2.class);
        startActivity(intent);
        
        

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void onResume() {
    rcAdapter.notifyDataSetChanged();
        super.onResume();
    }

///// handle click on home button on the tool bar
 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Return to mainactivity when home button is clicked
            case android.R.id.home:
              /*  Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                */
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
