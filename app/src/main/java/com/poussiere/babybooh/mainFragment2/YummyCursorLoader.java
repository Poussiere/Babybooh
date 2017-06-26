package com.poussiere.babybooh.mainFragment2;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import com.poussiere.babybooh.bdd.Contract;

/**
 * Created by poussiere on 2/16/16.
 */
public class YummyCursorLoader extends CursorLoader {

Context c;

    public YummyCursorLoader(Context context) {
        super(context);
        c=context;


    }

    @Override
    public Cursor loadInBackground() {
        Cursor c = getContext().getContentResolver().query(Contract.Evenements.URI, null, null, null, null);
        return c;


    }

}


