package com.example.sumitasharma.easyvocabulary.services;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sumitasharma.easyvocabulary.util.NotificationHelper;

import timber.log.Timber;

public class WordNotificationBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            //only enabling one type of notifications for demo purposes
            Timber.i("Inside WordNotificationBootReceiver");
            NotificationHelper.scheduledNotification(context);
        }
    }
}
