package com.example.sumitasharma.easyvocabulary.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import timber.log.Timber;

import static android.content.ContentValues.TAG;

public class WordContentProvider extends ContentProvider {
    private static final int WORDS = 100;
    private static final int WORD_WITH_ID = 101;
    // Declare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private WordDbHelper mWordDbHelper;

    private static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the Words directory and a single Words by ID.
         */
        uriMatcher.addURI(WordContract.AUTHORITY, WordContract.PATH_WORDS, WORDS);
        uriMatcher.addURI(WordContract.AUTHORITY, WordContract.PATH_WORDS + "/#", WORD_WITH_ID);

        return uriMatcher;
    }

    //MovieDetails movieDetails;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mWordDbHelper = new WordDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // COMPLETED (1) Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mWordDbHelper.getReadableDatabase();

        // COMPLETED (2) Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // COMPLETED (3) Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case WORDS:
                retCursor = db.query(WordContract.WordsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                if (retCursor.moveToFirst() && retCursor.getCount() >= 1) {
                    do {
                        Timber.i("Checking" + retCursor.getString(retCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
                        Timber.i("Checking" + retCursor.getString(retCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING)));


                    } while (retCursor.moveToNext());
                }


                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        try {
            // COMPLETED (4) Set a notification URI on the Cursor and return that Cursor
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // Return the desired Cursor
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
        // COMPLETED (1) Get access to the task database (to write new data to)
        final SQLiteDatabase db = mWordDbHelper.getWritableDatabase();
        // COMPLETED (2) Write URI matching code to identify the match for the tasks directory
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned
        switch (match) {
            case WORDS:
                // Inserting values into tasks table
                long id = db.insert(WordContract.WordsEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(WordContract.WordsEntry.CONTENT_URI, id);
                    Log.i(TAG, "inserted " + values.toString());
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // COMPLETED (4) Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        try {
            // COMPLETED (5) Notify the resolver if the uri has been changed, and return the newly inserted URI
            getContext().getContentResolver().notifyChange(uri, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numRowsDeleted;
        int match = sUriMatcher.match(uri);
        if (null == selection) selection = "1";
        switch (match) {
            case WORDS:
                numRowsDeleted = mWordDbHelper.getWritableDatabase().delete(
                        WordContract.WordsEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            try {
                getContext().getContentResolver().notifyChange(uri, null);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        final SQLiteDatabase db = mWordDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {

            case WORD_WITH_ID:
                count = db.update(WordContract.WordsEntry.TABLE_NAME, values, selection, selectionArgs);

                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
