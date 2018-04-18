package com.iblesa.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.iblesa.movieapp.data.MovieContract;
import com.iblesa.movieapp.data.SingleFavoriteMovieLoader;
import com.iblesa.movieapp.databinding.ActivityDetailBinding;
import com.iblesa.movieapp.model.Movie;
import com.iblesa.movieapp.model.MovieReview;
import com.iblesa.movieapp.model.MovieVideo;
import com.iblesa.movieapp.network.MovieReviewAPI;
import com.iblesa.movieapp.network.MovieVideoAPI;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import static com.iblesa.movieapp.Constants.LOADER_PARAM_MOVIE_API_KEY;
import static com.iblesa.movieapp.Constants.LOADER_MOVIE_REVIEW_KEY;
import static com.iblesa.movieapp.Constants.LOADER_MOVIE_VIDEOS_KEY;
import static com.iblesa.movieapp.Constants.LOADER_PARAM_MOVIE_ID;
import static com.iblesa.movieapp.Constants.LOADER_SINGLE_FAVORITE_MOVIE;

public class DetailActivity extends AppCompatActivity implements MovieReviewAdapter.MovieReviewListItemClickListener, MovieVideoAdapter.MovieVideoListItemClickListener {

    ActivityDetailBinding activityDetailBinding;
    MovieReviewAdapter movieReviewAdapter;
    MovieVideoAdapter movieVideoAdapter;
    private boolean isFavorite;
    private Movie movie;


