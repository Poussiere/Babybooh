package com.poussiere.babybooh.mainFragment3;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.poussiere.babybooh.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView petitNom;
    public ImageView petitePhoto;
    private final Context context;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        context=itemView.getContext();
        itemView.setOnClickListener(this);
        petitNom = (TextView)itemView.findViewById(R.id.nom_gallerie);
        petitePhoto = (ImageView)itemView.findViewById(R.id.petite_photo_noeuxpap);





    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(context, IdMonstreActivity.class);
        i.putExtra("idMonstre", getAdapterPosition()+1);
        context.startActivity(i);
    }
}