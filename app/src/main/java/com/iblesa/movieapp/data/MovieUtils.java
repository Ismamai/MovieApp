package com.iblesa.movieapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.iblesa.movieapp.data.MovieContract.MovieEntry;
import com.iblesa.movieapp.model.Movie;

public class MovieUtils {

    @NonNull
    public static ContentValues getContentValues(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry._ID, movie.getId());
        cv.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        cv.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        cv.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        cv.put(MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        cv.put(MovieEntry.COLUMN_VIDEO, movie.isVideo() ? 1 : 0);
        return cv;
    }

    public static Movie getMovie(Cursor cursor) {
        Movie.Builder builder = new Movie.Builder();

        return builder.id(cursor.getInt(cursor.getColumnIndex(MovieEntry._ID)))
                .title(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE)))
                .overview(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)))
                .releaseDate(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE)))
                .voteAverage(cursor.getFloat(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE)))
                .popularity(cursor.getFloat(cursor.getColumnIndex(MovieEntry.COLUMN_POPULARITY)))
                .posterPath(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH)))
                .backdropPath(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_BACKDROP_PATH)))
                .video(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_VIDEO)) == 1)
                .build();

    }
}
