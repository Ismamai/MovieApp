package com.iblesa.movieapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.R;
import com.iblesa.movieapp.data.MovieContract;
import com.iblesa.movieapp.data.MovieUtils;
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
            movies = MovieParser.parseMovieJSON(context.getString(R.string.movies_result));
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
            ContentValues cv = MovieUtils.getContentValues(movie);
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


}
