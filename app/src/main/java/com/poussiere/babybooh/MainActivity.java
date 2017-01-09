///////////////////////
//Choses à faire
//Pour l'heure des evemenents : lorsque cela tombe rond on a 17h0 et non 17h00 --> fait!
//Le on backpressed du mainactivity doit quitter l'application (et non revenir sur la veille par exemple) --> fait!
// Il faut faire en sorte de ne pas pouvoir revenir dans la veille autrement qu'en appuyant sur le bouton correspondant du mainactivity-->fait!

/////////////////////////////////////////////////////////////////////////////////////

package com.poussiere.babybooh;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.poussiere.babybooh.activite1.EcouteActivity;
import com.poussiere.babybooh.mainFragment1.main_fragment1;
import com.poussiere.babybooh.mainFragment2.StatsActivity;
import com.poussiere.babybooh.mainFragment2.mainFragment2;
import com.poussiere.babybooh.mainFragment3.TableauDeChasseActivity;
import com.poussiere.babybooh.annexes.EnregistrerActivity;
import com.poussiere.babybooh.annexes.SettingsActivity;
import com.poussiere.babybooh.bdd.BddDAO;
import com.poussiere.babybooh.mainFragment3.mainFragment3;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    public static final String ACT_NAME = "main_veille";
    Intent intentEnregistrer;
    Intent intentSettings;
    SharedPreferences prefs = null;
    FrameLayout conteneur;

    //Déclrations des variables servant dans les alertDialog du premier lancement
    String fille, garcon;
    String nom;
    String alertDiag5;
    String alertDiag6;
    String lui, elle;
    int nbNom;

    private BddDAO maBase;

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;

    private static final String SELECTED_ITEM = "arg_selected_item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.poussiere.babybooh.R.layout.activity_main);
        maBase = new BddDAO(this);
        intentEnregistrer = new Intent(MainActivity.this, EnregistrerActivity.class);
        intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
        conteneur=(FrameLayout)findViewById(R.id.conteneur_boutons);
        // couleur de la barre de statuts pour Lolipo et +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.gris_material_700));
        }
        //////////////////////////////////////////////////////////////

        //Toolbar :
        Toolbar mToolbar = (Toolbar) findViewById(com.poussiere.babybooh.R.id.my_toolbar1);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setTitle(com.poussiere.babybooh.R.string.app_name);


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

      MenuItem selectedItem;
     /*  if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else { selectedItem = mBottomNav.getMenu().getItem(0);


        }


        */
        selectedItem = mBottomNav.getMenu().getItem(0);
        selectFragment(selectedItem);













     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Sequence du premier lancement :

      //  prefs = PreferenceManager.getDefaultSharedPreferences(this); //déjà instancié dans le Onresume. A voir si nécessaire de le refaire icic
     //   if (prefs.getBoolean("firstrun", true)) {




        
    }// fin du onCreate

    @Override
    protected void onResume()
    {
        //Il faut mettre toutes les shared preferences à false des le onResume sinon un message concernant les réveils s'affiche au premier lancement

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstrun", true)) {

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


            /////////////////////////////////////////////////////////////
            //Création du folder dans lequel sertont enregistrés les sons
            String folder_main = "babyboohSongs";

            File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            if (!f.exists()) {
                f.mkdirs();
            }

            //////////////////////////////////////////////////////////
            //Construction des modalités de la question du sexe
            fille=getString(com.poussiere.babybooh.R.string.fille);
            garcon=getString(com.poussiere.babybooh.R.string.garcon);
            final CharSequence[] items = {fille,garcon};

            //Construction de l'EditText pour le nom
            final EditText bebeNom = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(20);
            bebeNom.setFilters(FilterArray);
            bebeNom.setLayoutParams(lp);
            /////////////////////////////////////////////////////////////

            //Septieme alertDialog

            final AlertDialog.Builder veryFirstTime7 = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTime7.setCancelable(false);
            veryFirstTime7.setMessage(com.poussiere.babybooh.R.string.ask_record2);
            veryFirstTime7.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(intentEnregistrer);

                        }
                    });

            veryFirstTime7.setNegativeButton(com.poussiere.babybooh.R.string.menu_principal,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

            // Sixième alertDialog
            final AlertDialog.Builder veryFirstTime6 = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTime6.setCancelable(false);

            veryFirstTime6.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            veryFirstTime7.show();

                        }
                    });




            // Cinquième alertDialog
            final AlertDialog.Builder veryFirstTime5 = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTime5.setCancelable(false);
            veryFirstTime5.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            veryFirstTime6.show();

                        }
                    });

            // Quatrieme alertDialog fille
            final AlertDialog.Builder veryFirstTime4Fille = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTime4Fille.setMessage(com.poussiere.babybooh.R.string.caractere_fille);
            veryFirstTime4Fille.setCancelable(false);
            veryFirstTime4Fille.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            veryFirstTime5.show();

                        }
                    });

            //Quatrieme alerDialog garçon
            final AlertDialog.Builder veryFirstTime4Garcon = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTime4Garcon.setMessage(com.poussiere.babybooh.R.string.caractere_garcon);
            veryFirstTime4Garcon.setCancelable(false);
            veryFirstTime4Garcon.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            veryFirstTime5.show();

                        }
                    });


            // Troisieme alertDialog

            final AlertDialog.Builder veryFirstTime3 = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTime3.setMessage(com.poussiere.babybooh.R.string.ask_nom);
            veryFirstTime3.setCancelable(false);
            veryFirstTime3.setView(bebeNom);
            veryFirstTime3.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //On récupère le nom du bébé dans l'editText
                            nom=bebeNom.getText().toString();

                            // On enregistre le nom du bébé dans les sharedPref
                            prefs.edit().putString("nom", nom).apply();

                            // On construit la phrase personnalisée de l'alerdialog 5
                            nbNom=nom.length();
                            // Si fille et nombre de lettres du nom est pair :
                            if (prefs.getBoolean("fille",true) && (nbNom%2)==0)
                            {alertDiag5=getString(com.poussiere.babybooh.R.string.caractere_fille1, nom);}

                            // Si Fille et nombre de lettres du nom est impair :
                            else if (prefs.getBoolean("fille",true) && (nbNom%2)!=0)
                            {alertDiag5=getString(com.poussiere.babybooh.R.string.caractere_fille2, nom);}

                            // Si Garcon et nombre de lettres du nom est pair :
                            else if (!prefs.getBoolean("fille",true) && (nbNom%2)==0)
                            {alertDiag5=getString(com.poussiere.babybooh.R.string.caractere_garçon1, nom);}

                            // Si garcon et nombre de lettres du nom est impair :
                            else if (!prefs.getBoolean("fille",true) && (nbNom%2)!=0)
                            {alertDiag5=getString(com.poussiere.babybooh.R.string.caractere_garçon2, nom);}
                            veryFirstTime5.setMessage(alertDiag5);


                            //On construit la phrase personnalisé de l'alertDialog 6
                            lui=getString(com.poussiere.babybooh.R.string.lui);
                            elle=getString(com.poussiere.babybooh.R.string.elle);

                            if (prefs.getBoolean("fille",true)) alertDiag6=getString(com.poussiere.babybooh.R.string.ask_record1, nom, elle);
                            if (!prefs.getBoolean("fille",true)) alertDiag6=getString(com.poussiere.babybooh.R.string.ask_record1, nom , lui);
                            veryFirstTime6.setMessage(alertDiag6);


                            // Si c'est une fille :
                            if (prefs.getBoolean("fille", true))
                            {dialog.dismiss();
                                veryFirstTime4Fille.show();}

                            //SI c'est un garcon :
                            else
                            {dialog.dismiss();
                                veryFirstTime4Garcon.show();
                            }

                        }
                    });




            //deuxieme AlertDialog
            final AlertDialog.Builder veryFirstTime2Bis = new AlertDialog.Builder(
                    MainActivity.this);
            //veryFirstTime2Bis.setTitle(R.string.ask_genre2);
            veryFirstTime2Bis.setCancelable(false);
            veryFirstTime2Bis.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {


                    switch (item) {
                        case 0:
                            prefs.edit().putBoolean("fille", true).apply();
                            break;
                        case 1:
                            prefs.edit().putBoolean("fille", false).apply();
                            break;

                    }
                    dialog.dismiss();
                    veryFirstTime3.show();
                }
            });

            //deuxieme AlertDialog
            final AlertDialog.Builder veryFirstTime2 = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTime2.setMessage(com.poussiere.babybooh.R.string.ask_genre1);
            veryFirstTime2.setCancelable(false);
            veryFirstTime2.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //si fille, alors very first time 4. setText = R.text.fille et si garçon...
                            dialog.dismiss();
                            veryFirstTime2Bis.show();

                        }
                    });




