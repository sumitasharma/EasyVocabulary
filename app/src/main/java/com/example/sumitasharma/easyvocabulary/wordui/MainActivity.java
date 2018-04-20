package com.example.sumitasharma.easyvocabulary.wordui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.DictionaryFragment;
import com.example.sumitasharma.easyvocabulary.fragments.ProgressFragment;
import com.example.sumitasharma.easyvocabulary.fragments.WordMainFragment;
import com.example.sumitasharma.easyvocabulary.fragments.WordPracticeFragment;
import com.example.sumitasharma.easyvocabulary.util.NotificationHelper;
import com.example.sumitasharma.easyvocabulary.util.WordsDbUtil;
import com.facebook.stetho.Stetho;

import java.util.HashMap;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.PROGRESS_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.WORD_MEANING_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.isOnline;

public class MainActivity extends AppCompatActivity implements WordMainFragment.PassCardViewInformation {
    public static final String TAG = MainActivity.class.getSimpleName();
    HashMap<Long, Boolean> mUserAnswer = new HashMap<>();
    HashMap<String, String> mCorrectAnswers = new HashMap<>();
    long interval;
    private int numberOfWordsForPractice;
    private String frequencyOfWordsForPractice;
    private String levelOfWordsForPractice;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize stetho
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        Log.i(TAG, "Inside onCreate");
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("com.example.sumitasharma.easyvocabulary.CUSTOM_INTENT");
        Log.i(TAG, "Sending Intent");
        //sendBroadcast(intent);
        /* Delete the table */
        //  Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        //  getContentResolver().delete(loaderUri,null,null);
        /* Insert the table */
        //new WordsDbUtil(this).readWordsFromAssets();
        WordsDbUtil wordsDbUtil = new WordsDbUtil(this);
        if (!wordsDbUtil.isDatabasePopulated()) {
            Timber.i("Database is not populated, populating it");
            wordsDbUtil.populateDatabase();
        }

        setupSharedPreference();

        //Start the Word Notification Service

//        Intent notifyIntent = new Intent(this,WordNotificationBootReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast
//                (this, 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                60*1000, pendingIntent);
//


        NotificationHelper.scheduledNotification(this, frequencyOfWordsForPractice);
        NotificationHelper.enableBootReceiver(this);

        // this.sendBroadcast(new Intent("android.intent.action.BOOT_COMPLETED"));
        Bundle bundle = this.getIntent().getExtras();

        //Checking if it is a tablet
        mTwoPane = findViewById(R.id.words_tablet_linear_layout) != null;

        WordMainFragment wordMainFragment = new WordMainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.word_main_fragment, wordMainFragment).commit();


        if (mTwoPane) {
            WordPracticeFragment wordPracticeFragment = new WordPracticeFragment();
            fragmentManager = getSupportFragmentManager();
            // Add the fragment to its container using a FragmentManager and a Transaction
            fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordPracticeFragment).commit();
        }
    }

//    private void populateDB(){
//        new WordDbPopulatorService().execute();
//    }

    private void setupSharedPreference() {
        Log.i(TAG, "Inside setupSharedPreference");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        numberOfWordsForPractice = Integer.parseInt(sharedPreferences.getString(getResources().getString(R.string.number_of_words_key), "1"));
        frequencyOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.frequency_of_words_key), "Daily"));
        levelOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.level_of_words_for_practice_key), "Easy"));
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

    protected void onResume() {
        super.onResume();
        if (!isOnline(this)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.word_main_fragment), R.string.internet_connectivity,
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.BLUE);
            //Toast.makeText(mContext, "Kindly check your Internet Connectivity", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void cardViewInformation(String cardViewNumber) {
        if (mTwoPane) { // Handle Tablet devices
            FragmentManager fragmentManager = getSupportFragmentManager();
            WordPracticeFragment wordPracticeFragment = new WordPracticeFragment();
            switch (cardViewNumber) {
                case WORD_MEANING_CARD_VIEW_IDENTIFIER:

                    // FragmentManager fragmentManager = getSupportFragmentManager();
                    // Add the fragment to its container using a FragmentManager and a Transaction
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordPracticeFragment).commit();
                    break;
                case QUIZ_CARD_VIEW_IDENTIFIER:
//                    WordQuizFragment wordQuizFragment = new WordQuizFragment();
//                    // FragmentManager fragmentManager = getSupportFragmentManager();
//                    // Add the fragment to its container using a FragmentManager and a Transaction
//                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordQuizFragment).commit();
                    Intent intent = new Intent();
                    intent.setClass(this, WordQuizPracticeActivity.class);
                    Timber.i("Inside WordQuizPractice selected");
                    startActivity(intent);
                    break;
                case PROGRESS_CARD_VIEW_IDENTIFIER:
                    ProgressFragment progressFragment = new ProgressFragment();
                    // FragmentManager fragmentManager = getSupportFragmentManager();
                    // Add the fragment to its container using a FragmentManager and a Transaction
                    Timber.i("Entering Progress Fragment");
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, progressFragment).commit();
                    break;
                case DICTIONARY_CARD_VIEW_IDENTIFIER:
                    DictionaryFragment dictionaryFragment = new DictionaryFragment();
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, dictionaryFragment).commit();
                    break;
                default:
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordPracticeFragment).commit();
                    break;

            }
        } else {  // Handle mobile devices
            if (cardViewNumber.equals(WORD_MEANING_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, PracticeWordsActivity.class);
                startActivity(intent);
            } else if (cardViewNumber.equals(QUIZ_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, WordQuizPracticeActivity.class);
                Timber.i("Inside WordQuizPractice selected");
                startActivity(intent);
            } else if (cardViewNumber.equals(PROGRESS_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, ProgressActivity.class);
                startActivity(intent);
            } else if (cardViewNumber.equals(DICTIONARY_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, DictionaryActivity.class);
                startActivity(intent);
            }

        }
    }

}
