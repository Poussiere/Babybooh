package com.poussiere.babybooh.mainFragment2;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import com.poussiere.babybooh.bdd.BddDAO;

/**
 * Created by poussiere on 2/16/16.
 */
public class YummyCursorLoader extends CursorLoader {
BddDAO maBase;

    public YummyCursorLoader(Context context, BddDAO mb) {
        super(context);
        maBase=mb;

    }

    @Override
    public Cursor loadInBackground() {
        Cursor c = maBase.getCursor();
        return c;


    }

}


