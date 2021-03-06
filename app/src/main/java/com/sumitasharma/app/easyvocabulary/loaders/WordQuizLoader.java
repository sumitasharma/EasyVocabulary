package com.sumitasharma.app.easyvocabulary.loaders;


import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.sumitasharma.app.easyvocabulary.data.WordContract;

import java.util.Arrays;

import timber.log.Timber;

import static com.sumitasharma.app.easyvocabulary.util.WordUtil.QUIZ_SORT;
import static com.sumitasharma.app.easyvocabulary.util.WordUtil.QUIZ_WHERE;

public class WordQuizLoader extends CursorLoader {

    private WordQuizLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortBy) {
        super(context, uri, projection, selection, selectionArgs, sortBy);
        Timber.i("Inside WordQuizLoader Second Constructor" + Arrays.toString(Query.PROJECTION) + QUIZ_WHERE + QUIZ_SORT);

    }

    public static WordQuizLoader newWordQuizInstance(Context context) {
        Timber.i("Inside WordQuizLoader");
        return new WordQuizLoader(context, WordContract.WordsEntry.CONTENT_URI, Query.PROJECTION, QUIZ_WHERE, null, QUIZ_SORT);
    }

    public static WordQuizLoader newInstanceForWordId(Context context, long wordId) {
        Timber.i("Inside WordQuizLoader newInstanceForWordId");
        //String[] projection = {WordContract.WordsEntry.COLUMN_WORD};
        String[] args = {String.valueOf(wordId)};
        String where = WordContract.WordsEntry.COLUMN_ID + " !=?";
        String sortOrder = "RANDOM() LIMIT 3";
        Timber.i("select * from words where " + where + "order by " + sortOrder + String.valueOf(wordId));
        return new WordQuizLoader(context, WordContract.WordsEntry.buildWordUri(wordId), Query.PROJECTION, where, args, sortOrder);
    }

    public interface Query {
        String[] PROJECTION = {
                WordContract.WordsEntry.COLUMN_ID,
                WordContract.WordsEntry.COLUMN_WORD,
                WordContract.WordsEntry.COLUMN_WORD_MEANING
        };

        int COLUMN_ID = 0;
        int COLUMN_WORD = 1;
        int COLUMN_MEANING = 2;
    }
}
