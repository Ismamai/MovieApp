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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
    private OkHttpClient client = new OkHttpClient();

    public MovieAPI(Context context, String apiKey, String sortCriteria) {
        super(context);
        this.apiKey = apiKey;
        this.sortCriteria = sortCriteria;
    }

    @Nullable
    private List<Movie> getMoviesFromUrl(Uri uri) {
        try {
            Log.d(Constants.TAG, "Loading data from uri " + uri);
            Request request = new Request.Builder()
                    .url(new URL(uri.toString()))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                ResponseBody body = response.body();
                if (body != null) {
                    return MovieParser.parseJSON(body.string());
                } else {
                    return new ArrayList<>();
                }
            }
        } catch (IOException e) {
            Log.e(Constants.TAG, "Error getting external content for url " + uri.toString(), e);
            return null;
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
