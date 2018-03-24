package com.iblesa.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, MoviesAdapter.ListItemClickListener{

    public static final String TAG = "iblesa_app";

    private RecyclerView recyclerView;
    private TextView errorMessageDisplay;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSharedPreferences();
        recyclerView = findViewById(R.id.rv_movies);
        errorMessageDisplay = findViewById(R.id.tv_error_message_display);
        progressBar = findViewById(R.id.pb_loading_indicator);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        String key = getString(R.string.themoviedb_api_key);
        MoviesAdapter adapter = new MoviesAdapter(this);
        recyclerView.setAdapter(adapter);
        loadData(key, adapter);
    }

    private void loadData(String key, MoviesAdapter adapter) {
        MovieAPI api = new MovieAPI(key, adapter);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String criteria = defaultSharedPreferences.getString(getString(R.string.preference_sort_key), getString(R.string.preference_sort_value_popular));
        SortCriteria sortCriteria;
        try {
            sortCriteria = new SortCriteria(criteria);
        } catch (IllegalArgumentException argument) {
            Log.e(Constants.TAG, "Value set for SortCriteria is not good " + criteria);
            sortCriteria = new SortCriteria(getString(R.string.preference_sort_value_popular));
        }
        showData();
        api.execute(sortCriteria);
    }

    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our movies_menu layout to this menu */
        inflater.inflate(R.menu.movies_menu, menu);
        /* Return true so that the movies_menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Time to remove the listener as we are getting destroyed
        defaultSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String value = sharedPreferences.getString(key, "");
        Log.d(Constants.TAG, "Preference has changed its value (" +key+") =  " + value );
    }


    private void showError() {
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessageDisplay.setVisibility(View.VISIBLE);
    }


    private void showData() {
        recyclerView.setVisibility(View.VISIBLE);
        errorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListItemClick(Movie movieSelected) {
        Log.d(Constants.TAG, "Item selected is " + movieSelected.toString());
        Intent intentDetailActivity = new Intent(this, DetailActivity.class);
        intentDetailActivity.putExtra(Intent.EXTRA_TEXT, movieSelected);
        startActivity(intentDetailActivity);
    }

    /**
     * This class will deal with network interaction with TheMovieDB api
     */

    class MovieAPI extends AsyncTask<SortCriteria, Void, List<Movie>> {

        private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
        private static final String POPULAR_URL = "popular";
        private static final String RATE_URL = "top_rated";
        private static final String API_KEY_PARAM = "api_key";

        private String param;
        private MoviesAdapter moviesAdapter;


        MovieAPI(String param, MoviesAdapter moviesAdapter) {
            this.param = param;
            this.moviesAdapter = moviesAdapter;
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            progressBar.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showData();
                moviesAdapter.setMovies(movies);
            } else {
                showError();
            }
        }

    }
}