    private LoaderManager.LoaderCallbacks<List<MovieReview>> movieReviewLoader = new LoaderManager.LoaderCallbacks<List<MovieReview>>() {
        @Override
        public Loader<List<MovieReview>> onCreateLoader(int id, Bundle args) {
            Log.d(Constants.TAG, "Trying to create loader for MovieReviews. loader id = "
                    + id + " == LOADER_MOVIE_REVIEW_KEY (" + Constants.LOADER_MOVIE_REVIEW_KEY
                    + ") = " + (id == Constants.LOADER_MOVIE_REVIEW_KEY));

            int movieId = args.getInt(LOADER_PARAM_MOVIE_ID);
            String apiKey = args.getString(LOADER_PARAM_MOVIE_API_KEY);
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


    private LoaderManager.LoaderCallbacks<List<MovieVideo>> movieVideosLoader = new LoaderManager.LoaderCallbacks<List<MovieVideo>>() {
        @Override
        public Loader<List<MovieVideo>> onCreateLoader(int id, Bundle args) {
            Log.d(Constants.TAG, "Trying to create loader for MovieVideo. loader id = "
                    + id + " == LOADER_MOVIE_VIDEOS_KEY (" + Constants.LOADER_MOVIE_VIDEOS_KEY
                    + ") = " + (id == Constants.LOADER_MOVIE_VIDEOS_KEY));

            int movieId = args.getInt(LOADER_PARAM_MOVIE_ID);
            String apiKey = args.getString(LOADER_PARAM_MOVIE_API_KEY);
            return new MovieVideoAPI(getApplicationContext(), apiKey, movieId );
        }

        @Override
        public void onLoadFinished(Loader<List<MovieVideo>> loader, List<MovieVideo> data) {
            populateVideos(data);
        }

        @Override
        public void onLoaderReset(Loader<List<MovieVideo>> loader) {

        }
    };

    private LoaderManager.LoaderCallbacks<Movie> favoriteMovieLoader = new LoaderManager.LoaderCallbacks<Movie>() {
        @Override
        public Loader<Movie> onCreateLoader(int id, Bundle args) {
            Log.d(Constants.TAG, "Trying to create loader for MovieReviews. loader id = "
                    + id + " == LOADER_MOVIE_REVIEW_KEY (" + Constants.LOADER_MOVIE_REVIEW_KEY
                    + ") = " + (id == Constants.LOADER_MOVIE_REVIEW_KEY));

            int movieId = args.getInt(LOADER_PARAM_MOVIE_ID);
            String apiKey = args.getString(LOADER_PARAM_MOVIE_API_KEY);
            return new SingleFavoriteMovieLoader(getApplicationContext(), movieId);
        }

        @Override
        public void onLoadFinished(Loader<Movie> loader, Movie data) {
            populateFavorite(data);

        }

        @Override
        public void onLoaderReset(Loader<Movie> loader) {

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
                movie = intentThatStartedThisActivity.getParcelableExtra(Intent.EXTRA_TEXT);
                Log.d(Constants.TAG, "Movie passed to activity " + movie);
                activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
                populateUI(movie);
                int id = movie.getId();

                //Initialize LayoutManager for Reviews
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                activityDetailBinding.rvReviews.setLayoutManager(linearLayoutManager);
                //Initialize MovieReviewAdapter
                movieReviewAdapter = new MovieReviewAdapter(this);
                activityDetailBinding.rvReviews.setAdapter(movieReviewAdapter);

                LinearLayoutManager linearLayoutManagerVideos = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                activityDetailBinding.rvVideos.setLayoutManager(linearLayoutManagerVideos);
                movieVideoAdapter = new MovieVideoAdapter(this);
                activityDetailBinding.rvVideos.setAdapter(movieVideoAdapter);

                loadExtendedData(id);
            }
        }
    }

    private void loadExtendedData(int id) {
        Log.d(Constants.TAG, "Loading extended data for movie with id " + id);
        Bundle queryBundle = new Bundle();
        queryBundle.putInt(LOADER_PARAM_MOVIE_ID, id);
        queryBundle.putString(LOADER_PARAM_MOVIE_API_KEY, getString(R.string.themoviedb_api_key));

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader loaderReviews = loaderManager.getLoader(LOADER_MOVIE_REVIEW_KEY);
        if (loaderReviews == null) {
            Log.d(Constants.TAG, "Initiating MovieReviews Loader");
            loaderManager.initLoader(LOADER_MOVIE_REVIEW_KEY, queryBundle, movieReviewLoader);
        } else {
            Log.d(Constants.TAG, "Restarting MovieReviews Loader");
            loaderManager.restartLoader(LOADER_MOVIE_REVIEW_KEY, queryBundle, movieReviewLoader);
        }
        Loader loaderVideos = loaderManager.getLoader(LOADER_MOVIE_VIDEOS_KEY);
        if (loaderVideos == null) {
            Log.d(Constants.TAG, "Initiating MovieVideos Loader");
            loaderManager.initLoader(LOADER_MOVIE_VIDEOS_KEY, queryBundle, movieVideosLoader);
        } else {
            Log.d(Constants.TAG, "Restarting MovieVideos Loader");
            loaderManager.restartLoader(LOADER_MOVIE_VIDEOS_KEY, queryBundle, movieVideosLoader);
        }
        Loader loaderSingleFavoriteVideo = loaderManager.getLoader(LOADER_SINGLE_FAVORITE_MOVIE);

        if (loaderSingleFavoriteVideo == null) {
            Log.d(Constants.TAG, "Initiating SingleFavoriteMovie Loader");
            loaderManager.initLoader(LOADER_SINGLE_FAVORITE_MOVIE, queryBundle, favoriteMovieLoader);
        } else {
            Log.d(Constants.TAG, "Restarting SingleFavoriteMovie Loader");
            loaderManager.restartLoader(LOADER_SINGLE_FAVORITE_MOVIE, queryBundle, favoriteMovieLoader);
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

    private void populateReviews(List<MovieReview> data) {
        int counter = 0;
        if (data != null) {
            counter = data.size();
        }
        Log.d(Constants.TAG, "Received reviews. Number of reviews " + counter);
        activityDetailBinding.tvReviewsCounter.setText(getString(R.string.reviews_counter,counter));
        movieReviewAdapter.setReviews(data);
    }

    private void populateVideos(List<MovieVideo> data) {
        int counter = 0;
        if (data != null) {
            counter = data.size();
        }
        Log.d(Constants.TAG, "Received videos. Number or videos " + counter);
        activityDetailBinding.tvVideosCounter.setText(getString(R.string.videos_counter,counter));
        movieVideoAdapter.setVideos(data);
    }

    private void populateFavorite(Movie data) {
        isFavorite = data != null;
        Log.d(Constants.TAG, "Favorite Movie " + isFavorite);
        Log.d(Constants.TAG, "Favorite Movie " + data);
        activityDetailBinding.tbFavoriteMovie.setChecked(isFavorite);
        activityDetailBinding.tbFavoriteMovie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Uri contentUri = MovieContract.MovieEntry.CONTENT_URI;
                if (isChecked) {
                    Log.d(Constants.TAG, "Movie has been marked as favorite");
                    ContentValues cv = new ContentValues();
                    cv.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
                    cv.put(MovieContract.MovieEntry._ID, movie.getId());
                    cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                    cv.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
                    cv.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
                    cv.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
                    cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
                    cv.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdrop_path());
                    cv.put(MovieContract.MovieEntry.COLUMN_VIDEO, movie.isVideo());
                    Uri insert = getBaseContext().getContentResolver().insert(contentUri, cv);

                    Log.d(Constants.TAG, "Added movie as Favorite " + movie + " with uri " + insert);
                } else {
                    Log.d(Constants.TAG, "Movie has been removed from favorites");
                    contentUri = contentUri.buildUpon().appendPath(Integer.toString(movie.getId())).build();
                    int numRowsDeleted = getBaseContext().getContentResolver()
                            .delete(contentUri,
                                    null,
                                    null);
                    Log.d(Constants.TAG, "Deleted Favorite movie " + movie);
                }
            }
        });

    }

    @Override
    public void onListItemClick(MovieReview movieReviewSelected) {
        Log.d(Constants.TAG, "Click on Review " + movieReviewSelected);
        if (TextUtils.isEmpty(movieReviewSelected.getUrl())) {
            Toast.makeText(this, "Review does not have a url", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieReviewSelected.getUrl()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    @Override
    public void onMovieVideoListItemClick(MovieVideo movieVideoSelected) {
        Log.d(Constants.TAG, "Click on Video " + movieVideoSelected);
        if (TextUtils.isEmpty(movieVideoSelected.getKey())) {
            Toast.makeText(this, "Video not ok", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + movieVideoSelected.getKey()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
