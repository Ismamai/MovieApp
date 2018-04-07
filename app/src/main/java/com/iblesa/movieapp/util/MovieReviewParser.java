package com.iblesa.movieapp.util;

import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.model.MovieReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * MovieReview parser. Converts json into list of Movies.
 */

public class MovieReviewParser {
    // Movie fields
    private static final String ID = "id";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String URL = "url";

    // Pagination fields
    private static final String PAGE = "page";
    private static final String RESULTS = "results";
    private static final String TOTAL_RESULTS = "total_results";
    private static final String TOTAL_PAGES = "total_pages";


    public static List<MovieReview> parseMovieReviewJSON(String input) throws JSONException {
        List<MovieReview> reviews = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(input);

        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject element = jsonArray.getJSONObject(i);
            try {
                reviews.add(parseMovieReview(element));
            } catch (JSONException e) {
                Log.e(Constants.TAG, "Error parsing movie ", e);
            }

        }
        return reviews;
    }

    private static MovieReview parseMovieReview(JSONObject movieReview) throws JSONException {
        MovieReview.Builder builder = new MovieReview.Builder();
        builder.id(Parser.getStringProperty(ID, movieReview, true));
        builder.author(Parser.getStringProperty(AUTHOR, movieReview, true));
        builder.content(Parser.getStringProperty(CONTENT, movieReview, true));
        builder.url(Parser.getStringProperty(URL, movieReview, true));
        return builder.build();

    }

}
