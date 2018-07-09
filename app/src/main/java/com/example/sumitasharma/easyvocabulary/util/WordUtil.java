package com.example.sumitasharma.easyvocabulary.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sumitasharma.easyvocabulary.BuildConfig;

public class WordUtil {
    public static final String QUIZ_WORD = "word";
    public static final String QUIZ_WORD_MEANING = "word_meaning";
    public static final String QUIZ_WORD_ID = "word_id";
    public static final String LAST_VIEW_PAGER = "last_view_pager";
    public static final String USER_QUIZ_ANSWERS = "user_quiz_answers";
    public static final String USER_TOTAL = "user_total";
    public static final String CORRECT_ANSWERS = "correct_answers";
    public final static String WORD_MEANING_CARD_VIEW_IDENTIFIER = "1";
    public final static String QUIZ_CARD_VIEW_IDENTIFIER = "2";
    public final static String PROGRESS_CARD_VIEW_IDENTIFIER = "3";
    public final static String DICTIONARY_CARD_VIEW_IDENTIFIER = "4";
    // public final static String WORD_DICTIONARY_URL = "https://od-api.oxforddictionaries.com/api/v1/";
    public final static String API_KEY = BuildConfig.APIKEY;
    public final static String APP_ID = BuildConfig.APPID;
    public static final String QUIZ_SORT = "RANDOM() LIMIT 4";
    public static final String QUIZ_WHERE = "wordPracticed = 1";
    public static final String QUIZ_SEARCH_IDENTIFIER = "quiz_search_identifier";
    public static final String QUIZ_SEARCH_MEANING_IDENTIFIER = "quiz_search_meaning_identifier";
    public static final int QUIZ_LOADER = 101;
    public static final int PROGRESS_LOADER = 102;
    public final static String WORD_DICTIONARY_URL = "http://api.datamuse.com/";
    public static final String LAST_SAVED_POSITION = "last_saved_position";
    public static final String NOTIFICATION_ID = "notification-id";
    public static final String NOTIFICATION = "notification";
    public static final String NOTIFICATION_CHANNEL_NAME = "easyVocabulary";
    public static final String NOTIFICATION_CHANNEL = "1234";
    public static final String DICTIONARY_SEARCH_WORD = "search_word";
    public static final String DICTIONARY_SEARCH_MEANING = "search_meaning";
    public static final String STATE_WORD_PRACTICE = "state";
    public static final String STATE_WORD_DICTIONARY = "state_dictionary";


    /**
     * Checks Internet Connectivity
     *
     * @return true if the Internet Connection is available, false otherwise.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
