package com.poussiere.babybooh.mainFragment3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poussiere.babybooh.R;


public class mainFragment3 extends Fragment {



    public mainFragment3() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static mainFragment3 newInstance() {
        mainFragment3 fragment = new mainFragment3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layoutView= inflater.inflate(R.layout.fragment_main_3, container, false);

       // GridLayoutManager lLayout = new GridLayoutManager(getActivity(), 3);
        GridLayoutManager  lLayout = new GridLayoutManager(getActivity(), calculateNoOfColumns(getActivity().getBaseContext())); // 2 = number of items on each row

        RecyclerView rView = (RecyclerView)layoutView.findViewById(R.id.recycler_view_tableau);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);


        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(getActivity());
        rView.setAdapter(rcAdapter);

        return layoutView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //Une méthode pour ajuster directement la taille vignette avec 2 vignettes par ligne
     public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
       
         int noOfColumns;
        // If we are on tablet, the posters we'll appeaR bigger
        if (dpWidth>=600)
        { noOfColumns = (int) (dpWidth / 400);}
      
         else 
         {noOfColumns = (int) (dpWidth / 130);}
        return noOfColumns;
    }
}
