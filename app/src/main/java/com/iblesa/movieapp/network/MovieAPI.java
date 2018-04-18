package com.iblesa.movieapp.network;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.model.Movie;
import com.iblesa.movieapp.model.SortCriteria;
import com.iblesa.movieapp.util.MovieParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * This class will deal with network interaction with TheMovieDB api
 */
public class MovieAPI extends AsyncTaskLoader<List<Movie>> {
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String POPULAR_URL = "popular";
    private static final String RATE_URL = "top_rated";
    private static final String API_KEY_PARAM = "api_key";

    private String apiKey;
    private final String sortCriteria;


    public MovieAPI(Context context, String apiKey, String sortCriteria) {
        super(context);
        this.apiKey = apiKey;
        this.sortCriteria = sortCriteria;
    }

    @Nullable
    private List<Movie> getMoviesFromUrl(Uri uri) {
        try {
            Log.d(Constants.TAG, "Loading data from uri " + uri);
            String responseFromHttpUrl = getResponseFromHttpUrl(new URL(uri.toString()));
            return MovieParser.parseJSON(responseFromHttpUrl);
        } catch (IOException e) {
            Log.e(Constants.TAG, "Error getting external content for url " + uri.toString(), e);
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

    @Override
    public List<Movie> loadInBackground() {
        Log.d(Constants.TAG, "Loading in background");
        switch (sortCriteria) {
            case SortCriteria.POPULAR: {
                Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(POPULAR_URL).appendQueryParameter(API_KEY_PARAM, apiKey).build();
                return getMoviesFromUrl(uri);

            }
            case SortCriteria.RATE: {
                Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(RATE_URL).appendQueryParameter(API_KEY_PARAM, apiKey).build();
                return getMoviesFromUrl(uri);
            }
            default:
                throw new IllegalArgumentException("SortCriteria not supported " + sortCriteria);

        }
    }

    @Override
    protected void onStartLoading() {
        Log.d(Constants.TAG, "MovieApi loader onStartLoading forcing load");
        forceLoad();
    }
}
