package com.iblesa.movieapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.R;
import com.iblesa.movieapp.data.MovieContract;
import com.iblesa.movieapp.data.MovieDBHelper;
import com.iblesa.movieapp.model.Movie;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Fake data, normally for debugging
 */

public class FakeMoviesData {

    public static List<Movie> getFakeMoviesData(Context context) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = MovieParser.parseJSON(context.getString(R.string.movies_result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static void populateFakeData(SQLiteDatabase db, List<Movie> movies) {
        if (db == null || movies == null) {
            return;
        }

        List<ContentValues> entries = new ArrayList<>();

        for (Movie movie : movies) {
            ContentValues cv = getContentValues(movie);
            entries.add(cv);
        }

        try {
            db.beginTransaction();
            db.delete(MovieContract.MovieEntry.TABLE_NAME, null, null);

            for (ContentValues entry : entries) {
                db.insert(MovieContract.MovieEntry.TABLE_NAME, null, entry);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e(Constants.TAG, "Error inserting fake data", e);
        } finally {
            db.endTransaction();
        }
    }

    @NonNull
    private static ContentValues getContentValues(Movie movie) {
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
