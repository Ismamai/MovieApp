package com.iblesa.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
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

import com.iblesa.movieapp.data.FavoriteMovieLoader;
import com.iblesa.movieapp.model.Movie;
import com.iblesa.movieapp.model.SortCriteria;
import com.iblesa.movieapp.network.MovieAPI;

import java.util.List;

import static com.iblesa.movieapp.Constants.LOADER_MOVIE_KEY;
import static com.iblesa.movieapp.Constants.LOADER_PARAM_MOVIE_API_KEY;
import static com.iblesa.movieapp.Constants.LOADER_PARAM_MOVIE_SORT_CRITERIA;
import static com.iblesa.movieapp.Constants.MOVIE_API_KEY;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,
        MoviesAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<List<Movie>> {

    private RecyclerView recyclerView;
    private TextView errorMessageDisplay;
    private ProgressBar progressBar;
    private MoviesAdapter moviesAdapter;

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
//        String key = getString(R.string.themoviedb_api_key);
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
//        loadData(key);
    }

    private void loadData(String key) {

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

        switch (sortCriteria.getCriteria()) {
            case SortCriteria.POPULAR:
            case SortCriteria.RATE: {
                ConnectivityManager cm =
                        (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = null;
                if (cm != null) {
                    activeNetwork = cm.getActiveNetworkInfo();
                }
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (!isConnected) {
                    showError();
                }
                break;
            }
            case SortCriteria.FAVORITES: {

                break;
            }
            default: {
                throw new UnsupportedOperationException("Unsupported operation sortCriteria: " + sortCriteria);
            }
        }
        Bundle queryBundle = new Bundle();
        queryBundle.putString(LOADER_PARAM_MOVIE_SORT_CRITERIA, sortCriteria.getCriteria());
        queryBundle.putString(LOADER_PARAM_MOVIE_API_KEY, key);
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Object> loader = loaderManager.getLoader(LOADER_MOVIE_KEY);
        if (loader == null) {
            Log.d(Constants.TAG, "Initiating Loader");
            loaderManager.initLoader(LOADER_MOVIE_KEY, queryBundle, this);
        } else {
            Log.d(Constants.TAG, "Restarting Loader");

            loaderManager.restartLoader(LOADER_MOVIE_KEY, queryBundle, this);
        }
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

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle args) {
        Log.d(Constants.TAG,"On OnCreateLoader ");
        progressBar.setVisibility(View.VISIBLE);
        String sortCriteria = args.getString(LOADER_PARAM_MOVIE_SORT_CRITERIA);
        if (sortCriteria == null) {
            throw new IllegalArgumentException("Missing parameter " + LOADER_PARAM_MOVIE_SORT_CRITERIA);
        }
        Loader<List<Movie>> resLoader;
        switch (sortCriteria) {
            case SortCriteria.POPULAR:
            case SortCriteria.RATE: {
                resLoader = new MovieAPI(this, args.getString(MOVIE_API_KEY), sortCriteria);
                break;
            }
            case SortCriteria.FAVORITES: {
                resLoader = new FavoriteMovieLoader(this);
                break;
            }
            default: {
                throw new UnsupportedOperationException("SortCriteria not supported " + sortCriteria);
            }
        }
        return resLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movies != null) {
            showData();
            moviesAdapter.setMovies(movies);
        } else {
            Log.d(Constants.TAG, "There are no movies to show");
            showError();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        Log.d(Constants.TAG, "Loader reset " + loader);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.TAG, "onResume, calling to reload data");
        loadData(getString(R.string.themoviedb_api_key));
    }
}
