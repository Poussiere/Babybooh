package com.poussiere.babybooh.annexes;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.poussiere.babybooh.R;
import com.poussiere.babybooh.mainFragment3.RecyclerViewHolders;
import com.poussiere.babybooh.objets.Lecture;

import java.io.File;

import static java.security.AccessController.getContext;

public class EnregistrerRecyclerViewAdapter extends RecyclerView.Adapter<EnregistrerRecyclerViewAdapter.EnrRecyclerViewHolders> {





    String nomDuSon, nomDuSonSelectionne;
    private Context context;




    public EnregistrerRecyclerViewAdapter(Context context)

    {
        this.context = context;

    }


    @Override
    public EnrRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_enregistrer, null);
        EnrRecyclerViewHolders ercv = new EnrRecyclerViewHolders(layoutView);


        return ercv;
    }

    @Override
    public void onBindViewHolder(EnrRecyclerViewHolders holder, int position) {

        // cheminFichier = Environment.getExternalStorageDirectory().getAbsolutePath() ;
        // cheminFichier += "/babyboohSongs/" ;
        //  mCurentFile =  new File(cheminFichier);
        // fichiersSons = mCurentFile.listFiles();

        String cheminFichier = context.getExternalFilesDir(null).getAbsolutePath() + "/babyboohSongs/";
        File mCurentFile = new File(cheminFichier);
        File[] fichiersSons = mCurentFile.listFiles();

        int white = ContextCompat.getColor(context, R.color.white);
        int black = ContextCompat.getColor(context, R.color.black);

        nomDuSon = fichiersSons[position].getName();
        // On supprime les 4 derniers caractères pour ne pas afficher l'extension des fichiers à l'écran
        nomDuSon.substring(0, nomDuSon.length() - 4);
        holder.sonNomTx.setText(nomDuSon);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        nomDuSonSelectionne = prefs.getString("nomDuSon", "messagePourBebe.3gpp"); // Le son selectionné par défaut est messagePourBebe.mp4

        if (nomDuSon.equals(nomDuSonSelectionne)) {
            holder.conteneur.setSelected(true);

            holder.sonNomTx.setTextColor(white);
            holder.getAdapterPosition();
        } else {
            holder.conteneur.setSelected(false);
            holder.sonNomTx.setTextColor(black);
        }





    }


    @Override
    public int getItemCount() {
        String cheminFichier = context.getExternalFilesDir(null).getAbsolutePath() + "/babyboohSongs/";
        File mCurentFile = new File(cheminFichier);
        File[] fichiersSons = mCurentFile.listFiles();

        if (fichiersSons != null) return fichiersSons.length;
        else return 0;
    }
    public class EnrRecyclerViewHolders extends RecyclerView.ViewHolder {

        public TextView sonNomTx ;
        public View conteneur;
        public ImageView playImage;
        public String nomDuSon;
        private Lecture lecture ;
        private Thread background;
        SharedPreferences prefs = null;
        String toasty;


        public EnrRecyclerViewHolders(View itemView) {
            super(itemView);
            sonNomTx = (TextView)itemView.findViewById(R.id.son_nom_tv);
            playImage=(ImageView)itemView.findViewById(R.id.play_image);
            conteneur=(View)itemView.findViewById(R.id.card_view_enr_cont);
            lecture=new Lecture(context);

            sonNomTx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nomDuSon = sonNomTx.getText().toString()+"3gpp";


                    Context context = view.getContext();
                    Resources res = context.getResources();

                    prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    prefs.edit().putString("nomDuSon", nomDuSon).apply();



                    toasty=nomDuSon+" "+res.getString(R.string.sonSelection);

                    //  toasty= res.getString(R.string.sonSelection, nomDuSon);

                    Toast.makeText(context,toasty, Toast.LENGTH_SHORT ).show();
                    //  conteneur.setSelected(true);


                    conteneur.setSelected(true);
                    notifyDataSetChanged();


                }
            });

            playImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    background = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            nomDuSon = sonNomTx.getText().toString()+"3gpp";
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

        public void refresh()
        {notifyDataSetChanged();}
    }

}

