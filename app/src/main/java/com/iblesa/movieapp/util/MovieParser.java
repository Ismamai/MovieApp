package com.iblesa.movieapp.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iblesa.movieapp.model.Movie;
import com.iblesa.movieapp.model.responses.MovieResponse;

import java.util.List;

/**
 * Movie parser. Converts json into list of Movies.
 */

public class MovieParser {
    public static List<Movie> parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
        return movieResponse.getMovies();
    }
}
