package com.iblesa.movieapp.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
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
                retCursor.setNotificationUri(getContentResolver(), uri);
                break;
            }
            case FAVORITES_WITH_ID: {
                //URI: content://<authority>/movies/#
                // We have to extract the id from the uri
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArg = new String[]{id};
                retCursor = db.query(TABLE_NAME,
                        projection, mSelection, mSelectionArg, null, null, sortOrder);
                retCursor.setNotificationUri(getContentResolver(), uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);

        }
        return retCursor;
    }

    private ContentResolver getContentResolver() {
        Context context = getContext();
        if (context != null) {
            return context.getContentResolver();
        } else {
            throw new IllegalStateException(("getContext is null"));
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES: {
                return "vnd.android.cursor.dir" + "/" + MovieContract.AUTHORITY + "/" + MovieContract.PATH_FAVORITES;
            }
            case FAVORITES_WITH_ID: {
                return "vnd.android.cursor.item" + "/" + MovieContract.AUTHORITY + "/" + MovieContract.PATH_FAVORITES;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);

        }
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
                throw new UnsupportedOperationException("Unknown Uri " + uri);

        }
        //Notify ContentResolver that URI has been modified
        getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int resDelete;
        switch (match) {
            case FAVORITES_WITH_ID: {
                //URI: content://<authority>/movies/#
                // We have to extract the id from the uri
                String id = uri.getPathSegments().get(1);
                String mWhere = "_id=?";
                String[] mWhereArgs = new String[]{id};
                resDelete = db.delete(TABLE_NAME, mWhere, mWhereArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        getContentResolver().notifyChange(uri, null);
        return resDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int updated;
        switch (match) {
            case FAVORITES_WITH_ID: {
                //URI: content://<authority>/movies/#
                // We have to extract the id from the uri
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArg = new String[]{id};
                updated = db.update(TABLE_NAME, values, mSelection, mSelectionArg);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        if (updated != 0) {
            getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }
}
