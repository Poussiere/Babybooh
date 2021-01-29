package com.poussiere.babybooh.mainFragment2;

import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;

import com.bumptech.glide.Glide;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.bdd.Contract;
import com.poussiere.babybooh.mainFragment3.IdMonstreActivity;

import java.util.concurrent.TimeUnit;

public class DetailActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {


    public final static int CURSOR_LOADER_SIMPLE_EVENEMENT = 64;
    private ImageView photoMonstre;
    private TextView nomMonstre;
    private TextView date;
    private TextView heure;
    private TextView duree;
    private TextView decibels;
    private TextView highestDecibels;
    private TextView luminosite;
    private TextView lux;
    private TextView interrompu;
    private Uri mUri;
    private int numMonstre = 0;
    private ShareActionProvider mShareActionProvider;
    private Intent sendIntent;

    private String mDate, mHeure, mDuree, mDecibels, mHighestDecibels, mLuminosite, mLux, mNomDuMonstre;


    private int tabNoms[] = {R.string.monstre1_nom, R.string.monstre2_nom,
            R.string.monstre3_nom, R.string.monstre4_nom, R.string.monstre5_nom,
            R.string.monstre6_nom, R.string.monstre7_nom, R.string.monstre8_nom,
            R.string.monstre9_nom, R.string.monstre10_nom, R.string.monstre11_nom,
            R.string.monstre12_nom};

    private int tabImages[] = {R.drawable.mini_monstre1, R.drawable.mini_monstre2,
            R.drawable.mini_monstre3, R.drawable.mini_monstre4, R.drawable.mini_monstre5,
            R.drawable.mini_monstre6, R.drawable.mini_monstre7, R.drawable.mini_monstre8,
            R.drawable.mini_monstre9, R.drawable.mini_monstre10, R.drawable.mini_monstre11,
            R.drawable.mini_monstre12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Uri taskUri = getIntent().getData();
        mUri = taskUri;

        photoMonstre = (ImageView) findViewById(R.id.detail_photo_id);
        nomMonstre = (TextView) findViewById(R.id.detail_nom_monstre_view);
        date = (TextView) findViewById(R.id.detail_date_view);
        heure = (TextView) findViewById(R.id.detail_heure_view);
        duree = (TextView) findViewById(R.id.detail_duree_view);
        decibels = (TextView) findViewById(R.id.detail_decibels_reveil_view);
        highestDecibels = (TextView) findViewById(R.id.detail_decibels_highest_view);
        luminosite = (TextView) findViewById(R.id.detail_luminosite_view);
        lux = (TextView) findViewById(R.id.detail_lux_view);
        interrompu = (TextView) findViewById(R.id.detail_interrompu_evenement_view);


        // couleur de la barre de statuts pour
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color_dark));


        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getLoaderManager().initLoader(CURSOR_LOADER_SIMPLE_EVENEMENT, null, this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Return to mainactivity when home button is clicked
            case android.R.id.home:
              /*  Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                */
                onBackPressed();
                return true;
            case R.id.menu_item_share:
                startActivity(Intent.createChooser(sendIntent, getString(R.string.partager)));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case CURSOR_LOADER_SIMPLE_EVENEMENT:


                return new android.content.CursorLoader(this, mUri, null, null, null, null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        //  nom, mDate, mHeure, mDuree, mDecibels, mHighestDecibels, mLuminosite, mLux;
        if (cursor != null) {
            cursor.moveToFirst();
            numMonstre = cursor.getInt(Contract.Evenements.POSITION_COL5);
            // photoMonstre.setImageResource(tabImages[numMonstre-1]);
            Glide.with(this).load(tabImages[numMonstre - 1]).into(photoMonstre);


            mNomDuMonstre = getString(tabNoms[numMonstre - 1]);
            nomMonstre.setText(mNomDuMonstre);

            long lDate = cursor.getLong(Contract.Evenements.POSITION_COL3);
            mDate = DateUtils.formatDateTime(this, lDate, DateUtils.FORMAT_SHOW_YEAR);
            date.setText(mDate);

            mHeure = DateUtils.formatDateTime(this, lDate, DateUtils.FORMAT_SHOW_TIME);
            heure.setText(mHeure);

            //Affichage de la duree : à voir s'il ne faut pas ajouter l'heure
            long lDuree = cursor.getLong(Contract.Evenements.POSITION_COL8);
            mDuree = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(lDuree),
                    TimeUnit.MILLISECONDS.toSeconds(lDuree) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lDuree))
            );

            duree.setText(mDuree);

            String dB = String.valueOf(Math.round(cursor.getDouble(Contract.Evenements.POSITION_COL2)));
            mDecibels = dB + " " + getString(R.string.db);
            decibels.setText(mDecibels);

            String dbMax = String.valueOf(Math.round(cursor.getDouble(Contract.Evenements.POSITION_COL6)));
            mHighestDecibels = dbMax + " " + getString(R.string.db);
            highestDecibels.setText(mHighestDecibels);

            int lLux = cursor.getInt(Contract.Evenements.POSITION_COL4);
            mLux = lLux + " " + getString(R.string.lux);
            mLuminosite = getmLuminosite(lLux);
            luminosite.setText(mLuminosite);
            lux.setText(" (" + mLux + ")");

            int lInterrompu = cursor.getInt(Contract.Evenements.POSITION_COL7);
            if (lInterrompu == 1) {
                interrompu.setVisibility(View.VISIBLE);
            } else interrompu.setVisibility(View.INVISIBLE);

        }

        sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        // Garçon ou fille + nom du bébé + nom du monstre.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sexe = prefs.getString("sexe", "fille");
        String nomBebe = prefs.getString("nom", "Rose");

        String messageToTheWorld = null;

        if (sexe.equals("garcon")) {
            messageToTheWorld = getString(R.string.message_to_the_world_garcon, nomBebe, mNomDuMonstre);

        } else {
            messageToTheWorld = getString(R.string.message_to_the_world_fille, nomBebe, mNomDuMonstre);
        }

        sendIntent.putExtra(Intent.EXTRA_TEXT, messageToTheWorld);
        sendIntent.setType("text/plain");
        // setShareIntent(sendIntent);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public String getmLuminosite(int lux) {
        String lum = null;
        if (lux <= 1) {
            lum = getString(R.string.noir);
        } else if (lux > 1 && lux < 11) {
            lum = getString(R.string.obscurite);
        } else if (lux > 10 && lux < 101) {
            lum = getString(R.string.lumière_faible);
        } else if (lux > 100 && lux < 401) {
            lum = getString(R.string.lumière_forte);
        } else if (lux > 400) {
            lum = getString(R.string.jour);
        }
        return lum;
    }

    public void onImageClick(View v) {
        if (numMonstre != 0) {
            Intent i = new Intent(DetailActivity.this, IdMonstreActivity.class);
            i.putExtra("idMonstre", numMonstre);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Return true to display menu
        return true;
    }
}

