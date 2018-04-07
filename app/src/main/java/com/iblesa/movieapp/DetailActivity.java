package com.iblesa.movieapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iblesa.movieapp.databinding.ActivityDetailBinding;
import com.iblesa.movieapp.model.Movie;
import com.iblesa.movieapp.model.MovieReview;
import com.iblesa.movieapp.model.MovieVideo;
import com.iblesa.movieapp.network.MovieReviewAPI;
import com.iblesa.movieapp.network.MovieVideoAPI;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import static com.iblesa.movieapp.Constants.LOADER_MOVIE_PARAM_API_KEY;
import static com.iblesa.movieapp.Constants.LOADER_MOVIE_REVIEW_PARAM_MOVIE_ID;
import static com.iblesa.movieapp.Constants.LOADER_MOVIE_REVIEW_KEY;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding activityDetailBinding;

    private LoaderManager.LoaderCallbacks<List<MovieReview>> movieReviewLoader = new LoaderManager.LoaderCallbacks<List<MovieReview>>() {
        @Override
        public Loader<List<MovieReview>> onCreateLoader(int id, Bundle args) {
            Log.d(Constants.TAG, "Trying to create loader for MovieReviews. loader id = "
                    + id + " == LOADER_MOVIE_REVIEW_KEY (" + Constants.LOADER_MOVIE_REVIEW_KEY
                    + ") = " + (id == Constants.LOADER_MOVIE_REVIEW_KEY));

            int movieId = args.getInt(LOADER_MOVIE_REVIEW_PARAM_MOVIE_ID);
            String apiKey = args.getString(LOADER_MOVIE_PARAM_API_KEY);
            return new MovieReviewAPI(getApplicationContext(), apiKey, movieId );
        }

        @Override
        public void onLoadFinished(Loader<List<MovieReview>> loader, List<MovieReview> data) {
            populateReviews(data);

        }

        @Override
        public void onLoaderReset(Loader<List<MovieReview>> loader) {

        }
    };

    private void populateReviews(List<MovieReview> data) {
        Log.d(Constants.TAG, "Received reviews " + data);
    }

    private LoaderManager.LoaderCallbacks<List<MovieVideo>> movieVideosLoader = new LoaderManager.LoaderCallbacks<List<MovieVideo>>() {
        @Override
        public Loader<List<MovieVideo>> onCreateLoader(int id, Bundle args) {
            Log.d(Constants.TAG, "Trying to create loader for MovieVideo. loader id = "
                    + id + " == LOADER_MOVIE_VIDEOS_KEY (" + Constants.LOADER_MOVIE_VIDEOS_KEY
                    + ") = " + (id == Constants.LOADER_MOVIE_VIDEOS_KEY));

            int movieId = args.getInt(LOADER_MOVIE_REVIEW_PARAM_MOVIE_ID);
            String apiKey = args.getString(LOADER_MOVIE_PARAM_API_KEY);
            return new MovieVideoAPI(getApplicationContext(), apiKey, movieId );
        }

        @Override
        public void onLoadFinished(Loader<List<MovieVideo>> loader, List<MovieVideo> data) {

        }

        @Override
        public void onLoaderReset(Loader<List<MovieVideo>> loader) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intentThatStartedThisActivity = getIntent();

        // COMPLETED (2) Display the weather forecast that was passed from MainActivity
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                Movie movie = intentThatStartedThisActivity.getParcelableExtra(Intent.EXTRA_TEXT);
                Log.d(Constants.TAG, "Movie passed to activity " + movie);
                activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
                populateUI(movie);
//                testParsingMovieReviews();
                int id = movie.getId();
                loadExtendedData(id);

            }
        }
    }

//    private void testParsingMovieReviews() {
//        List<MovieReview> movieReviews = null;
//        try {
//            movieReviews = MovieReviewParser.parseMovieReviewJSON(getString(R.string.movie_reviews_sample));
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e(Constants.TAG, "Error parsing data ", e);
//        }
//        Log.d(Constants.TAG, "Reviews is " + movieReviews);
//    }

    private void loadExtendedData(int id) {
        Log.d(Constants.TAG, "Loading extended data for movie with id " + id);
        Bundle queryBundle = new Bundle();
        queryBundle.putInt(LOADER_MOVIE_REVIEW_PARAM_MOVIE_ID, id);
        queryBundle.putString(LOADER_MOVIE_PARAM_API_KEY, getString(R.string.themoviedb_api_key));

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader loaderReviews = loaderManager.getLoader(LOADER_MOVIE_REVIEW_KEY);
        if (loaderReviews == null) {
            Log.d(Constants.TAG, "Initiating Loader");
            loaderManager.initLoader(LOADER_MOVIE_REVIEW_KEY, queryBundle, movieReviewLoader);
        } else {
            Log.d(Constants.TAG, "Restarting Loader");

            loaderManager.restartLoader(LOADER_MOVIE_REVIEW_KEY, queryBundle, movieReviewLoader);
        }

    }

    private void populateUI(Movie movie) {
        String posterPath = movie.getPosterPath();
        String imgSample = "http://image.tmdb.org/t/p/w185/" + posterPath;
        Log.d(Constants.TAG, "Loading image for movie detail url = " + imgSample);
        Picasso.with(this).load(imgSample)
                .placeholder(R.drawable.progress_animation)
                .into(activityDetailBinding.imageView);
        activityDetailBinding.tvTitle.setText(movie.getTitle());
        activityDetailBinding.tvReleaseDate.setText(movie.getReleaseDate());
        activityDetailBinding.tvVoteAverage.setText(String.format(Locale.getDefault(), "%.2f", movie.getVoteAverage()));
        activityDetailBinding.tvSynopsis.setText(movie.getOverview());
    }
}
