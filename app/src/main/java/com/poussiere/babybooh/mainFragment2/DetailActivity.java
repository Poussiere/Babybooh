package com.poussiere.babybooh.mainFragment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.poussiere.babybooh.R;

public class DetailActivity extends AppCompatActivity {
    
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        
       photoMonstre=(ImageView)findViewById(R.id.detail_photo_id);
       nomMonstre = (TextView)findViewById(R.id.detail_nom_monstre_view);
       date=(TextView)findViewById(R.id.detail_date_view);
       heure = (TextView)findViewById(R.id.detail_heure_view);
       duree = (TextView)findViewById(R.id.detail_duree_view);
       decibels = (TextView)findViewById(R.id.detail_decibels_reveil_view);
       highestDecibels = (TextView)findViewById(R.id.detail_decibels_highest_view);
       luminosite = (TextView)findViewById(detail_luminosite_view);
       lux = (TextView)findViewById(R.id.detail_lux_view);
       interrompu = (TextView)findViewById(R.id.detail_interrompu_evenement_view);
        
        // couleur de la barre de statuts pour Lolipo et +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color_dark));
        }
        //////////////////////////////////////////////////////////////


        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Ici on va faire une synthèse de l"évenement sur une période donnée : combien de cris en 5 minutes; decibels les plus élevés...

        /*

        String.format("%02d min, %02d sec",
    TimeUnit.MILLISECONDS.toMinutes(millis),
    TimeUnit.MILLISECONDS.toSeconds(millis) -
    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
);

         */
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
        }
        return super.onOptionsItemSelected(item);
    }
}
