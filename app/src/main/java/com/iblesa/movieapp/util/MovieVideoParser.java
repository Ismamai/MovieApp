package com.iblesa.movieapp.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iblesa.movieapp.model.MovieVideo;
import com.iblesa.movieapp.model.responses.MovieVideosResponse;

import java.util.List;

/**
 * MovieReview parser. Converts json into list of Movies.
 */

public class MovieVideoParser {

    public static List<MovieVideo> parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieVideosResponse movieVideosResponse = gson.fromJson(response, MovieVideosResponse.class);
        return movieVideosResponse.getMovieVideos();
    }

}
