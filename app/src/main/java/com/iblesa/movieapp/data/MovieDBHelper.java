package com.iblesa.movieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_RELEASE_DATE + "TEXT NOT NULL ," +
                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL ," +
                MovieEntry.COLUMN_VOTE_AVERAGE + "REAL NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
