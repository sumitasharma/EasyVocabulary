package com.example.sumitasharma.easyvocabulary.wordui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.ApiService;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.Example;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.RetroClient;
import com.example.sumitasharma.easyvocabulary.fragments.DictionaryFragment;
import com.example.sumitasharma.easyvocabulary.fragments.ProgressFragment;
import com.example.sumitasharma.easyvocabulary.fragments.WordMainFragment;
import com.example.sumitasharma.easyvocabulary.fragments.WordPracticeFragment;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizFragment;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.PROGRESS_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.WORD_MEANING_CARD_VIEW_IDENTIFIER;
public class MainActivity extends AppCompatActivity implements WordMainFragment.PassCardViewInformation {
    public static final String TAG = MainActivity.class.getSimpleName();
    public int numberOfWordsForPractice;
    public String frequencyOfWordsForPractice;
    public String levelOfWordsForPractice;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Inside onCreate");
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("com.example.sumitasharma.easyvocabulary.CUSTOM_INTENT");
        Log.i(TAG, "Sending Intent");
        //sendBroadcast(intent);
        //  dataFromDictionary();
        setupSharedPreference();
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

    @Override
    public void cardViewInformation(String cardViewNumber) {
        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            WordPracticeFragment wordPracticeFragment = new WordPracticeFragment();
            switch (cardViewNumber) {
                case WORD_MEANING_CARD_VIEW_IDENTIFIER:

                    // FragmentManager fragmentManager = getSupportFragmentManager();
                    // Add the fragment to its container using a FragmentManager and a Transaction
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordPracticeFragment).commit();
                    break;
                case QUIZ_CARD_VIEW_IDENTIFIER:
                    WordQuizFragment wordQuizFragment = new WordQuizFragment();
                    // FragmentManager fragmentManager = getSupportFragmentManager();
                    // Add the fragment to its container using a FragmentManager and a Transaction
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordQuizFragment).commit();
                    break;
                case PROGRESS_CARD_VIEW_IDENTIFIER:
                    ProgressFragment progressFragment = new ProgressFragment();
                    // FragmentManager fragmentManager = getSupportFragmentManager();
                    // Add the fragment to its container using a FragmentManager and a Transaction
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
        } else {
            if (cardViewNumber.equals(WORD_MEANING_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, PracticeWordsActivity.class);
                startActivity(intent);
            } else if (cardViewNumber.equals(QUIZ_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, WordQuizPracticeActivity.class);
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

    public void wordSearchDictionary(final HashMap<String, String> words, final Context context) {
        Timber.i("Inside wordSearchDictionary");
        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        for (final String word : words.keySet()) {
            Call<Example> call = api.getMyJSON(word);
            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    if (response.isSuccessful()) {
                        Example example = response.body();
                        List<String> definitions = example.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getSenses().get(0).getDefinitions();
                        String meaning = null;
                        for (String definition : definitions) {
                            meaning = definition;
                        }
                        // Create new empty ContentValues object
                        ContentValues contentValues = new ContentValues();

                        // Put the task description and selected mPriority into the ContentValues
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD, word);
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD_MEANING, meaning);
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD_LEVEL, words.get(word));
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD_PRACTICED, "true");
                        contentValues.put(WordContract.WordsEntry.COLUMN_LAST_UPDATED, System.currentTimeMillis());
                        // Insert the content values via a ContentResolver
                        Timber.i("meaning :" + meaning);
                        context.getContentResolver().insert(WordContract.WordsEntry.CONTENT_URI, contentValues);
                    } else {
                        Timber.i("Error while fetching the data from API");
                    }

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Timber.i("Error");
                }
            });

        }
    }

    public void dataFromDictionary() {
        HashMap<String, String> words = new HashMap<>();
        words.put("ocean", "Moderate");
        words.put("rich", "Easy");
        words.put("igloo", "Difficult");
        wordSearchDictionary(words, this);
    }

}
