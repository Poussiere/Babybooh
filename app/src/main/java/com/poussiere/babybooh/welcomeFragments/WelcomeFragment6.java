package com.poussiere.babybooh.welcomeFragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poussiere.babybooh.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment6 extends Fragment {

    private TextView tv1;
    private SharedPreferences prefs;

    public WelcomeFragment6() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WelcomeFragment6 newInstance() {
        WelcomeFragment6 fragment = new WelcomeFragment6();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_welcome_fragment6, container, false);

       prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());
        tv1=(TextView)layoutView.findViewById(R.id.welcome_fragment_6_text);

       String lui=getString(com.poussiere.babybooh.R.string.lui);
       String elle=getString(com.poussiere.babybooh.R.string.elle);
        String nom=prefs.getString("nom", "Rose");

        if (prefs.getBoolean("fille",true)) {
            tv1.setText(getString(com.poussiere.babybooh.R.string.ask_record1,nom , elle));
        }
       else if (!prefs.getBoolean("fille",true)){
            tv1.setText(getString(com.poussiere.babybooh.R.string.ask_record1, nom , lui));
        }

        return layoutView;
    }

}
