package com.iblesa.movieapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iblesa.movieapp.databinding.ActivityDetailBinding;
import com.iblesa.movieapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding activityDetailBinding;

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
            }
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
