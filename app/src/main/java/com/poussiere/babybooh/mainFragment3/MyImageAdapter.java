package com.poussiere.babybooh.mainFragment3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.poussiere.babybooh.R;

/**
 * Created by poussiere on 2/20/16.
 *
 * Adapter pour la gallerie du tableau de chasse
 */
public class MyImageAdapter extends BaseAdapter {

    public int tabImages [] = {R.drawable.monstre1, R.drawable.monstre2,
            R.drawable.monstre3, R.drawable.monstre4, R.drawable.monstre5,
            R.drawable.monstre6, R.drawable.monstre7, R.drawable.monstre8,
            R.drawable.monstre9, R.drawable.monstre10, R.drawable.monstre11,
            R.drawable.monstre12 };

    private Context contextBis;
    private ImageView imageView;

    public MyImageAdapter (Context context)
    {contextBis=context;
    }


    @Override
    public int getCount() {
        return tabImages.length;
    }

    @Override
    public Object getItem(int position) {
        return tabImages[position-1];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        imageView = new ImageView(contextBis);
        imageView.setImageResource(tabImages[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(new GridView.LayoutParams(params));

        return imageView;
    }

}