//alertdialogbis2
            final AlertDialog.Builder veryFirstTimeBis2 = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTimeBis2.setMessage(com.poussiere.babybooh.R.string.first_time3);
            veryFirstTimeBis2.setCancelable(false);
            veryFirstTimeBis2.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //si fille, alors very first time 4. setText = R.text.fille et si garçon...
                            dialog.dismiss();
                            veryFirstTime2.show();

                        }
                    });

            //alertdialog 1bis (après découpage)
            final AlertDialog.Builder veryFirstTimeBis1 = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTimeBis1.setMessage(com.poussiere.babybooh.R.string.first_time2);
            veryFirstTimeBis1.setCancelable(false);
            veryFirstTimeBis1.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //si fille, alors very first time 4. setText = R.text.fille et si garçon...
                            dialog.dismiss();
                            veryFirstTimeBis2.show();

                        }
                    });




            //premiere AlertDialog
            final AlertDialog.Builder veryFirstTime = new AlertDialog.Builder(
                    MainActivity.this);
            veryFirstTime.setTitle(com.poussiere.babybooh.R.string.first_time_tittle);
            veryFirstTime.setMessage(com.poussiere.babybooh.R.string.first_time1);
            veryFirstTime.setCancelable(false);




            // D�finition du bouton continuer de alertDialog2
            veryFirstTime.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            veryFirstTimeBis1.show();

                        }
                    });



            veryFirstTime.show();
            prefs.edit().putBoolean("firstrun", false).apply();
        }

        // fin de la séquence du premier lancement
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////








        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Vérification s'il y a des notifications à afficher
        if(prefs.getBoolean("unReveil", true))
        {
            //afficher alertDialog
            final AlertDialog.Builder alertReveil1 = new AlertDialog.Builder(
                    MainActivity.this);
            alertReveil1.setCancelable(false);
            nom=prefs.getString("nom","bebe");
            String alRev1=getString(com.poussiere.babybooh.R.string.retour_de_ecoute1, nom );
            alertReveil1.setMessage(alRev1);

            alertReveil1.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            prefs.edit().putBoolean("unReveil",false).apply();
                            dialog.dismiss();

                        }
                    });
            alertReveil1.show();
        }
        else if(prefs.getBoolean("plusieursReveil", true))
        {
            final AlertDialog.Builder alertReveil2 = new AlertDialog.Builder(MainActivity.this);
            alertReveil2.setCancelable(false);
            nom=prefs.getString("nom","bebe");
            String alRev2=getString(com.poussiere.babybooh.R.string.retour_de_ecoute2, nom);

            alertReveil2.setMessage(alRev2);
            alertReveil2.setPositiveButton(com.poussiere.babybooh.R.string.continuer,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            prefs.edit().putBoolean("plusieursReveil",false).apply();
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

            case com.poussiere.babybooh.R.id.enregistrer:
                startActivity(intentEnregistrer);
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
                                maBase.open();
                                maBase.delete();
                                maBase.close();

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

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            finish();
        }
    }


    private void selectFragment(MenuItem item) {

        conteneur.removeAllViews();
        Fragment frag = null;


        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.accueil_btn:
                frag = main_fragment1.newInstance();
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


        // uncheck the other items.
     /*   for (int i = 0; i< mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }*/




        if (frag != null) {


             FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.add(R.id.conteneur_boutons, frag, frag.getTag());
            ft.commit();
        }
    }


}