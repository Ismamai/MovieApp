package com.iblesa.movieapp.network;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.iblesa.movieapp.Constants;
import com.iblesa.movieapp.model.MovieReview;
import com.iblesa.movieapp.util.MovieReviewParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * This class will deal with network interaction with TheMovieDB api
 */
public class MovieReviewAPI extends AsyncTaskLoader<List<MovieReview>> {
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String REVIEWS_URL = "reviews";
    private static final String API_KEY_PARAM = "api_key";


    private String apiKey;
    private int movieId;


    public MovieReviewAPI(Context context, String apiKey, int movieId) {
        super(context);
        this.apiKey = apiKey;
        this.movieId = movieId;
    }

    @Nullable
    private List<MovieReview> getMovieReviewsFromUrl(Uri uri) {
        try {
            Log.d(Constants.TAG, "Loading data from uri " + uri);
            String responseFromHttpUrl = getResponseFromHttpUrl(new URL(uri.toString()));
            return MovieReviewParser.parseJSON(responseFromHttpUrl);
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
    public List<MovieReview> loadInBackground() {
        Log.d(Constants.TAG, "Loading review in background");
                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(Integer.toString(movieId))
                        .appendPath(REVIEWS_URL)
                        .appendQueryParameter(API_KEY_PARAM, apiKey).build();
                return getMovieReviewsFromUrl(uri);

    }

    @Override
    protected void onStartLoading() {
        Log.d(Constants.TAG, "MovieReviewAPI loader onStartLoading forcing load");
        forceLoad();
    }
}
