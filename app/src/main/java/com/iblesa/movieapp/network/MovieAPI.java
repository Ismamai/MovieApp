package com.iblesa.movieapp.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.iblesa.movieapp.MainActivity;
import com.iblesa.movieapp.model.Movie;
import com.iblesa.movieapp.model.SortCriteria;

import java.util.List;

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
                break;

            }
            case SortCriteria.RATE: {
                Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(RATE_URL).appendQueryParameter(API_KEY_PARAM, param).build();
                break;
            }
            default:
                throw new IllegalArgumentException("SortCriteria not supported " + sortCriteria);

        }
        return null;
    }
}
