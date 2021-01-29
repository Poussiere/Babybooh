package com.poussiere.babybooh.mainFragment2;



import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poussiere.babybooh.R;
import com.poussiere.babybooh.bdd.Contract;

public class mainFragment2 extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>, CarnetRecyclerView.CarnerOnClickHandler {

    public static final int CURSOR_LOADER_ID = 42;
    private RecyclerView rc;
    private Cursor cursor;
    private CarnetRecyclerView adapter;
    private TextView noData;

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
        noData=(TextView)layoutView.findViewById(R.id.textViewNoData);
       // LinearLayoutManager lLayout = new LinearLayoutManager(getActivity());
        GridLayoutManager lLayout=new GridLayoutManager(getActivity(), 1);
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

                return new android.content.CursorLoader(getActivity(), getAllEvents, null, null, null, Contract.Evenements.COLUMN_COL3+" DESC");

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        int cursorCount;
        if (data==null)cursorCount=0;

        else{
            cursor=data;
            adapter.setEventsCursor(cursor);
            cursorCount=cursor.getCount();}

            noData.setVisibility(cursorCount > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
            adapter.setEventsCursor(null);
        }




    @Override
    public void onEventClick(int index) {
        cursor.moveToPosition(index);
        int id = cursor.getInt(Contract.Evenements.POSITION_ID);
        Uri simpleTaskRequestUri = Contract.Evenements.makeUriForSingleEvenement(id);
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.setData(simpleTaskRequestUri);
        startActivity(i);
    }
}