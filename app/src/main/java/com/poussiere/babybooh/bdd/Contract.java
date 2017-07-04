package com.poussiere.babybooh.bdd;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by poussiere on 26/06/17.
 */

public final class Contract {

    static final String AUTHORITY = "com.poussiere.babybooh";
    static final String PATH_EVENEMENTS = "evenements";
    static final String PATH_EVENEMENTS_WITH_ID= "evenements/*";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private Contract() {
    }

    public static final class Evenements implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_EVENEMENTS).build();
        public static final String COLUMN_COL2 = "decibels"; //decibels du cri déclencheur initial
        public static final String COLUMN_COL3 = "date";
        public static final String COLUMN_COL4 = "lumiere";
        public static final String COLUMN_COL5 = "monstre";
        public static final String COLUMN_COL6 = "highestDecibels"; //valeur du cri le plus fort de l'évenement
        public static final String COLUMN_COL7 = "evenementInterrompu"; // 1 si l'évenement a été interrompu par l'utilisateur, 0 sinon
        publci static final String COMUMN_COL8 = "duree"; //indique la durée totale du réveil avant rendormissement ou arrêt utilisateur
        
        public static final int POSITION_ID = 0;
        public static final int POSITION_COL2 = 1;
        public static final int POSITION_COL3= 2;
        public static final int POSITION_COL4 = 3;
        public static final int POSITION_COL5 = 4;
        public static final int POSITION_COL6 = 5;
        public static final int POSITION_COL7 = 6;
        public static final int POSITION_COL8 = 7;
       
       /* public static final ImmutableList<String> EVENEMENT_COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_COL2,
                COLUMN_COL3,
                COLUMN_COL4,
                COLUMN_COL5
        );
        */

        static final String NOM_DE_LA_TABLE="Evenements";

        public static Uri makeUriForSingleEvenement(int id) {
            return URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        static String getIdFromUri(Uri queryUri) {

            return queryUri.getLastPathSegment();
        }


    }

}

