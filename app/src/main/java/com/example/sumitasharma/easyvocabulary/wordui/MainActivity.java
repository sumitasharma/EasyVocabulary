package com.example.sumitasharma.easyvocabulary.wordui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sumitasharma.easyvocabulary.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public int numberOfWordsForPractice;
    public String frequencyOfWordsForPractice;
    public String levelOfWordsForPractice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Inside onCreate");
        setContentView(R.layout.activity_main);
        setupSharedPreference();
        //        if(key.equals(getString(R.string.number_of_words_key))) {
//            numberOfWordsForPractice = sharedPreferences.getInt(getString(R.string.number_of_words_key), 1);}

    }

    // Setting the sharedPreferences for Words number, frequency and level
    private void setupSharedPreference() {
        Log.i(TAG, "Inside setupSharedPreference");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i(TAG, "Inside setupSharedPreference after getDefaultSharedPreferences");
        Log.i(TAG, "Inside setupSharedPreference" + numberOfWordsForPractice + frequencyOfWordsForPractice + levelOfWordsForPractice);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        Log.i(TAG, "Inside onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "Inside onOptionsItemSelected");
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intentMenuSetting = new Intent(this, PreferenceActivity.class);
            Log.i(TAG, "Intent started");
            startActivity(intentMenuSetting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
