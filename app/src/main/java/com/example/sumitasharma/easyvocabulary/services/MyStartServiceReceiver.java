package com.example.sumitasharma.easyvocabulary.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.sumitasharma.easyvocabulary.services.GetDataFromDictionary.scheduleJob;

public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        scheduleJob(context);
    }
}
