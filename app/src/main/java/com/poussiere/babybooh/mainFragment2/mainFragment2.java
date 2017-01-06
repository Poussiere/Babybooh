package com.poussiere.babybooh.mainFragment2;


import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.widget.CursorAdapter;



import android.content.Context;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.poussiere.babybooh.MainActivity;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.bdd.BddDAO;

public class mainFragment2 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private BddDAO maBase;
    protected ListView lv;
    protected MyCursorAdapter ad;


    public mainFragment2() {
        // Required empty public constructor
    }



    public static mainFragment2 newInstance() {
        mainFragment2 fragment = new mainFragment2();
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
        View layoutView= inflater.inflate(R.layout.fragment_main2, container, false);

        maBase = new BddDAO(getActivity());
        maBase.open();
        lv = (ListView)layoutView.findViewById(R.id.lvEvenements);
        ad = new MyCursorAdapter(getActivity(), null, 1);
        lv.setAdapter(ad);

        getLoaderManager().initLoader(1, null, this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long p) {

                Cursor c = (Cursor) adapter.getItemAtPosition(position);
                int m = c.getInt(4); //id du monstre. Si nécessaire on pourra envoyer d'autres valeurs tels que les decibels ou les lux afin de compléter la fiche d'identité du monstre

                //lancer intent idMonstre activity et mettre le cursor dans un extra
            }


        });


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


    @Override
    public void onPause()
    {   maBase.close();
        super.onPause();}

    @Override
    public void onResume()
    { maBase.open();
        super.onResume();}

    //méthodes du cursorLoader

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new YummyCursorLoader(getActivity(), maBase);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        ad.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        ad.swapCursor(null);
    }


}
