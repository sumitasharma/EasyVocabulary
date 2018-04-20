package com.example.sumitasharma.easyvocabulary.services;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sumitasharma.easyvocabulary.util.NotificationHelper;

public class WordNotificationBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            //only enabling one type of notifications for demo purposes
            NotificationHelper.scheduledNotification(context);
        }
    }
}
