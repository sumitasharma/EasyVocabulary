package com.example.sumitasharma.easyvocabulary.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.example.sumitasharma.easyvocabulary.R;

public class PreferenceSettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = PreferenceSettingsFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String numberOfWords = sharedPref.getString(getString(R.string.number_of_words_key), "1");
        android.support.v7.preference.Preference connectionPrefNumber = findPreference(getString(R.string.number_of_words_key));
        connectionPrefNumber.setSummary(numberOfWords);
        String frequencyOfWords = sharedPref.getString(getString(R.string.frequency_of_words_key), getString(R.string.daily));
        android.support.v7.preference.Preference connectionPrefFrequency = findPreference(getString(R.string.frequency_of_words_key));
        connectionPrefFrequency.setSummary(frequencyOfWords);
        String levelOfWords = sharedPref.getString(getString(R.string.level_of_words_for_practice_key), getString(R.string.easy));
        android.support.v7.preference.Preference connectionPrefLevel = findPreference(getString(R.string.level_of_words_for_practice_key));
        connectionPrefLevel.setSummary(levelOfWords);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        SharedPreferences sharedPreferencesScreen = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        if (s.equals(getString(R.string.number_of_words_key))) {
            android.support.v7.preference.Preference connectionPref = findPreference(s);
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(s, ""));
        }
        if (s.equals(getString(R.string.frequency_of_words_key))) {
            android.support.v7.preference.Preference connectionPref = findPreference(s);
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(s, ""));
        }
        if (s.equals(getString(R.string.level_of_words_for_practice_key))) {
            android.support.v7.preference.Preference connectionPref = findPreference(s);
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(s, ""));
        }
    }

    private void setPreferenceSummary(android.support.v7.preference.Preference preference, String value) {
        if (preference instanceof android.support.v7.preference.ListPreference) {
            Log.i(TAG, "Inside setPreferenceSummary");
            android.support.v7.preference.ListPreference listPreference = (android.support.v7.preference.ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            Log.i(TAG, "Inside setPreferenceSummary" + prefIndex);
            if (prefIndex >= 0) {
                Log.i(TAG, "Inside setPreference" + listPreference.getEntries()[prefIndex]);

                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
