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
public class MovieReviewAPI extends AsyncTaskLoader<List<MovieReview>> {
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String REVIEWS_URL = "reviews";
    private static final String API_KEY_PARAM = "api_key";


    private String apiKey;
    private int movieId;
    private OkHttpClient client = new OkHttpClient();


    public MovieReviewAPI(Context context, String apiKey, int movieId) {
        super(context);
        this.apiKey = apiKey;
        this.movieId = movieId;
    }

    @Nullable
    private List<MovieReview> getMovieReviewsFromUrl(Uri uri) {
        try {
            Log.d(Constants.TAG, "Loading data from uri " + uri);
            Request request = new Request.Builder()
                    .url(new URL(uri.toString()))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                ResponseBody body = response.body();
                if (body != null) {
                    return MovieReviewParser.parseJSON(body.string());
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
