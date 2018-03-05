package com.iblesa.movieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iblesa.movieapp.MainActivity;
import com.iblesa.movieapp.data.MovieContract.MovieEntry;

/**
 * Movie class to deal with db
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    // File that will contain the database
    private static final String DATABASE_NAME = "movies.db";
    // database version
    private static final int DATABASE_VERSION = 1;


    public MovieDBHelper(Context context) {
        // Call the parent constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER UNIQUE NOT NULL" +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_RELEASE_DATE + "TEXT NOT NULL ," +
                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL ," +
                MovieEntry.COLUMN_POPULARITY + "REAL NOT NULL," +
                MovieEntry.COLUMN_VOTE_AVERAGE + "REAL NOT NULL" +
                ");";
        Log.d(MainActivity.TAG, SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_STATEMENT = "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
        db.execSQL(DROP_STATEMENT);
        Log.d(MainActivity.TAG, DROP_STATEMENT);
        onCreate(db);
    }
}
