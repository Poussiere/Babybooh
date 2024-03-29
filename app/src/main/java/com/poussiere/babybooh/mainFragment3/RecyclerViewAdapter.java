package com.poussiere.babybooh.mainFragment3;


        import android.content.Context;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;
        import com.poussiere.babybooh.R;
        import com.poussiere.babybooh.objets.Monstre;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    String monstreSexy = null; //car en String
    SharedPreferences prefs=null;
    Boolean monstreDebloque=null;

    private int tabNoms [] = {R.string.monstre1_nom, R.string.monstre2_nom,
            R.string.monstre3_nom, R.string.monstre4_nom, R.string.monstre5_nom,
            R.string.monstre6_nom, R.string.monstre7_nom, R.string.monstre8_nom,
            R.string.monstre9_nom, R.string.monstre10_nom, R.string.monstre11_nom,
            R.string.monstre12_nom };

    private int tabImages[] = {R.drawable.mini_monstre1, R.drawable.mini_monstre2,
            R.drawable.mini_monstre3, R.drawable.mini_monstre4, R.drawable.mini_monstre5,
            R.drawable.mini_monstre6, R.drawable.mini_monstre7, R.drawable.mini_monstre8,
            R.drawable.mini_monstre9, R.drawable.mini_monstre10, R.drawable.mini_monstre11,
            R.drawable.mini_monstre12 };

    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

      monstreSexy= Monstre.quelMonstreString(position+1);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
      monstreDebloque=prefs.getBoolean(monstreSexy, false);

        if (monstreDebloque)
        { holder.petitNom.setText(context.getText(tabNoms[position]));
        //holder.petitePhoto.setImageResource(tabImages[position]);
        Glide.with(context).load(tabImages[position]).into(holder.petitePhoto);}

        else
        {holder.petitNom.setText(context.getText(R.string.inconnu));
          //  holder.petitePhoto.setImageResource(R.drawable.interrogationbig);
               Glide.with(context).load(R.drawable.interrogationbig).into(holder.petitePhoto);
        }

    }

    @Override
    public int getItemCount() {
        return tabImages.length;
    }

}
