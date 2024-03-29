

package com.poussiere.babybooh;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.poussiere.babybooh.annexes.AProposActivity;
import com.poussiere.babybooh.annexes.CalibreActivity;
import com.poussiere.babybooh.bdd.Contract;
import com.poussiere.babybooh.mainFragment1.MainFragment1;
import com.poussiere.babybooh.mainFragment2.mainFragment2;
import com.poussiere.babybooh.annexes.EnregistrerActivity;
import com.poussiere.babybooh.mainFragment3.mainFragment3;
import com.poussiere.babybooh.welcomeFragments.WelcomeFragment1;
import com.poussiere.babybooh.welcomeFragments.WelcomeFragment2;
import com.poussiere.babybooh.welcomeFragments.WelcomeFragment3;
import com.poussiere.babybooh.welcomeFragments.WelcomeFragment4;
import com.poussiere.babybooh.welcomeFragments.WelcomeFragment5;
import com.poussiere.babybooh.welcomeFragments.WelcomeFragment6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {


    private Intent intentEnregistrer;
    private Intent intentSettings;
    private Intent intentCalibrer;
    private Intent intentAPropos;
    private SharedPreferences prefs = null;
    private FrameLayout conteneur;
    private RelativeLayout conteneurDuMain;
    private RelativeLayout welcomeConteneur;
    private LinearLayout circlesConteneur;
    private FrameLayout welcomeFragment;
    public static final int MY_PERMISSIONS_REQUEST_AUDIO_RECORD = 42;
    private Fragment welcomeFrag;

    private TextView welcomeNextButton;
    private TextView welcomeReturnButton;


    //Circle indicator de la séquence de bienvenue
    private ImageView [] circleTab;

    //Déclrations des variables servant dans les alertDialog du premier lancement

    String nom;


    private BottomNavigationView mBottomNav;
    private MenuItem selectedItem;
    private int mSelectedItem;

    private static final String SELECTED_ITEM = "arg_selected_item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.poussiere.babybooh.R.layout.activity_main);
        intentEnregistrer = new Intent(MainActivity.this, EnregistrerActivity.class);
        intentSettings = new Intent(MainActivity.this, AppCompatPreferenceActivity.class);
        intentCalibrer = new Intent(MainActivity.this, CalibreActivity.class);
        intentAPropos = new Intent(MainActivity.this, AProposActivity.class);
        conteneur=(FrameLayout)findViewById(R.id.conteneur_boutons);
        welcomeConteneur=(RelativeLayout)findViewById(R.id.conteneur_du_premier_lancement);
        circlesConteneur=(LinearLayout)findViewById(R.id.circles_id);
        welcomeFragment=(FrameLayout) findViewById(R.id.welcome_fragment_id);
        welcomeNextButton=(TextView) findViewById(R.id.btn_next);
        welcomeReturnButton = (TextView) findViewById(R.id.btn_retour);
        conteneurDuMain=(RelativeLayout)findViewById(R.id.conteneur_du_main);

        // couleur de la barre de statut
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color_dark));

        //Toolbar :
        Toolbar mToolbar = (Toolbar) findViewById(com.poussiere.babybooh.R.id.my_toolbar1);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setTitle(com.poussiere.babybooh.R.string.app_name);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Création du circle indicator de la séquence de bienvenue
      
 
     
        circleTab = new ImageView[6]; // Il y a 6 fragents dans la séquence d'accueil, donc 7 cercles
 
        for (int i = 0; i < 6 ; i++) {
            circleTab[i] = new ImageView(this);
            circleTab[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.non_selected_circle, null));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
 
            params.setMargins(4, 0, 4, 0);
 
            circlesConteneur.addView(circleTab[i], params);
        }
 
        circleTab[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_circle, null));
       
    


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Bottom bar et fragments

        mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {


                selectFragment(item);
                return true;
            }
        });



        //I have a doubt on it
      if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else { selectedItem = mBottomNav.getMenu().getItem(0);
          mBottomNav.setSelectedItemId(R.id.accueil_btn);}



        selectFragment(selectedItem);




        ///////////////////////////////////////////////////////////////
        //Lancement du welcome fragment

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstrun", true)) {
            welcomeFrag = WelcomeFragment1.newInstance();


            welcomeConteneur.setVisibility(VISIBLE);

            if (welcomeFrag != null) {

                welcomeFragment.removeAllViews();
                FragmentTransaction fragTrans = getFragmentManager().beginTransaction().addToBackStack(null);

                fragTrans.add(R.id.welcome_fragment_id, welcomeFrag, welcomeFrag.getTag());
                fragTrans.commit();


            }
            
             /////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////Demander autorisation d'acceder au micro
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

            /////////////////////////////////////////////////////////////////////////////////////////
            //Création des sharedpréference qui permettront de dire si un monstre a été débloqué ou non (ne pas oublier de remettre à 0 en cas de reinitrences lorsqu'on fait reset (sharedprefenrece.clear())
            prefs.edit().putBoolean("monstre1", false).apply();
            prefs.edit().putBoolean("monstre2", false).apply();
            prefs.edit().putBoolean("monstre3", false).apply();
            prefs.edit().putBoolean("monstre4", false).apply();
            prefs.edit().putBoolean("monstre5", false).apply();
            prefs.edit().putBoolean("monstre6", false).apply();
            prefs.edit().putBoolean("monstre7", false).apply();
            prefs.edit().putBoolean("monstre8", false).apply();
            prefs.edit().putBoolean("monstre9", false).apply();
            prefs.edit().putBoolean("monstre10", false).apply();
            prefs.edit().putBoolean("monstre11", false).apply();
            prefs.edit().putBoolean("monstre12", false).apply();
            prefs.edit().putBoolean("unReveil",false).apply();
            prefs.edit().putBoolean("plusieursReveil",false).apply();

           //Effacement du nom si celui ci avait déjà été suivi et mise du sexe à la valeur par défaut
            prefs.edit().putString("sexe","fille");
            prefs.edit().putString("nom", "").apply();


            /////////////////////////////////////////////////////////////
            //Création du folder dans lequel sertont enregistrés les sons
            String folder_main = "babyboohSongs";

            File f = new File(getExternalFilesDir(null), folder_main);

            if (!f.exists()) {
                f.mkdirs();
            }

            ////////////////////////////////////////////////////////////////
            //Cope de ani couni sur la mémoire interne
            
            try {
            
            if (f.mkdirs() || f.isDirectory()) {
                String str_song_name = "Music box 1" + ".3gpp";
                String path = this.getExternalFilesDir(null).getAbsolutePath() ;
                path += "/babyboohSongs";
                //CopyRAWtoSDCard(R.raw.anicouni, path + File.separator + str_song_name);
                 InputStream in = getResources().openRawResource(R.raw.musicbox1);
                  FileOutputStream out = new FileOutputStream(path+File.separator+str_song_name);
                 byte[] buff = new byte[1024];
                  int read = 0;
                try {
                 while ((read = in.read(buff)) > 0) {
                  out.write(buff, 0, read);
                   }
                } finally {
                   in.close();
                 out.close();}

                //Music box 2
                str_song_name = "Music box 2" + ".3gpp";
                in = getResources().openRawResource(R.raw.musicbox2);
                out = new FileOutputStream(path+File.separator+str_song_name);
                buff = new byte[1024];
                read = 0;
                try {
                    while ((read = in.read(buff)) > 0) {
                        out.write(buff, 0, read);
                    }
                } finally {
                    in.close();
                    out.close();}

                //Music box 3
                str_song_name = "Music box 3" + ".3gpp";
                in = getResources().openRawResource(R.raw.musicbox3);
                out = new FileOutputStream(path+File.separator+str_song_name);
                buff = new byte[1024];
                read = 0;
                try {
                    while ((read = in.read(buff)) > 0) {
                        out.write(buff, 0, read);
                    }
                } finally {
                    in.close();
                    out.close();}


            }
                } catch (IOException e) {
            e.printStackTrace();
               }

            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putString("nomDuSon", "Music box 1"+".3gpp").apply();


            AlertDialog.Builder alertAvertissement = new AlertDialog.Builder(
                    MainActivity.this);

            alertAvertissement.setMessage(R.string.a_propos2);
            alertAvertissement.setTitle(R.string.avertissement);

            alertAvertissement.setPositiveButton(R.string.compris,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            dialog.cancel();

                        }
                    });

            alertAvertissement.show();
            
            
        }
        else{
            conteneurDuMain.setVisibility(VISIBLE);
        }



        
    }// fin du onCreate


    @Override
    protected void onResume()
    {
        //Il faut mettre toutes les shared preferences à false des le onResume sinon un message concernant les réveils s'affiche au premier lancement

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Vérification s'il y a des notifications à afficher
        if(prefs.getBoolean("unReveil", true))
        {
            prefs.edit().putBoolean("unReveil",false).apply();
            //afficher alertDialog
            final AlertDialog.Builder alertReveil1 = new AlertDialog.Builder(
                    MainActivity.this);
            alertReveil1.setCancelable(false);
            nom=prefs.getString("nom","bebe");
            String sexe=prefs.getString("sexe","fille");
            String alRev1="";
            
            if (sexe.equals("garcon")){
            alRev1=getString(com.poussiere.babybooh.R.string.retour_de_ecoute1_garcon, nom );}
                            
            else if (sexe.equals("fille")){
            alRev1=getString(com.poussiere.babybooh.R.string.retour_de_ecoute1_fille, nom );}               
                            
            alertReveil1.setMessage(alRev1);

            alertReveil1.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
            alertReveil1.show();
        }
        else if(prefs.getBoolean("plusieursReveil", true))
        {
            prefs.edit().putBoolean("plusieursReveil",false).apply();
            final AlertDialog.Builder alertReveil2 = new AlertDialog.Builder(MainActivity.this);
            alertReveil2.setCancelable(false);
            nom=prefs.getString("nom","bebe");
            String sexe=prefs.getString("sexe","fille");
            String alRev2="";
                
            if (sexe.equals("garcon")){
            alRev2=getString(com.poussiere.babybooh.R.string.retour_de_ecoute2_garcon, nom);}
                
             else if (sexe.equals("fille")){
             alRev2=getString(com.poussiere.babybooh.R.string.retour_de_ecoute2_fille, nom);
                }

            alertReveil2.setMessage(alRev2);
            alertReveil2.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
            alertReveil2.show();

        }

        super.onResume();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.poussiere.babybooh.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case com.poussiere.babybooh.R.id.action_settings:
                startActivity(intentSettings);
                return true;

            case R.id.calibrer:
                startActivity(intentCalibrer);
                return true;

            case com.poussiere.babybooh.R.id.enregistrer:
                startActivity(intentEnregistrer);
                return true;

            case R.id.a_propos:
                startActivity(intentAPropos);
                return true;

            case com.poussiere.babybooh.R.id.reinit:
                ////////////////////////////////////////////////////////////////////////
                // Cr�ation d'une fen�tre de dialogue demandant si l'utlisateur veut vraiment tout supprimer


                AlertDialog.Builder alerteReinit = new AlertDialog.Builder(
                        MainActivity.this);



                // Setting Dialog Message
                alerteReinit.setMessage(com.poussiere.babybooh.R.string.alerte4);

                // Attention à bien supprimer les fichiers sons également
                // D�finition du bouton oui
                alerteReinit.setPositiveButton(com.poussiere.babybooh.R.string.oui,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //Remise à zero de la base de données
                                getContentResolver().delete(Contract.Evenements.URI, null, null );

                                
                                // Les fichiers sons sont effacés
                                String cheminFichierTx = getExternalFilesDir(null).getAbsolutePath();
                                cheminFichierTx += "/babyboohSongs/";
                                File repertoire = new File(cheminFichierTx);

                                if (repertoire.isDirectory())
                                {
                                    String[] children = repertoire.list();
                                    for (int i = 0; i < children.length; i++)
                                    {
                                        new File(repertoire, children[i]).delete();
                                    }
                                }
                                //L'application sera lancée comme si c'était la première fois
                                prefs.edit().putBoolean("firstrun", true).apply();
                                finish();

                            }
                        });

                // D�finition du bouton non
                alerteReinit.setNegativeButton(com.poussiere.babybooh.R.string.non,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // le fihcier son est lu et une autre boite de dialogue est ouverte proposant de lire � nouveau le son

                                dialog.cancel();
                            }
                        });
                alerteReinit.show();
                return true;


                //////////////////////////////////////////////////////////////////////////

            default:
                return super.onOptionsItemSelected(item);
        }



    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);

        super.onSaveInstanceState(outState);
    }


  /*  @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            finish();
        }
    }*/


    private void selectFragment(MenuItem item) {

        conteneur.removeAllViews();
        Fragment frag = null;


        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.accueil_btn:
                frag = MainFragment1.newInstance();
                break;
            case R.id.historique_btn:
                frag = mainFragment2.newInstance();
                break;
            case R.id.gallerie_btn:
                frag = mainFragment3.newInstance();
                break;

        }

        // update selected item
        mSelectedItem = item.getItemId();

        /*

        // uncheck the other items.
       for (int i = 0; i< mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

*/

        if (frag != null) {


             FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.add(R.id.conteneur_boutons, frag, frag.getTag());
            ft.commit();
        }

    }

    public void onWelcomeSuivantClick(View v) {
        Fragment f = getFragmentManager().findFragmentById(R.id.welcome_fragment_id);
        if (f instanceof WelcomeFragment1) {
            diplayWelcomeFragment(2);
            welcomeReturnButton.setVisibility(VISIBLE);


        } else if (f instanceof WelcomeFragment2) {
            diplayWelcomeFragment(3);

        } else if (f instanceof WelcomeFragment3) {
            diplayWelcomeFragment(4);
String alertDiag5;
    String alertDiag6;
    String lui, elle;
    int nbNom;
        } else if (f instanceof WelcomeFragment4) {

            //On récupère le sexe qui a été coché et on l'enregistre dans un sharedpreference
            RadioGroup sexe = (RadioGroup) f.getView().findViewById(R.id.radioSex);
            boolean sexeOk = false;
            boolean nameOk = false;

            int itemCheckedIndex = sexe.indexOfChild(findViewById(sexe.getCheckedRadioButtonId()));
            switch (itemCheckedIndex) {
                case 0:
                    prefs.edit().putString("sexe", "fille").apply();
                    sexeOk = true;
                    break;
                case 1:
                    prefs.edit().putString("sexe", "garcon").apply();
                    sexeOk = true;
                    break;
                default:
                    Toast.makeText(this, R.string.choix_sexe_toast, Toast.LENGTH_LONG).show();
                    sexeOk = false;
                    break;
            }


            EditText bebeNom = (EditText) f.getView().findViewById(R.id.welcome_bebe_nom_edit_text);
            String nom = bebeNom.getText().toString();

            if (!nom.equals("")) {
                prefs.edit().putString("nom", nom).apply();
                nameOk = true;
            } else {
                nameOk = false;
                Toast.makeText(this, R.string.entrerNom, Toast.LENGTH_LONG).show();
            }
            if (sexeOk && nameOk) {

                diplayWelcomeFragment(5);
            }
        } else if (f instanceof WelcomeFragment5) {

            welcomeNextButton.setText(R.string.terminer);
            diplayWelcomeFragment(6);
        }else if (f instanceof WelcomeFragment6){
            welcomeConteneur.setVisibility(GONE);
        
            prefs.edit().putBoolean("firstrun", false).apply();
            
            
            Intent intent = new Intent(MainActivity.this, CalibreActivity.class);
            conteneurDuMain.setVisibility(VISIBLE);
            startActivity(intent);
        }

    }
            
            //Listener du bouton retour de la séquence d'accueil
            public void onWelcomeRetourClick(View view){
                 Fragment f = getFragmentManager().findFragmentById(R.id.welcome_fragment_id);
        if (f instanceof WelcomeFragment2) {
          diplayWelcomeFragment(1);
                welcomeReturnButton.setVisibility(GONE);


        } else if (f instanceof WelcomeFragment3) {
            diplayWelcomeFragment(2);
            
            }
                else if (f instanceof WelcomeFragment4) {
            diplayWelcomeFragment(3);
            
            }

            else if (f instanceof WelcomeFragment5) {
           diplayWelcomeFragment(4);
            // Checker s'il y a déjà un sharedpreference pour le sexe et le nom et remplir les cases le cas échéant
            }

         else if (f instanceof WelcomeFragment6) {
            welcomeNextButton.setText(R.string.continuer);
           diplayWelcomeFragment(5);
            }


    }
                //Methode qui affiche le welcome fragment à l'index indiqué
                public void diplayWelcomeFragment (int index)
                { switch (index){
                    case 1 :
                        welcomeFrag = WelcomeFragment1.newInstance();
                        if (welcomeFrag!=null){
                        welcomeFragment.removeAllViews();
                        FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                        fragTrans.add(R.id.welcome_fragment_id, welcomeFrag, welcomeFrag.getTag());
                        fragTrans.commit();
                            for (int i = 0; i < 6 ; i++) {
                                circleTab[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.non_selected_circle, null));
                            }

                            circleTab[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_circle, null));
                        }
                        break;
                    case 2 :
                        welcomeFrag = WelcomeFragment2.newInstance();
                        if (welcomeFrag!=null){
                            welcomeFragment.removeAllViews();
                            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                            fragTrans.add(R.id.welcome_fragment_id, welcomeFrag, welcomeFrag.getTag());
                            fragTrans.commit();
                            for (int i = 0; i < 6 ; i++) {
                                circleTab[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.non_selected_circle, null));
                            }

                            circleTab[1].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_circle, null));
                        }
                        break;
                    case 3 :
                        welcomeFrag = WelcomeFragment3.newInstance();
                        if (welcomeFrag!=null){
                            welcomeFragment.removeAllViews();
                            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                            fragTrans.add(R.id.welcome_fragment_id, welcomeFrag, welcomeFrag.getTag());
                            fragTrans.commit();
                            for (int i = 0; i < 6 ; i++) {
                                circleTab[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.non_selected_circle, null));
                            }

                            circleTab[2].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_circle, null));
                        }
                        break;
                    case 4:
                        welcomeFrag = WelcomeFragment4.newInstance();
                        if (welcomeFrag!=null){
                            welcomeFragment.removeAllViews();
                            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                            fragTrans.add(R.id.welcome_fragment_id, welcomeFrag, welcomeFrag.getTag());
                            fragTrans.commit();
                            for (int i = 0; i < 6 ; i++) {
                                circleTab[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.non_selected_circle, null));
                            }

                            circleTab[3].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_circle, null));
                        }
                        break;
                    case 5:
                        welcomeFrag = WelcomeFragment5.newInstance();
                        if (welcomeFrag!=null){
                            welcomeFragment.removeAllViews();
                            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                            fragTrans.add(R.id.welcome_fragment_id, welcomeFrag, welcomeFrag.getTag());
                            fragTrans.commit();
                            for (int i = 0; i < 6 ; i++) {
                                circleTab[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.non_selected_circle, null));
                            }

                            circleTab[4].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_circle, null));
                        }
                        break;
                    case 6:
                        welcomeFrag = WelcomeFragment6.newInstance();
                        if (welcomeFrag!=null){
                            welcomeFragment.removeAllViews();
                            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                            fragTrans.add(R.id.welcome_fragment_id, welcomeFrag, welcomeFrag.getTag());
                            fragTrans.commit();
                            for (int i = 0; i < 6 ; i++) {
                                circleTab[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.non_selected_circle, null));
                            }

                            circleTab[5].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_circle, null));
                        }
                        break;

                }
                }

    @Override
    public void onBackPressed(){

        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}
