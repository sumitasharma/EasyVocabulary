package com.sumitasharma.app.easyvocabulary.wordwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.sumitasharma.app.easyvocabulary.R;
import com.sumitasharma.app.easyvocabulary.data.WordContract;
import com.sumitasharma.app.easyvocabulary.wordui.MainActivity;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class EasyVocabularyWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int numberOfWordsForPractice = Integer.parseInt(sharedPreferences.getString(context.getResources().getString(R.string.number_of_words_key), "1"));
        String frequencyOfWordsForPractice = (sharedPreferences.getString(context.getResources().getString(R.string.frequency_of_words_key), "sharedpreference"));
        String levelOfWordsForPractice = (sharedPreferences.getString(context.getResources().getString(R.string.level_of_words_for_practice_key), "shared"));
        String[] projection = {WordContract.WordsEntry.COLUMN_WORD, WordContract.WordsEntry.COLUMN_WORD_MEANING};
        String sort_order = "RANDOM() LIMIT 1";
        Cursor retCursor = context.getContentResolver().query(WordContract.WordsEntry.CONTENT_URI,
                projection,
                null,
                null,
                sort_order);
        if (retCursor.moveToFirst() && retCursor.getCount() >= 1) {

            Timber.i("word in widget : " + retCursor.getString(0));
            Timber.i("word meaning in widget : " + retCursor.getString(1));
            final int N = appWidgetIds.length;

            // Perform this loop procedure for each App Widget that belongs to this provider
            for (int appWidgetId : appWidgetIds) {
                // Create an Intent to launch ExampleActivity
                Intent intent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.easy_vocabulary_widget);
                views.setOnClickPendingIntent(R.id.meaningwidget_text, pendingIntent);
                views.setOnClickPendingIntent(R.id.wordwidget_text, pendingIntent);
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
                views.setOnClickPendingIntent(R.id.appwidget_hint_text, pendingIntent);
                views.setOnClickPendingIntent(R.id.widget_linear_layout, pendingIntent);
                views.setTextViewText(R.id.wordwidget_text, retCursor.getString(0).substring(0, 1).toUpperCase() + retCursor.getString(0).substring(1).toLowerCase());
                views.setTextViewText(R.id.meaningwidget_text, retCursor.getString(1).substring(0, 1).toUpperCase() + retCursor.getString(1).substring(1).toLowerCase());

                // Tell the AppWidgetManager to perform an update on the current app widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
        retCursor.close();
    }
}


