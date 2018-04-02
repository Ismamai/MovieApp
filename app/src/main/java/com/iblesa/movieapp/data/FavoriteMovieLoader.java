package com.iblesa.movieapp.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieLoader extends android.support.v4.content.AsyncTaskLoader<List<Movie>> {

    public FavoriteMovieLoader(Context context) {
        super(context);
    }

    @Override
    public List<Movie> loadInBackground() {

        Cursor cursor = getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null, null, null, MovieContract.MovieEntry.COLUMN_ADDED);
        List<Movie> retMovies = null;
        if (cursor != null) {
            retMovies = new ArrayList<>();
            while (cursor.moveToNext()) {
                retMovies.add(MovieUtils.getMovie(cursor));
            }
        }
        return retMovies;
    }

    @Override
    protected void onStartLoading() {
        Log.d(Constants.TAG, "FavoriteMovieLoader onStartLoading forcing load");
        forceLoad();
    }
}
