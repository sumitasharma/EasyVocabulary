package com.example.sumitasharma.easyvocabulary.wordui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizSummaryFragment;

import java.util.HashMap;
import java.util.Map;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.USER_QUIZ_ANSWERS;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.USER_TOTAL;

public class WordQuizSummaryActivity extends AppCompatActivity {
    HashMap<Long, Boolean> mUserAnswer = new HashMap<>();
    int mTotal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quiz_summary);
        if (savedInstanceState != null) {
            mUserAnswer = (HashMap<Long, Boolean>) getIntent().getSerializableExtra(USER_QUIZ_ANSWERS);
            for (Map.Entry<Long, Boolean> entry : mUserAnswer.entrySet()) {
                if (entry.getValue()) {
                    mTotal++;
                }
            }
        }

        Bundle argsForWordQuizSummaryFragment = new Bundle();
        argsForWordQuizSummaryFragment.putInt(USER_TOTAL, mTotal);

        WordQuizSummaryFragment wordPracticeFragment = new WordQuizSummaryFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.quiz_answer_summary, wordPracticeFragment).commit();
    }
}
