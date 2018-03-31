package com.example.sumitasharma.easyvocabulary.loaders;


import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.example.sumitasharma.easyvocabulary.data.WordContract;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DEFAULT_SORT;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DEFAULT_WHERE;

public class WordQuizLoader extends CursorLoader {

    private WordQuizLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, DEFAULT_WHERE, null, DEFAULT_SORT);
    }

    public static WordQuizLoader newWordQuizInstance(Context context) {
        return new WordQuizLoader(context, WordContract.WordsEntry.CONTENT_URI);
    }

    public static WordQuizLoader newInstanceForWordId(Context context, long wordId) {
        return new WordQuizLoader(context, WordContract.WordsEntry.buildWordUri(wordId));
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
