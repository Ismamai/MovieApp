package com.iblesa.movieapp.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.model.Movie;
import com.iblesa.movieapp.model.SortCriteria;
import com.iblesa.movieapp.util.MovieParser;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * This class will deal with network interaction with TheMovieDB api
 */

public class MovieAPI extends AsyncTask<SortCriteria, Void, List<Movie>> {

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String POPULAR_URL = "popular";
    private static final String RATE_URL = "top_rated";
    private static final String API_KEY_PARAM = "api_key";
    private String param;


    public MovieAPI(String param) {
        this.param = param;
    }

    @Override
    protected List<Movie> doInBackground(SortCriteria... sortTypes) {
        if (sortTypes.length < 1) {
            throw new IllegalArgumentException("Missing SortCriteria parameter");
        }
        String sortCriteria = sortTypes[0].getCriteria();
        switch (sortCriteria) {
            case SortCriteria.POPULAR: {
                Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(POPULAR_URL).appendQueryParameter(API_KEY_PARAM, param).build();
                return getMoviesFromUrl(uri);

            }
            case SortCriteria.RATE: {
                Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(RATE_URL).appendQueryParameter(API_KEY_PARAM, param).build();
                return getMoviesFromUrl(uri);
            }
            default:
                throw new IllegalArgumentException("SortCriteria not supported " + sortCriteria);

        }
    }

    @Nullable
    private List<Movie> getMoviesFromUrl(Uri uri) {
        try {
            String responseFromHttpUrl = getResponseFromHttpUrl(new URL(uri.toString()));
            return MovieParser.parseJSON(responseFromHttpUrl);
        } catch (IOException e) {
            Log.e(Constants.TAG, "Error getting external content for url " + uri.toString(), e);
            return null;
        } catch (JSONException e) {
            Log.e(Constants.TAG, "Error parsing json", e);
            return null;
        }
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    private String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
