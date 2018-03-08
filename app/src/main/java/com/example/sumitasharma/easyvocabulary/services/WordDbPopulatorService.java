package com.example.sumitasharma.easyvocabulary.services;


import android.app.job.JobParameters;
import android.app.job.JobService;

import timber.log.Timber;

public class WordDbPopulatorService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Timber.i("Inside WordDbPopulator");
        new GetDataFromDictionary(this, this, jobParameters).dataFromDictionary();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
