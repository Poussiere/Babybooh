package com.poussiere.babybooh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//Splashscreen avec un temps défini. Il semble que ce soit considéré comme une mauvaise pratique bien que assez répandu
rivate static int SPLASH_TIME_OUT = 2000;     

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                
                Intent i = new Intent (this, MainActivity.class);
                startActivity(i);
 
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}


/*
//SplashCreen sans durée définie (juste affiché pendant le temps de chargement qui peut être très court. C'est une bonne pratique
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
*/
