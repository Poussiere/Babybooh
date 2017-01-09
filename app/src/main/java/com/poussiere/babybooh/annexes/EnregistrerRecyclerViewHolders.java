package com.poussiere.babybooh.annexes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.poussiere.babybooh.R;
import com.poussiere.babybooh.objets.Lecture;

/**
 La lecture du son fonctionne lorsque l'on clique sur un nom. Il faudrait faire en sorte qu'on ne puisse pas lancer 2 lecture en meme temps
 +Ajouter le highlight au moment du clique. Bisous à plus.
 */
/*public class EnregistrerRecyclerViewHolders extends RecyclerView.ViewHolder {

    public TextView sonNomTx ;
    public View conteneur;
    public ImageView playImage;
    public String nomDuSon;
    private Lecture lecture ;
    private Thread background;
    SharedPreferences prefs = null;
    String toasty;


    public EnregistrerRecyclerViewHolders(View itemView) {
        super(itemView);
        sonNomTx = (TextView)itemView.findViewById(R.id.son_nom_tv);
        playImage=(ImageView)itemView.findViewById(R.id.play_image);
        conteneur=(View)itemView.findViewById(R.id.card_view_enr_cont);
        lecture=new Lecture();

        sonNomTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomDuSon = sonNomTx.getText().toString();


                Context context = view.getContext();
                Resources res = context.getResources();

                prefs = PreferenceManager.getDefaultSharedPreferences(context);
                prefs.edit().putString("nomDuSon", nomDuSon).apply();



                toasty=nomDuSon+" "+res.getString(R.string.sonSelection);

              //  toasty= res.getString(R.string.sonSelection, nomDuSon);

                Toast.makeText(context,toasty, Toast.LENGTH_SHORT ).show();
              //  conteneur.setSelected(true);


                    conteneur.setSelected(true);



            }
        });

     playImage.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {



                                             background = new Thread(new Runnable() {
                                                 @Override
                                                 public void run() {

                                                     nomDuSon = sonNomTx.getText().toString();
                                                     lecture.lire(nomDuSon);
                                                     lecture.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                         public void onCompletion(MediaPlayer mp) {
                                                             lecture.stop();

                                                         }

                                                     });
                                                 }
                                             });
                                             background.start();}



                                     });


}
    public String getNomDuSon ()
    {return nomDuSon;}
}
*/