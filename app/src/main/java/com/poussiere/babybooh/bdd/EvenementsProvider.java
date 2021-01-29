package com.poussiere.babybooh.bdd;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class EvenementsProvider extends ContentProvider {

    private static final int EVENEMENTS = 100;
    private static final int EVENEMENTS_FOR_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private BddTableEvenement dbHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Contract.AUTHORITY, Contract.PATH_EVENEMENTS, EVENEMENTS);
        matcher.addURI(Contract.AUTHORITY, Contract.PATH_EVENEMENTS_WITH_ID, EVENEMENTS_FOR_ID) ;
        return matcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new BddTableEvenement(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor returnCursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case EVENEMENTS:
                returnCursor = db.query(
                        Contract.Evenements.NOM_DE_LA_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case EVENEMENTS_FOR_ID:
                returnCursor = db.query(
                        Contract.Evenements.NOM_DE_LA_TABLE,
                        projection,
                        Contract.Evenements._ID+ " = ?",
                        new String []{Contract.Evenements.getIdFromUri(uri)},
                        null,
                        null,
                        sortOrder
                );

                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        Context context = getContext();
        if (context != null) {
            returnCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;

        switch (uriMatcher.match(uri)) {
            case EVENEMENTS:
                db.insert(
                        Contract.Evenements.NOM_DE_LA_TABLE,
                        null,
                        values
                );
                returnUri = Contract.Evenements.URI;
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;

        if (null == selection) {
            selection = "1";
        }
        switch (uriMatcher.match(uri)) {
            case EVENEMENTS:
                rowsDeleted = db.delete(
                        Contract.Evenements.NOM_DE_LA_TABLE,
                        selection,
                        selectionArgs
                );

                break;

            case EVENEMENTS_FOR_ID:
                String id = Contract.Evenements.getIdFromUri(uri);
                rowsDeleted = db.delete(
                        Contract.Evenements.NOM_DE_LA_TABLE,
                        '"' + id + '"' + " =" + Contract.Evenements._ID,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        if (rowsDeleted != 0) {
            Context context = getContext();
            if (context != null) {
                context.getContentResolver().notifyChange(uri, null);
            }
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case EVENEMENTS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        db.insert(
                                Contract.Evenements.NOM_DE_LA_TABLE,
                                null,
                                value
                        );
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                Context context = getContext();
                if (context != null) {
                    context.getContentResolver().notifyChange(uri, null);
                }

                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }


    }
}

