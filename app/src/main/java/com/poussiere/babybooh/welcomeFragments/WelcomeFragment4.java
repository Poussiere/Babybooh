package com.poussiere.babybooh.welcomeFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.poussiere.babybooh.R;


public class WelcomeFragment4 extends Fragment {

    private SharedPreferences prefs;
    private RadioGroup radioGroup;
    private EditText editText;
    public WelcomeFragment4() {
        // Required empty public constructor
    }


    public static WelcomeFragment4 newInstance() {
        WelcomeFragment4 fragment = new WelcomeFragment4();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutView = inflater.inflate(R.layout.fragment_welcome_fragment4, container, false);
        prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());
        radioGroup=(RadioGroup)layoutView.findViewById(R.id.radioSex);
        editText=(EditText)layoutView.findViewById(R.id.welcome_bebe_nom_edit_text);

        //On regarde si un sexe a déjà été rempli (par defaut c'est fille de toutes façons. Si c'est garcon on précheck garçon
        boolean fille = prefs.getBoolean("fille", true);
        if (!fille){
            radioGroup.check(R.id.radioGarcon);
        }

        //On rempli déjà le nom s'il existe déjà dans le sharedpreference
        String nom = prefs.getString("nom", "nomParDefaut");
        if (!nom.equals("nomParDefaut")){
            editText.setText(nom);
        }


        return layoutView;
    }

}
