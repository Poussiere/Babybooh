package com.poussiere.babybooh.welcomeFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poussiere.babybooh.R;


public class WelcomeFragment5 extends Fragment {
   private TextView tv1;
    private TextView tv2;
    private SharedPreferences prefs;


    public WelcomeFragment5() {
        // Required empty public constructor
    }

   
    public static WelcomeFragment5 newInstance() {
        WelcomeFragment5 fragment = new WelcomeFragment5();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_welcome_fragment5, container, false);

        tv1=(TextView)layoutView.findViewById(R.id.welcome_fragment_5_text1);
        tv2=(TextView)layoutView.findViewById(R.id.welcome_fragment_5_text2);
        
         prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String nom=prefs.getString("nom","Rose");
        int nbNom=nom.length();
        
        // Si fille et nombre de lettres du nom est pair :
                            if (prefs.getBoolean("fille",true) && (nbNom%2)==0)
                            {   tv1.setText(R.string.caractere_fille);
                                tv2.setText(getString(com.poussiere.babybooh.R.string.caractere_fille1, nom));}
                            // Si Fille et nombre de lettres du nom est impair :
                            else if (prefs.getBoolean("fille",true) && (nbNom%2)!=0)
                            {tv1.setText(R.string.caractere_fille);
                                tv2.setText(getString(com.poussiere.babybooh.R.string.caractere_fille2, nom));}
                            // Si Garcon et nombre de lettres du nom est pair :
                            else if (!prefs.getBoolean("fille",true) && (nbNom%2)==0)
                            {tv1.setText(R.string.caractere_garcon);
                                tv2.setText(getString(R.string.caractere_garçon1, nom));}
                            // Si garcon et nombre de lettres du nom est impair :
                            else if (!prefs.getBoolean("fille",true) && (nbNom%2)!=0)
                            {tv1.setText(R.string.caractere_garcon);
                                tv2.setText(getString(R.string.caractere_garçon2, nom));}
                          
        return layoutView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

        }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
