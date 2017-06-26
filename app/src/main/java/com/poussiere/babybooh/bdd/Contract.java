package com.poussiere.babybooh.bdd;

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
        public static final String COLUMN_COL2 = "decibels";
        public static final String COLUMN_COL3 = "date";
        public static final String COLUMN_COL4 = "lumiere";
        public static final String COLUMN_COL5 = "monstre";
        public static final int POSITION_ID = 0;
        public static final int POSITION_COL2 = 1;
        public static final int POSITION_COL3= 2;
        public static final int POSITION_COL4 = 3;
        public static final int POSITION_COL5 = 4;
       
        public static final ImmutableList<String> EVENEMENT_COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_COL2,
                COLUMN_COL3,
                COLUMN_COL4,
                COLUMN_COL5
        );
        static final String NOM_DE_LA_TABLE="Evenements";

        public static Uri makeUriForSingleEvenement(int id) {
            return URI.buildUpon().appendPath(id).build();
        }

        static String getIdFromUri(Uri queryUri) {
            return queryUri.getLastPathSegment();
        }


    }

}

