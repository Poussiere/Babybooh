package com.poussiere.babybooh.mainFragment3;


        import android.content.Context;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

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
        holder.petitePhoto.setImageResource(tabImages[position]);}

        else
        {holder.petitNom.setText(context.getText(R.string.inconnu));
            holder.petitePhoto.setImageResource(R.drawable.interrogationbig);

        }

    }

    @Override
    public int getItemCount() {
        return tabImages.length;
    }




}