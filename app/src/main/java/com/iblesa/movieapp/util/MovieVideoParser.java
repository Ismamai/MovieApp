package com.iblesa.movieapp.util;

import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.model.MovieVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * MovieReview parser. Converts json into list of Movies.
 */

public class MovieVideoParser {
    // MovieVideo fields
    private static final String ID = "id";
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String SITE = "site";
    private static final String SIZE = "size";
    private static final String TYPE = "type";

    // Pagination fields
    private static final String PAGE = "page";
    private static final String RESULTS = "results";
    private static final String TOTAL_RESULTS = "total_results";
    private static final String TOTAL_PAGES = "total_pages";

    public static List<MovieVideo> parseMovieVideoJSON(String input) throws JSONException {
        List<MovieVideo> videos = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(input);

        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject element = jsonArray.getJSONObject(i);
            try {
                videos.add(parseMovieVideo(element));
            } catch (JSONException e) {
                Log.e(Constants.TAG, "Error parsing movie ", e);
            }
        }
        return videos;
    }

    private static MovieVideo parseMovieVideo(JSONObject movieVideo) throws JSONException {
        MovieVideo.Builder builder = new MovieVideo.Builder();
        builder.id(Parser.getStringProperty(ID, movieVideo, true));
        builder.key(Parser.getStringProperty(KEY, movieVideo, true));
        builder.name(Parser.getStringProperty(NAME, movieVideo, true));
        builder.site(Parser.getStringProperty(SITE, movieVideo, true));
        builder.size(Parser.getCompulsoryIntProperty(SIZE, movieVideo));
        builder.type(Parser.getStringProperty(TYPE, movieVideo, true));
        return builder.build();
    }
}
