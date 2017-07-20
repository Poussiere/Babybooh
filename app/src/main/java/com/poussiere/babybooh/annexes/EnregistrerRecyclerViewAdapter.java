package com.poussiere.babybooh.annexes;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
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





    String nomDuSon, nomDuSonSelectionne,nomDuSonCourt;
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
        
        
        holder.playImage.setImageResource(android.R.drawable.ic_media_play)
        String cheminFichier = context.getExternalFilesDir(null).getAbsolutePath() + "/babyboohSongs/";
        File mCurentFile = new File(cheminFichier);
        File[] fichiersSons = mCurentFile.listFiles();

        int white = ContextCompat.getColor(context, R.color.white);
        int black = ContextCompat.getColor(context, R.color.black);

        nomDuSon = fichiersSons[position].getName();
        // On supprime les 4 derniers caractères pour ne pas afficher l'extension des fichiers à l'écran
        nomDuSonCourt= nomDuSon.substring(0, nomDuSon.length() - 5);
        holder.sonNomTx.setText(nomDuSonCourt);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        nomDuSonSelectionne = prefs.getString("nomDuSon", "anicouni.3gpp"); // Le son selectionné par défaut est messagePourBebe.mp4

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
        private Thread background, background2;
        private SharedPreferences prefs = null;
        private String toasty;
        private Handler handler;
        private AudioManager manager;


        public EnrRecyclerViewHolders(View itemView) {
            super(itemView);
            sonNomTx = (TextView)itemView.findViewById(R.id.son_nom_tv);
            playImage=(ImageView)itemView.findViewById(R.id.play_image);
            conteneur=(View)itemView.findViewById(R.id.card_view_enr_cont);
            lecture=new Lecture(context);
            
            //Pour remettre le bouton play une fois que la lecture d'un son est finie
            handler = new Handler (context.getMainLooper()) {
                public void handleMessage(Message msg){
                super.handleMessage(msg);
                playImage.setImageResource(android.R.drawable.ic_media_pause);
                 }
            };
            
            sonNomTx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nomDuSon = sonNomTx.getText().toString();


                    Context context = view.getContext();
                    Resources res = context.getResources();

                    prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    prefs.edit().putString("nomDuSon", nomDuSon+".3gpp").apply();



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

                    
                    if (!lecture.isRunning()){

                  manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            if(manager.isMusicActive())
                {      
                        
                    background = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            nomDuSon = sonNomTx.getText().toString()+".3gpp";
                            lecture.lire(nomDuSon);
                            lecture.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    lecture.stop();
                                    handler.sendEmptyMessage(0);
                                    
                                }

                            });
                        }
                    });
                        playImage.setImageResource(android.R.drawable.ic_media_pause);
                    background.start();
            } // fin du if audiomanger...
                        else {Toast.makeText(context, R.string.son_en_cours, Toast.LENGTH_LONG);
                    
                    
                    }
                        else{
                              background2 = new Thread(new Runnable() {
                        @Override
                        public void run() {

                         
                            lecture.stop();
                            
                        }
                    });
                        playImage.setImageResource(android.R.drawable.ic_media_play);
                    background2.start();}
                        }



            });


        }
        public String getNomDuSon ()
        {return nomDuSon;}

        public void refresh()
        {notifyDataSetChanged();}
    }

}

