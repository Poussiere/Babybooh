package com.poussiere.babybooh.mainFragment3;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.poussiere.babybooh.R;


public class TableauDeChasseActivity extends AppCompatActivity {

    private GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableau_de_chasse);

        //Toolbar :
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar4);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setTitle(R.string.titre_activite3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        lLayout = new GridLayoutManager(TableauDeChasseActivity.this, 3);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view_tableau);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);


        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(TableauDeChasseActivity.this);
        rView.setAdapter(rcAdapter);

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
