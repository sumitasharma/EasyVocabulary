package com.example.sumitasharma.easyvocabulary.services;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
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

public class GetDataFromDictionary {
    private Context mContext;
    private JobService mJobService;
    private JobParameters mJobParameters;
    private HashMap<String, String> words = new HashMap<>();

    public GetDataFromDictionary(WordDbPopulatorService wordDbPopulatorService, Context context, JobParameters jobParameters) {
        mContext = context;
        mJobParameters = jobParameters;
        mJobService = wordDbPopulatorService;
    }

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        Timber.i("Inside scheduleJob");
        ComponentName serviceComponent = new ComponentName(context, WordDbPopulatorService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

    public void wordSearchDictionary(final HashMap<String, String> words, final Context context) {
        Timber.i("Inside populateDatabase");
        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        for (int i = 0; i < words.size(); i++) {
            Call<Example> call = api.getMyJSON(words.get(i));
            final String word = words.get(i);
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
                        // Insert the content values via a ContentResolver
                            Timber.i("meaning :" + meaning);
                            context.getContentResolver().insert(WordContract.WordsEntry.CONTENT_URI, contentValues);


                    } else {
                        Timber.i("Error while fetching the data from API");
                    }
                    mJobService.jobFinished(mJobParameters, true);
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
        //   populateDatabase(words, mContext);
    }
}
