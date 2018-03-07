package com.example.sumitasharma.easyvocabulary.services;


import android.app.job.JobParameters;
import android.app.job.JobService;

public class WordDbPopulatorService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new GetDataFromDictionary(this).dataFromDictionary();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
