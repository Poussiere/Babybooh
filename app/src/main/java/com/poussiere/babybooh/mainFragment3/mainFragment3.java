package com.poussiere.babybooh.mainFragment3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poussiere.babybooh.R;


public class mainFragment3 extends Fragment {


    private GridLayoutManager lLayout;

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

        lLayout = new GridLayoutManager(getActivity(), 3);

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


}
