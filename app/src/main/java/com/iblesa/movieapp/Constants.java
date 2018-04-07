package com.iblesa.movieapp;

/**
 * Constants class
 */

public class Constants {
    public static final String TAG = "iblesa_app";
    public static final String MOVIES = "movies_list";
    public static final String MOVIE_DETAIL = "movie_detail";

    static final String MOVIE_API_KEY = "API_KEY";

    // LoaderMovie constants
    static final int LOADER_MOVIE_KEY = 30;
    static final String LOADER_MOVIE_PARAM_SORT_CRITERIA = "SORT_CRITERIA";
    static final String LOADER_MOVIE_PARAM_API_KEY = MOVIE_API_KEY;

    private static final String MOVIE_ID = "MOVIE_ID";
    // LoaderMovieReview constants
    static final int LOADER_MOVIE_REVIEW_KEY = 40;
    static final String LOADER_MOVIE_REVIEW_PARAM_MOVIE_ID = MOVIE_ID;

    // LoaderMovieReview constants
    static final int LOADER_MOVIE_VIDEOS_KEY = 50;
    static final String LOADER_MOVIE_VIDEOS_PARAM_MOVIE_ID = MOVIE_ID;

}
