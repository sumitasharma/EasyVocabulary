package com.example.sumitasharma.easyvocabulary.wordui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizAnswerKeyFragment;

import java.util.HashMap;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.CORRECT_ANSWERS;

public class WordQuizAnswerActivity extends AppCompatActivity {

    private HashMap<String, String> mCorrectAnswers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quiz_answer);
        Timber.i("WordQuizAnswerActivity called");
        mCorrectAnswers = (HashMap<String, String>) getIntent().getSerializableExtra(CORRECT_ANSWERS);
        Bundle argsForWordQuizSummaryFragment = new Bundle();
        argsForWordQuizSummaryFragment.putSerializable(CORRECT_ANSWERS, mCorrectAnswers);
        WordQuizAnswerKeyFragment wordQuizAnswerKeyFragment = new WordQuizAnswerKeyFragment();
        wordQuizAnswerKeyFragment.setArguments(argsForWordQuizSummaryFragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_from_right, R.anim.activity_exit_left);
        transaction.replace(R.id.quiz_correct_answers, wordQuizAnswerKeyFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
