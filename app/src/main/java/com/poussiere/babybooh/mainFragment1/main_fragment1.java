package com.poussiere.babybooh.mainFragment1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.poussiere.babybooh.MainActivity;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.activite1.EcouteActivity;


public class main_fragment1 extends Fragment {



    private FloatingActionButton fabLancerVeille;
    private View topContenair;
    private Intent intentLancerVeille;
    protected Intent avionIntent;
    // Cr�er un ContentResolver sans l'instancier pour d�tecter le mode avion
    private ContentResolver contentResolver ;
    private ImageView monstreFont, bebeAcceuil;
    private View bebeMain;

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
        topContenair=(View)layoutView.findViewById((R.id.main1_top_conteneur));
        monstreFont=(ImageView)layoutView.findViewById(R.id.monstre_du_fond);
        bebeAcceuil=(ImageView)layoutView.findViewById(R.id.image_bebe_accueil);
        bebeMain=(View)layoutView.findViewById((R.id.bebe_main));

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        double heightD = width*0.3;
        int height=(int)heightD;
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        topContenair.setLayoutParams(parms);

        double bebeWidthD= width/2.3;
        int bebeWidth=(int)bebeWidthD;
        double bebeheightD=bebeWidth*1.1;
        int bebeheight=(int) bebeheightD;
        RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams(bebeWidth,bebeheight);
        parms2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        parms2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        parms2.setMarginEnd(30);
        bebeMain.setLayoutParams(parms2);

        Glide.with(this).load(R.drawable.fondmonstre).into(monstreFont);
        Glide.with(this).load(R.drawable.bebeone).into(bebeAcceuil);


        fabLancerVeille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Séquence du mode avion


                int permissionCheckAudio = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.RECORD_AUDIO);
                if (permissionCheckAudio == PackageManager.PERMISSION_GRANTED) {
                    if (!estEnModeAvion())

                    {
                        AlertDialog.Builder alerteOndes = new AlertDialog.Builder(
                                getActivity());

                        // Setting Dialog Message
                        alerteOndes.setMessage(com.poussiere.babybooh.R.string.alerte2);


                        // D�finition du bouton oui
                        alerteOndes.setPositiveButton(com.poussiere.babybooh.R.string.oui,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        avionIntent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                startActivity(avionIntent);
                                            }
                                        });
                                    }


                                });

                        // D�finition du bouton non
                        alerteOndes.setNegativeButton(com.poussiere.babybooh.R.string.non,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        alerteOndes.show();
                    } else

                    {
                        Intent i = new Intent(getActivity(), EcouteActivity.class);
                        startActivity(i);
                    }

                }else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            MainActivity.MY_PERMISSIONS_REQUEST_AUDIO_RECORD);
                }



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
