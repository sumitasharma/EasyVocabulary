package com.example.sumitasharma.easyvocabulary.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WordUtil {
    public final static String WORD_MEANING_CARD_VIEW_IDENTIFIER = "1";
    public final static String QUIZ_CARD_VIEW_IDENTIFIER = "2";
    public final static String PROGRESS_CARD_VIEW_IDENTIFIER = "3";
    public final static String DICTIONARY_CARD_VIEW_IDENTIFIER = "4";
    public final static String WORD_DICTIONARY_URL = "https://od-api.oxforddictionaries.com/api/v1/";
    public final static String API_KEY = "bb5d70980b5db60c1c8b185dc9dc4d78";
    public final static String APP_ID = "351ed498";
    public final static String ERROR_CALLING_API = "error_calling_api";

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
