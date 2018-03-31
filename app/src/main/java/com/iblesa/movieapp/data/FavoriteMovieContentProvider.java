package com.iblesa.movieapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.iblesa.movieapp.data.MovieContract.MovieEntry.TABLE_NAME;

public class FavoriteMovieContentProvider extends ContentProvider {
    MovieDBHelper dbHelper;

    public static final int FAVORITES = 100;
    public static final int FAVORITES_WITH_ID = 101;


    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVORITES, FAVORITES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVORITES + "/#", FAVORITES_WITH_ID);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        dbHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case FAVORITES: {
                retCursor = db.query(TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                //Informing the cursor what uri created it
                retCursor.setNotificationUri(getContext().getContentResolver(),uri);
                break;
            }
//            case FAVORITES_WITH_ID: {
//                retCursor = null;
//                break;
//            }
            default:
                throw new UnsupportedOperationException("Unkown Uri " + uri);

        }
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri retUri;
        switch (match) {
            case FAVORITES: {
                long id = db.insert(TABLE_NAME,
                        null, values);
                if (id > 0) {
                    retUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unkown Uri " + uri);

        }
        //Notify ContentResolver that URI has been modified
        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
