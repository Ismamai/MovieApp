package com.iblesa.movieapp.data;

import android.provider.BaseColumns;

/**
 * Database contract for Movie
 */
public class MovieContract {
    /**
     * Constructor should never be invoked
     */
    private MovieContract() {
    }

    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    }
}
