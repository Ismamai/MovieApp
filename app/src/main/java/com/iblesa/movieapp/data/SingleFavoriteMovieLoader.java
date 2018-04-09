package com.iblesa.movieapp.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SingleFavoriteMovieLoader extends android.support.v4.content.AsyncTaskLoader<Movie> {

    private final int movieId;

    public SingleFavoriteMovieLoader(Context context, int movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    public Movie loadInBackground() {
        Uri contentUri = MovieContract.MovieEntry.CONTENT_URI;
        contentUri = contentUri.buildUpon().appendPath(Integer.toString(movieId)).build();
        Cursor cursor = getContext().getContentResolver()
                .query(contentUri,
                        null,
                        null,
                        null,
                        MovieContract.MovieEntry.COLUMN_ADDED);
        Movie retMovies = null;
        if (cursor != null) {
            if (cursor.moveToNext()) {
                retMovies = MovieUtils.getMovie(cursor);
            }
            cursor.close();
        }
        return retMovies;
    }

    @Override
    protected void onStartLoading() {
        Log.d(Constants.TAG, "FavoriteMovieLoader onStartLoading forcing load");
        forceLoad();
    }
}
