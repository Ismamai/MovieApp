package com.iblesa.movieapp.data;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.iblesa.movieapp.model.Movie;

public class MovieUtils {

    @NonNull
    public static ContentValues getContentValues(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry._ID, movie.getId());
        cv.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        cv.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        cv.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        cv.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdrop_path());
        cv.put(MovieContract.MovieEntry.COLUMN_VIDEO, movie.isVideo() ? 1 : 0);
        return cv;
    }
}
