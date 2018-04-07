package com.iblesa.movieapp.util;

import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Movie parser. Converts json into list of Movies.
 */

public class MovieParser {
    // Movie fields
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String VIDEO = "video";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String POPULARITY = "popularity";

    // Pagination fields
    private static final String PAGE = "page";
    private static final String RESULTS = "results";
    private static final String TOTAL_RESULTS = "total_results";
    private static final String TOTAL_PAGES = "total_pages";


    public static List<Movie> parseMovieJSON(String input) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(input);

        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject element = jsonArray.getJSONObject(i);
            try {
                movies.add(parseMovie(element));
            } catch (JSONException e) {
                Log.e(Constants.TAG, "Error parsing movie ", e);
            }

        }

        return movies;
    }


    private static Movie parseMovie(JSONObject movie) throws JSONException {
        Movie.Builder builder = new Movie.Builder();
        builder.id(Parser.getCompulsoryIntProperty(ID, movie));
        builder.title(Parser.getStringProperty(TITLE, movie, true));
        builder.overview(Parser.getStringProperty(OVERVIEW, movie, true));
        builder.releaseDate(Parser.getStringProperty(RELEASE_DATE, movie, true));
        builder.voteAverage(Parser.getCompulsoryFloatProperty(VOTE_AVERAGE, movie));
        builder.popularity(Parser.getCompulsoryFloatProperty(POPULARITY, movie));
        builder.posterPath(Parser.getStringProperty(POSTER_PATH, movie, false));
        builder.backdropPath(Parser.getStringProperty(BACKDROP_PATH, movie, false));
        builder.video(Parser.getCompulsoryBooleanProperty(VIDEO, movie));
        return builder.build();

    }


}
