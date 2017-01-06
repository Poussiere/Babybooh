package com.poussiere.babybooh.mainFragment2;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.poussiere.babybooh.R;
import com.poussiere.babybooh.mainFragment3.IdMonstreActivity;
import com.poussiere.babybooh.bdd.BddDAO;


public class StatsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    public static String ACT_NAME = "StatsActivity";
    private Intent i;
    private BddDAO maBase;
    protected ListView lv;
    protected MyCursorAdapter ad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //Toolbar :
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar3);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setTitle(R.string.titre_activite2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        maBase = new BddDAO(this);
        maBase.open();
        i=new Intent (StatsActivity.this, IdMonstreActivity.class);
        lv = (ListView)findViewById(R.id.lvEvenements);
        ad = new MyCursorAdapter(this, null, 1);
        lv.setAdapter(ad);

        getLoaderManager().initLoader(1, null, this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long p) {

                Cursor c = (Cursor) adapter.getItemAtPosition(position);
                int m = c.getInt(4); //id du monstre. Si nécessaire on pourra envoyer d'autres valeurs tels que les decibels ou les lux afin de compléter la fiche d'identité du monstre
                i.putExtra("idMonstre", m); //transfert de l'identifiant du monstre
                startActivity(i);
                //lancer intent idMonstre activity et mettre le cursor dans un extra
            }


        });


    }


    @Override
    public void onPause()
    {   maBase.close();
        super.onPause();}

    @Override
    public void onResume()
    { maBase.open();
        super.onResume();}

    //méthodes du cursorLoader

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new YummyCursorLoader(this, maBase);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        ad.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        ad.swapCursor(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }

}
