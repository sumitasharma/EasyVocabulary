package com.sumitasharma.app.easyvocabulary.services;


import android.app.job.JobParameters;
import android.app.job.JobService;

import timber.log.Timber;

public class WordDbPopulatorJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Timber.i("Inside WordDbPopulatorJobService");
        new GetDataFromDictionary(this, this, jobParameters).dataFromDictionary();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
