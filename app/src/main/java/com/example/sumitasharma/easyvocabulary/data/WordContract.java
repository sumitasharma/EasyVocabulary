package com.example.sumitasharma.easyvocabulary.data;

import android.net.Uri;
import android.provider.BaseColumns;


public class WordContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.sumitasharma.easyvocabulary";
    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_WORDS = "words";
    // The base content URI = "content://" + <authority>
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class WordsEntry implements BaseColumns {

        // MovieEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORDS).build();
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_ID = "wordID";
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_WORD_MEANING = "wordMeaning";
        public static final String COLUMN_WORD_LEVEL = "wordLevel";
        public static final String COLUMN_LAST_UPDATED = "lastUpdated";
        public static final String COLUMN_WORD_PRACTICED = "wordPracticed";
        public static final String COLUMN_WORD_TYPE = "wordPartOfSpeechType";
        public static final String COLUMN_WORD_EXAMPLE = "wordExample";

        public static Uri buildWordUri(long word_id) {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORDS).appendPath(Long.toString(word_id)).build();
        }

    }

}
