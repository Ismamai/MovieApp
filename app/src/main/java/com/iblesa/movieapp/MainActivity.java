package com.iblesa.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.iblesa.movieapp.model.Movie;
import com.iblesa.movieapp.model.SortCriteria;
import com.iblesa.movieapp.network.MovieAPI;
import com.iblesa.movieapp.util.FakeMoviesData;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String TAG = "iblesa_app";
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSharedPreferences();
        recyclerView = findViewById(R.id.rv_movies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        String key = getString(R.string.themoviedb_api_key);
        List<Movie> movies = null;
        MovieAPI api = new MovieAPI(key);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String criteria = defaultSharedPreferences.getString(getString(R.string.preference_sort_key), getString(R.string.preference_sort_value_popular));
        SortCriteria sortCriteria;
        try {
            sortCriteria = new SortCriteria(criteria);
        } catch (IllegalArgumentException argument) {
            Log.e(Constants.TAG, "Value set for SortCriteria is not good " + criteria);
            sortCriteria = new SortCriteria(getString(R.string.preference_sort_value_popular));
        }
         api.execute(sortCriteria);

        if (movies == null || movies.size() == 0) {
            movies = FakeMoviesData.getFakeMoviesData(this);
        }

        MoviesAdapter adapter = new MoviesAdapter(movies);
        recyclerView.setAdapter(adapter);
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
}
