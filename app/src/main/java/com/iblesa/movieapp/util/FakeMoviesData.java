package com.iblesa.movieapp.util;

import android.content.Context;

import com.iblesa.movieapp.R;
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
}
