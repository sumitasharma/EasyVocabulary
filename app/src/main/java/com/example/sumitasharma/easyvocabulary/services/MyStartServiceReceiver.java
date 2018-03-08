package com.example.sumitasharma.easyvocabulary.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.services.GetDataFromDictionary.scheduleJob;

public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("Inside onReceive");
        scheduleJob(context);
    }
}
