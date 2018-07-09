package com.example.sumitasharma.easyvocabulary.loaders;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.util.WordsDbUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class LoadingDataFromCloud extends AsyncTaskLoader {

    // final TimingLogger mTimingsLogger = new TimingLogger("LoadingDataFromCloud", "loadInBackground");
    Context mContext;


    public LoadingDataFromCloud(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Nullable
    @Override
    public Object loadInBackground() {
        Timber.i("Inside loadInBackground");

        WordsDbUtil wordsDbUtil = new WordsDbUtil(mContext);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        // mTimingsLogger.addSplit("Total Time for All documents");
        Timber.i("Loader: Setting up Query at: " + Calendar.getInstance().getTime());
        Query limitWords = db.collection("dictionary").limit(100);


        limitWords.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshots) {
                        if (!querySnapshots.isEmpty()) {
                            Timber.i("Loader: Inside onSuccess at: " + Calendar.getInstance().getTime());
                            // int documentCount = 0;
                            List<ContentValues> contentValuesList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : querySnapshots) {
                                Timber.i(document.getId() + " => " + document.getData().get("word"));
                                // Create new empty ContentValues object
                                ContentValues contentValues = new ContentValues();

                                // Put the task description and selected mPriority into the ContentValues
                                contentValues.put(WordContract.WordsEntry.COLUMN_WORD, (String) document.getData().get("word"));
                                contentValues.put(WordContract.WordsEntry.COLUMN_WORD_MEANING, (String) document.getData().get("wordMeaning"));
                                contentValues.put(WordContract.WordsEntry.COLUMN_WORD_LEVEL, (String) document.getData().get("wordLevel"));
                                contentValues.put(WordContract.WordsEntry.COLUMN_WORD_PRACTICED, false);
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String date = dateFormat.format(new Date());
                                contentValues.put(WordContract.WordsEntry.COLUMN_LAST_UPDATED, date);

                                contentValuesList.add(contentValues);
                                // documentCount++;

                            }
                            ContentValues[] bulkInsertArray = new ContentValues[contentValuesList.size()];
                            Timber.i("Size of dictionary: " + contentValuesList.size());
                            // Insert the content values via a ContentResolver
                            mContext.getContentResolver().bulkInsert(WordContract.WordsEntry.CONTENT_URI, contentValuesList.toArray(bulkInsertArray));
                        } else {
                            Timber.i("Error getting documents. QuerySnapshots is empty");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Timber.i("Loader: Inside onFailure at: " + Calendar.getInstance().getTime());
                        Timber.i("Got Exception while listening" + e.toString() + e.getMessage());
                    }
                });
        // mTimingsLogger.dumpToLog();
        return null;
    }
}
