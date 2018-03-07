package com.example.sumitasharma.easyvocabulary.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;

import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.ApiService;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.Example;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class GetDataFromDictionary {
    private Context mContext;
    ;

    public GetDataFromDictionary(Context context) {
        mContext = context;
    }

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
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

    public static void wordSearchDictionary(final List<String> words, final Context context) {

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

                        for (String definition : definitions) {
                            // Create new empty ContentValues object
                            ContentValues contentValues = new ContentValues();

                            // Put the task description and selected mPriority into the ContentValues
                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD, word);
                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD_MEANING, String.valueOf((example.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getSenses().get(0).getDefinitions())));
                            String meaning = String.valueOf(example.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getSenses().get(0).getDefinitions());
                            // Insert the content values via a ContentResolver
                            Timber.i("meaning :" + meaning);
                            context.getContentResolver().insert(WordContract.WordsEntry.CONTENT_URI, contentValues);

                        }

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
        List<String> words = new ArrayList<String>();
        words.add("ocean");
        words.add("rich");
        words.add("igloo");
        wordSearchDictionary(words, mContext);
    }

}
