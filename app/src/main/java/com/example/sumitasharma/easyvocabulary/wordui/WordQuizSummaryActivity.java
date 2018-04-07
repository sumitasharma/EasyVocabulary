package com.example.sumitasharma.easyvocabulary.wordui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizSummaryFragment;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.CORRECT_ANSWERS;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.USER_QUIZ_ANSWERS;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.USER_TOTAL;

public class WordQuizSummaryActivity extends AppCompatActivity implements WordQuizSummaryFragment.CorrectAnswers {
    HashMap<Long, Boolean> mUserAnswer = new HashMap<>();
    HashMap<String, String> mCorrectAnswers = new HashMap<>();
    int mTotal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quiz_summary);
            mUserAnswer = (HashMap<Long, Boolean>) getIntent().getSerializableExtra(USER_QUIZ_ANSWERS);
        mCorrectAnswers = (HashMap<String, String>) getIntent().getSerializableExtra(CORRECT_ANSWERS);
        for (Map.Entry<Long, Boolean> entry : mUserAnswer.entrySet()) {
            if (entry.getValue()) {
                mTotal++;
                Timber.i("Value of boolean " + String.valueOf(entry.getValue()) + mTotal);
            }

        }
        Timber.i("Inside WordQuizSummaryActivity");
        Bundle argsForWordQuizSummaryFragment = new Bundle();
        argsForWordQuizSummaryFragment.putString(USER_TOTAL, String.valueOf(mTotal));

        WordQuizSummaryFragment wordQuizSummaryFragment = new WordQuizSummaryFragment();
        wordQuizSummaryFragment.setArguments(argsForWordQuizSummaryFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.quiz_answer_summary, wordQuizSummaryFragment).commit();
    }

    @Override
    public void callCorrectAnswerActivity() {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable(CORRECT_ANSWERS, mCorrectAnswers);
        intent.putExtras(b);
        Timber.i("calling WordQuizAnswerActivity");
        intent.setClass(this, WordQuizAnswerActivity.class);
        startActivity(intent);
    }
}
