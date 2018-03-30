package com.iblesa.movieapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Database contract for Movie
 */
public class MovieContract {


    // Authority for this ContentProvider
    public static final String AUTHORITY = "com.iblesa.movieapp";
    // BaseContentUri for this provider
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Valid paths supported by this Content Provider
    static final String PATH_FAVORITES = "favorites";
    /**
     * Constructor should never be invoked
     */
    private MovieContract() {
    }

    public static class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_ADDED = "added";

    }
}
