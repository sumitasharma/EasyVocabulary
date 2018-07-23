package com.example.sumitasharma.easyvocabulary.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;

import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.ApiService;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.Example;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.RetroClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class WordsDbUtil {
    private final Context mContext;

    public WordsDbUtil(Context context) {
        this.mContext = context;
    }

    public void populateDatabase() {
        Timber.i("Inside populateDatabase");
        final HashMap<String, String> words = readWordsFromAssets();
        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        // Calling JSON

        for (final String word : words.keySet()) {
            Call<List<Example>> call = api.getMyJSON(word);

            // Enqueue Callback will be call when get response...

            call.enqueue(new Callback<List<Example>>() {
                @Override
                public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                    if (response.isSuccessful()) {
                        List<Example> exampleList = response.body();
                        Example example = exampleList.get(0);
                        String meaning = example.getDefinition();
                        if (meaning.isEmpty()) {
                            Timber.i("Unable to find word in dictionary" + word);
                            return;
                        }
                        String longestDefinition = meaning;
                        int longestDefinitionIndex = 0;
                        for (int i = 1; i < exampleList.size(); i++) {
                            example = exampleList.get(i);
                            if (example.getDefinition().length() >= longestDefinition.length()) {
                                longestDefinition = example.getDefinition();
                                longestDefinitionIndex = i;
                            }
                        }
                        if (exampleList.get(longestDefinitionIndex).getType().equals("abbreviation") || exampleList.get(longestDefinitionIndex).getType() == null) {
                            return;
                        }
                        meaning = exampleList.get(longestDefinitionIndex).getDefinition();

                        // Create new empty ContentValues object
                        ContentValues contentValues = new ContentValues();

                        // Put the task description and selected mPriority into the ContentValues
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD, word);
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD_MEANING, meaning);
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD_LEVEL, words.get(word));
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD_PRACTICED, false);
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD_TYPE, exampleList.get(longestDefinitionIndex).getType());
                        contentValues.put(WordContract.WordsEntry.COLUMN_WORD_EXAMPLE, exampleList.get(longestDefinitionIndex).getExample());
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String date = dateFormat.format(new Date());
                        contentValues.put(WordContract.WordsEntry.COLUMN_LAST_UPDATED, date);
                        // Insert the content values via a ContentResolver
                        Timber.i("meaning :" + meaning);
                        mContext.getContentResolver().insert(WordContract.WordsEntry.CONTENT_URI, contentValues);
                    } else {
                        Timber.i("Error while fetching the data from API");
                    }

                }

                @Override
                public void onFailure(Call<List<Example>> call, Throwable t) {
                    Timber.i("Error");
                }
            });

        }
    }

    public boolean isDatabasePopulated() {
        Cursor cursor = mContext.getContentResolver().query(WordContract.WordsEntry.CONTENT_URI, null, null, null, null);
        return cursor.getCount() > 0;
    }

    private HashMap<String, String> readWordsFromAssets() {
        Timber.i("dataFrom Dictionary");
        HashMap<String, String> words = new HashMap<>();

        AssetManager assetManager = mContext.getAssets();
        // To load text file
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_easy_init"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                Timber.i("Easy words :" + mLine);
                words.put(mLine, "Easy");
            }
        } catch (IOException e) {
            //log the exception
            Timber.i("Exception during reading of the Assets file");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_moderate_init"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                words.put(mLine, "Moderate");
                Timber.i("Moderate Words " + mLine);
            }
        } catch (IOException e) {
            //log the exception
            Timber.i("Exception during reading of the Assets file");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_difficult_init"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                Timber.i("Difficult words " + mLine);
                words.put(mLine, "Difficult");
            }
        } catch (IOException e) {
            //log the exception
            Timber.i("Exception during reading of the Assets file");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return words;
    }

}
