package com.poussiere.babybooh.annexes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poussiere.babybooh.MainActivity;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.objets.Ecoute;
import java.io.IOException;

public class CalibreActivity extends AppCompatActivity {

    private boolean calibrageEnCours;
    private Thread background1;
    private TextView tv1;
    private FloatingActionButton fb;
    private SharedPreferences prefs;
    private Ecoute ecoute;
    private double amplitudeRef;
    private Handler handler;
    private AlertDialog.Builder alertFin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibre);

        // couleur de la barre de statuts pour Lolipo et +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color_dark));
        }


        tv1 = (TextView) findViewById(R.id.tv_lancer_calibrage);
        fb = (FloatingActionButton) findViewById(R.id.bouton_flottant_calibrage);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar_enr2);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.salle_de_calibrage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calibrageEnCours = false;
        ecoute = new Ecoute(this);


        //Construction de l'alert dialog finale
        alertFin = new AlertDialog.Builder(CalibreActivity.this);

        alertFin.setMessage(R.string.calibrage_terminé);
        alertFin.setCancelable(false);
        alertFin.setPositiveButton(R.string.terminer,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                });


    }

    public void onClick(View v) {
        if (!calibrageEnCours) {

            int permissionCheckAudio = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO);
            if (permissionCheckAudio == PackageManager.PERMISSION_GRANTED) {


                calibrageEnCours = true;

                final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                animation.setDuration(1000); // duration - half a second
                animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
                animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
                animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in


                fb.startAnimation(animation);
                tv1.setText(R.string.calibrage_en_cours);


                handler=new Handler(Looper.myLooper()){

                    @Override
                    public void handleMessage(Message msg){
                        super.handleMessage(msg);
                        ecoute.arreterEcoute();
                        calibrageEnCours = false;
                        fb.clearAnimation();
                        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        prefs.edit().putString("amplitudeRef", Double.toString(amplitudeRef)).apply();

                        alertFin.show();



                    }

                };

                background1 = new Thread(new Runnable() {

                    public void run() {


                        try {
                            ecoute.demarrerEcoute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            background1.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        amplitudeRef = ecoute.obtenirAmplitude();

                        while (amplitudeRef==0.0) {
                            try {
                                background1.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            amplitudeRef = ecoute.obtenirAmplitude();
                        }

                        handler.sendEmptyMessage(0);
                    } });

                background1.start();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MainActivity.MY_PERMISSIONS_REQUEST_AUDIO_RECORD);
            }

            //////////////////////////////////////////////////////////////////////////////////////////////////
            //Construction d'une alertedialog pour demander de nommer le fichier son

        }
    }

    protected void onPause() {


        if (calibrageEnCours) {
            calibrageEnCours = false;

            ecoute.arreterEcoute();


        }

        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Return to mainactivity when home button is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
