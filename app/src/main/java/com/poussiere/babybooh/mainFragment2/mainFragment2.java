package com.poussiere.babybooh.mainFragment2;



import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.poussiere.babybooh.R;
import com.poussiere.babybooh.annexes.EnregistrerActivity;
import com.poussiere.babybooh.bdd.Contract;

public class mainFragment2 extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>, CarnetRecyclerView.CarnerOnClickHandler {

    public static final int CURSOR_LOADER_ID = 42;
    private RecyclerView rc;
    private Cursor cursor;
    private CarnetRecyclerView adapter;

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
        rc=(RecyclerView)layoutView.findViewById(R.id.lvEvenements);
        LinearLayoutManager lLayout = new LinearLayoutManager(getActivity());
        lLayout.setReverseLayout(true);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(lLayout);
        adapter = new CarnetRecyclerView(getActivity(), this);
        rc.setAdapter(adapter);
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);



    return layoutView;}


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
    {super.onPause();}

    @Override
    public void onResume()
    {super.onResume();}


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case CURSOR_LOADER_ID:

                Uri getAllEvents = Contract.Evenements.URI;

                return new android.content.CursorLoader(getActivity(), getAllEvents, null, null, null, null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null){
            cursor=data;
            adapter.setEventsCursor(cursor);
    }}

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
            adapter.setEventsCursor(null);
        }



   /* @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (data != null){
        cursor=data;
        adapter.setEventsCursor(cursor);}

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adapter.setEventsCursor(null);

    }
*/

    @Override
    public void onEventClick(int index) {

    }
}
/*
    protected ListView lv;
    protected MyCursorAdapter ad;
    private TextView emptyView;


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

        lv = (ListView)layoutView.findViewById(R.id.lvEvenements);
        emptyView=(TextView)layoutView.findViewById((R.id.empty_view));
        ad = new MyCursorAdapter(getActivity(), null, 1);

        lv.setEmptyView(emptyView);
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
    {super.onPause();}

    @Override
    public void onResume()
    {super.onResume();}

    //méthodes du cursorLoader

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new YummyCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        ad.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        ad.swapCursor(null);
    }


*/
