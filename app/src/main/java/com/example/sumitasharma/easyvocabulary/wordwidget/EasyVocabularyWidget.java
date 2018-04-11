package com.example.sumitasharma.easyvocabulary.wordwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.wordui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class EasyVocabularyWidget extends AppWidgetProvider {
    TextView mWord;
    TextView mMeaning;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String mWord, String mMeaning,
                                int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.easy_vocabulary_widget);

        views.setTextViewText(R.id.wordwidget_text, mWord.substring(0, 1).toUpperCase() + mWord.substring(1).toLowerCase());
        views.setTextViewText(R.id.meaningwidget_text, mMeaning.substring(0, 1).toUpperCase() + mMeaning.substring(1).toLowerCase());

        //Create and Launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //Click Handler on Widget to launch Pending Intent
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        views.setOnClickPendingIntent(R.id.wordwidget_text, pendingIntent);
        views.setOnClickPendingIntent(R.id.meaningwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateWordAppWidget(Context context, AppWidgetManager appWidgetManager, String mWord, String mMeaning,
                                    int[] appWidgetIds) {


        Log.i("BakingAppWidgetProvider", "Inside updateBakingAppWidget");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, mWord, mMeaning, appWidgetId);
        }


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        EasyVocabularyService.startActionUpdateWordApp(context);


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

