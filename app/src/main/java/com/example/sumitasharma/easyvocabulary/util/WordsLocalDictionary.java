package com.example.sumitasharma.easyvocabulary.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;

import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.ApiService;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.Example;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.RetroClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class WordsLocalDictionary {
    private Context mContext;

    public WordsLocalDictionary(Context context) {
        this.mContext = context;
    }

    public static void wordSearchDictionary(final HashMap<String, String> words, final Context context) {
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
        Timber.i("dataFrom Dictionary");
        HashMap<String, String> words = new HashMap<>();

        AssetManager assetManager = mContext.getAssets();
        // To load text file
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_easy"), "UTF-8"));

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
                    new InputStreamReader(mContext.getAssets().open("words_moderate"), "UTF-8"));

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
                    new InputStreamReader(mContext.getAssets().open("words_difficult"), "UTF-8"));

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
        //   wordSearchDictionary(words, mContext);
    }

}
