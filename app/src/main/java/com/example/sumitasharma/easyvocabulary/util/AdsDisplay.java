package com.example.sumitasharma.easyvocabulary.util;


import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.sumitasharma.easyvocabulary.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import timber.log.Timber;


public class AdsDisplay {
    public static void displayAds(View view, Context context) {
        Log.i("AdDisplay", "Before Ads in AdDisplay");
        AdView mAdView = view.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        //mAdView.setAdSize(AdSize.SMART_BANNER);
        // mAdView.setAdUnitId(context.getResources().getString(R.string.banner_ad_unit_id));
        mAdView.loadAd(adRequest);
        Timber.i("Added Ads");
    }
}
