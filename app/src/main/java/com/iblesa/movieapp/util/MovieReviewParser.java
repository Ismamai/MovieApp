package com.iblesa.movieapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iblesa.movieapp.model.MovieReview;
import com.iblesa.movieapp.model.responses.MovieReviewResponse;

import java.util.List;

public class MovieReviewParser {

    public static List<MovieReview> parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieReviewResponse movieReviewResponse = gson.fromJson(response, MovieReviewResponse.class);
        return movieReviewResponse.getMovieReviews();
    }

}
