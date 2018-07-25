package com.sumitasharma.app.easyvocabulary.util;


import android.content.Context;
import android.util.Log;
import android.view.View;

import com.sumitasharma.app.easyvocabulary.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import timber.log.Timber;


public class AdsDisplay {
    public static void displayAds(View view, Context context) {
        Log.i("AdDisplay", "Before Ads in AdDisplay");
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        Timber.i("Added Ads");
    }
}
