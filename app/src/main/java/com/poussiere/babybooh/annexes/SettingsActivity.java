package com.poussiere.babybooh.annexes;


import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.poussiere.babybooh.R;


public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        // couleur de la barre de statuts pour Lolipo et +
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color_dark));
    }
}
