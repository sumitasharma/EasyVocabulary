package com.example.sumitasharma.easyvocabulary.wordui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
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
    private HashMap<Long, Boolean> mUserAnswer;
    private HashMap<String, String> mCorrectAnswers;
    private int mTotal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quiz_summary);
        mUserAnswer = new HashMap<>();
        mCorrectAnswers = new HashMap<>();
        Intent intent = getIntent();
        if (intent != null) {
            mUserAnswer = (HashMap<Long, Boolean>) intent.getSerializableExtra(USER_QUIZ_ANSWERS);
            mCorrectAnswers = (HashMap<String, String>) intent.getSerializableExtra(CORRECT_ANSWERS);
        } else {
            mUserAnswer = (HashMap<Long, Boolean>) savedInstanceState.getSerializable(USER_QUIZ_ANSWERS);
            mCorrectAnswers = (HashMap<String, String>) savedInstanceState.getSerializable(CORRECT_ANSWERS);
        }
        if (mUserAnswer != null) {
            for (Map.Entry<Long, Boolean> entry : mUserAnswer.entrySet()) {
                if (entry.getValue()) {
                    mTotal++;
                    Timber.i("Value of boolean " + String.valueOf(entry.getValue()) + mTotal);
                }
            }
        } else
            Timber.i("??");
        Timber.i("Inside WordQuizSummaryActivity");
        Bundle argsForWordQuizSummaryFragment = new Bundle();
        argsForWordQuizSummaryFragment.putString(USER_TOTAL, String.valueOf(mTotal));
        Timber.i("total in activity summary " + mTotal);

        WordQuizSummaryFragment wordQuizSummaryFragment = new WordQuizSummaryFragment();
        wordQuizSummaryFragment.setArguments(argsForWordQuizSummaryFragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_from_right, R.anim.activity_exit_left);
        transaction.replace(R.id.quiz_answer_summary, wordQuizSummaryFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(USER_QUIZ_ANSWERS, mUserAnswer);
        outState.putSerializable(CORRECT_ANSWERS, mCorrectAnswers);
    }
}
