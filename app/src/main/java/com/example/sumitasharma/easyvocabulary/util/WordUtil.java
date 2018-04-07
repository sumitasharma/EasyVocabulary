package com.example.sumitasharma.easyvocabulary.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
    public final static String WORD_DICTIONARY_URL = "https://od-api.oxforddictionaries.com/api/v1/";
    public final static String API_KEY = "";
    public final static String APP_ID = "";
    public final static String ERROR_CALLING_API = "error_calling_api";
    public static final String QUIZ_SORT = "RANDOM() LIMIT 5";
    public static final String QUIZ_WHERE = "wordPracticed = 1";


    /**
     * Checks Internet Connectivity
     *
     * @return true if the Internet Connection is available, false otherwise.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } else
            return false;
    }

}
