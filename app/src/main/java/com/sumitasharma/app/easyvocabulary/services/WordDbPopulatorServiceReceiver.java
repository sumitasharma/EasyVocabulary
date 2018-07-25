package com.sumitasharma.app.easyvocabulary.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import timber.log.Timber;

//import static com.example.sumitasharma.easyvocabulary.services.GetDataFromDictionary.scheduleJob;

public class WordDbPopulatorServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Timber.i("Inside MyStartServiceReceiver");
        Log.i("MyStartServiceReceiver", "Inside MyStartServiceReceiver onReceive");

        Toast.makeText(context, "Toast from WordDBPopulatorServiceReceiver", Toast.LENGTH_LONG).show();
        // scheduleJob(context);

    }
}
