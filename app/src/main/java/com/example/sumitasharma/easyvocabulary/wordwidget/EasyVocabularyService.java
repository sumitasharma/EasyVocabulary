package com.example.sumitasharma.easyvocabulary.wordwidget;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.util.WordUtil;

import timber.log.Timber;

public class EasyVocabularyService extends IntentService {
    private static final String ACTION_DISPLAY_WORD_MEANING = "com.example.sumitasharma.bakingapp.action.display_word_meaning";

    public EasyVocabularyService() {
        super("EasyVocabularyService");
    }

    public static void startActionUpdateWordApp(Context context) {
        Intent intent = new Intent(context, EasyVocabularyService.class);
        intent.setAction(ACTION_DISPLAY_WORD_MEANING);
        context.startService(intent);
    }

    private void handleActionDisplayWordMeaning() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, EasyVocabularyWidget.class));
        Log.i("EasyVocabularyService", "Inside handleActionDisplayWordMeaning");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int numberOfWordsForPractice = Integer.parseInt(sharedPreferences.getString(getResources().getString(R.string.number_of_words_key), "1"));
        String frequencyOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.frequency_of_words_key), "sharedpreference"));
        String levelOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.level_of_words_for_practice_key), "shared"));
        String[] projection = {WordContract.WordsEntry.COLUMN_WORD, WordContract.WordsEntry.COLUMN_WORD_MEANING};
        String sort_order = "RANDOM() LIMIT 1";

        Cursor retCursor = this.getContentResolver().query(WordContract.WordsEntry.CONTENT_URI,
                projection,
                null,
                null,
                sort_order);
        Timber.i("Inside query of content provider in widget" + retCursor.getCount());
        if (retCursor.moveToFirst() && retCursor.getCount() >= 1) {
//            do {
//                wordWidgetText.setText(retCursor.getString(0));
//                meaningWidgetText.setText(retCursor.getString(1));
            Timber.i("word in widget : " + retCursor.getString(0));
            Timber.i("word meaning in widget : " + retCursor.getString(1));
//            } while (retCursor.moveToNext());
//            Random random = new Random();
//            mRecipeIndex = random.nextInt(max);
//
//            Log.i("BakingAppWidgetService", "Adding randomIndex before calling Provider: " + mRecipeIndex);
            if (WordUtil.isOnline(this))
                EasyVocabularyWidget.updateWordAppWidget(this, appWidgetManager, retCursor.getString(0), retCursor.getString(1), appWidgetIds);
        }


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (ACTION_DISPLAY_WORD_MEANING.equals(intent.getAction())) {
                handleActionDisplayWordMeaning();
            }

        }

    }
}




