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

    // Pagination fields
    private static final String PAGE = "page";
    private static final String RESULTS = "results";
    private static final String TOTAL_RESULTS = "total_results";
    private static final String TOTAL_PAGES = "total_pages";


    public static List<Movie> parseJSON(String input) throws JSONException {
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
        builder.id(getCompulsoryIntProperty(ID, movie));
        builder.title(getStringProperty(TITLE, movie, true));
        builder.overview(getStringProperty(OVERVIEW, movie, true));
        builder.releaseDate(getStringProperty(RELEASE_DATE, movie, true));
        builder.voteAverage(getCompulsoryFloatProperty(VOTE_AVERAGE, movie));
        builder.posterPath(getStringProperty(POSTER_PATH, movie, false));
        builder.backdropPath(getStringProperty(BACKDROP_PATH, movie, false));
        builder.video(getCompulsoryBooleanProperty(VIDEO, movie));
        return builder.build();

    }

    private static String getStringProperty(String propertyName, JSONObject jsonObject, boolean compulsory) throws JSONException {
        if (jsonObject.has(propertyName)) {
            return jsonObject.getString(propertyName);
        } else if (compulsory) {

            String errorMessage = "JSONObject does not have compulsory property, propertyName = " + propertyName + ", jsonObject = " + jsonObject.toString();
            Log.e(Constants.TAG, errorMessage);
            throw new JSONException(errorMessage);
        }
        return null;
    }

    private static int getCompulsoryIntProperty(String propertyName, JSONObject jsonObject) throws JSONException {
        if (jsonObject.has(propertyName)) {
            return jsonObject.getInt(propertyName);
        }

        String errorMessage = "JSONObject does not have compulsory property, propertyName = " + propertyName + ", jsonObject = " + jsonObject.toString();
        Log.e(Constants.TAG, errorMessage);
        throw new JSONException(errorMessage);
    }

    private static float getCompulsoryFloatProperty(String propertyName, JSONObject jsonObject) throws JSONException {
        if (jsonObject.has(propertyName)) {
            return (float) jsonObject.getDouble(propertyName);
        }

        String errorMessage = "JSONObject does not have compulsory property, propertyName = " + propertyName + ", jsonObject = " + jsonObject.toString();
        Log.e(Constants.TAG, errorMessage);
        throw new JSONException(errorMessage);
    }

    private static boolean getCompulsoryBooleanProperty(String propertyName, JSONObject jsonObject) throws JSONException {
        if (jsonObject.has(propertyName)) {
            return jsonObject.getBoolean(propertyName);
        }

        String errorMessage = "JSONObject does not have compulsory property, propertyName = " + propertyName + ", jsonObject = " + jsonObject.toString();
        Log.e(Constants.TAG, errorMessage);
        throw new JSONException(errorMessage);
    }

}
