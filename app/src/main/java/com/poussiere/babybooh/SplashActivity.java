package com.poussiere.babybooh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    /*

    //Splashscreen avec un temps défini. Il semble que ce soit considéré comme une mauvaise pratique bien que assez répandu
    private static int SPLASH_TIME_OUT = 2000;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i = new Intent(this, MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
*/



//SplashCreen sans durée définie (juste affiché pendant le temps de chargement qui peut être très court. C'est une bonne pratique

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

