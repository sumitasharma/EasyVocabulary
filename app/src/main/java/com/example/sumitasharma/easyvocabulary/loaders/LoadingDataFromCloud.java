package com.example.sumitasharma.easyvocabulary.loaders;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.TimingLogger;

import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.util.WordsDbUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class LoadingDataFromCloud extends AsyncTaskLoader {

    final TimingLogger mTimingsLogger = new TimingLogger("LoadingDataFromCloud", "loadInBackground");
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


        mTimingsLogger.addSplit("Total Time for All documents");

        db.collection("dictionary")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
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
                                // Insert the content values via a ContentResolver
                                mContext.getContentResolver().insert(WordContract.WordsEntry.CONTENT_URI, contentValues);
                                mTimingsLogger.addSplit("Single Document");

                            }
                        } else {
                            Timber.i("Error getting documents.", task.getException());
                        }
                    }
                });
        mTimingsLogger.dumpToLog();
        return null;
    }
}

