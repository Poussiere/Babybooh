package com.poussiere.babybooh.mainFragment1;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poussiere.babybooh.MainActivity;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.activite1.EcouteActivity;


public class main_fragment1 extends Fragment {



    private FloatingActionButton fabLancerVeille;
    private Intent intentLancerVeille;
    protected Intent avionIntent;
    // Cr�er un ContentResolver sans l'instancier pour d�tecter le mode avion
    private ContentResolver contentResolver ;

    public main_fragment1() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static main_fragment1 newInstance() {
        main_fragment1 fragment = new main_fragment1();
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
        View layoutView  = inflater.inflate(R.layout.fragment_main1, container, false);
        fabLancerVeille=(FloatingActionButton)layoutView.findViewById(R.id.boutonLancerVeille);

        fabLancerVeille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Séquence du mode avion

                if (!estEnModeAvion())

                {   AlertDialog.Builder alerteOndes = new AlertDialog.Builder(
                        getActivity());

                    // Setting Dialog Message
                    alerteOndes.setMessage(com.poussiere.babybooh.R.string.alerte2);


                    // D�finition du bouton oui
                    alerteOndes.setPositiveButton(com.poussiere.babybooh.R.string.oui,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    avionIntent=new Intent (Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            startActivity(avionIntent);
                                        }
                                    } );}


                            });

                    // D�finition du bouton non
                    alerteOndes.setNegativeButton(com.poussiere.babybooh.R.string.non,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alerteOndes.show();}


                else

                {Intent i = new Intent(getActivity(), EcouteActivity.class);
                    startActivity(i);}





            }
        });


        return layoutView;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }





    // Cr�er une m�thode qui teste si le mode avion est enclench� ou non :

    public boolean estEnModeAvion ()
    {

        contentResolver = getActivity().getContentResolver();
        if (Settings.System.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) == 0)
        {return false;}

        else return true;

    }


}
