package com.poussiere.babybooh.annexes;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poussiere.babybooh.R;

import java.io.File;

public class EnregistrerRecyclerViewAdapter extends RecyclerView.Adapter<EnregistrerRecyclerViewHolders> {



    String cheminFichier =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/babyboohSongs/";
    File mCurentFile =  new File(cheminFichier);
    File[]fichiersSons= mCurentFile.listFiles();

    private Context context;

    public EnregistrerRecyclerViewAdapter(Context context) {
        this.context = context;
    }



    @Override
    public EnregistrerRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_enregistrer, null);
        EnregistrerRecyclerViewHolders ercv = new EnregistrerRecyclerViewHolders(layoutView);
        return ercv;
    }

    @Override
    public void onBindViewHolder(EnregistrerRecyclerViewHolders holder, int position) {

       // cheminFichier = Environment.getExternalStorageDirectory().getAbsolutePath() ;
       // cheminFichier += "/babyboohSongs/" ;
      //  mCurentFile =  new File(cheminFichier);
       // fichiersSons = mCurentFile.listFiles();
       holder.sonNom.setText(fichiersSons[position].getName());

    }

    @Override
    public int getItemCount() {
        if (fichiersSons != null) return fichiersSons.length;
        else return 0;
    }

}

