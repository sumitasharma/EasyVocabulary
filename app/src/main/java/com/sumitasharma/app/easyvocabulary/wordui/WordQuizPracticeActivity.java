package com.sumitasharma.app.easyvocabulary.wordui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import com.sumitasharma.app.easyvocabulary.R;
import com.sumitasharma.app.easyvocabulary.adapter.CursorPagerAdapter;
import com.sumitasharma.app.easyvocabulary.fragments.WordQuizFragment;
import com.sumitasharma.app.easyvocabulary.loaders.WordQuizLoader;

import java.util.HashMap;

import timber.log.Timber;

import static com.sumitasharma.app.easyvocabulary.util.WordUtil.CORRECT_ANSWERS;
import static com.sumitasharma.app.easyvocabulary.util.WordUtil.QUIZ_LOADER;
import static com.sumitasharma.app.easyvocabulary.util.WordUtil.USER_QUIZ_ANSWERS;

public class WordQuizPracticeActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>, WordQuizFragment.PassUserChoice, WordQuizFragment.SubmitAnswers {

    private HashMap<String, String> mWordAndMeaning = new HashMap<String, String>();
    private HashMap<Long, Boolean> mUserAnswer = new HashMap<>();
    private Cursor mCursor;
    private long mStartId;
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private CursorPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quiz_practice);
        //  Instantiate a ViewPager and a PagerAdapter.
        Timber.i("Inside oncreate WordQuizPractice activity");
        if (savedInstanceState != null) {
            this.mUserAnswer = (HashMap<Long, Boolean>) savedInstanceState.getSerializable(USER_QUIZ_ANSWERS);
            this.mWordAndMeaning = (HashMap<String, String>) savedInstanceState.getSerializable(CORRECT_ANSWERS);
        }
        getSupportLoaderManager().initLoader(QUIZ_LOADER, null, this);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.pager);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return WordQuizLoader.newWordQuizInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        mPager.setAdapter(mPagerAdapter);

        Timber.i("Inside onloadfinished WordQuizPracticeActivity" + mCursor.getCount());

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void callback(long wordId, Boolean answer) {
            if (mUserAnswer.containsKey(wordId)) {
                mUserAnswer.remove(wordId);
                Timber.i("answer :" + answer);
                mUserAnswer.put(wordId, answer);
            } else {
            mUserAnswer.put(wordId, answer);
            Timber.i("answer :" + wordId + answer);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(USER_QUIZ_ANSWERS, mUserAnswer);
        outState.putSerializable(CORRECT_ANSWERS, mWordAndMeaning);
    }

    @Override
    public void submitAnswer(boolean submitAnswer) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable(USER_QUIZ_ANSWERS, mUserAnswer);
        b.putSerializable(CORRECT_ANSWERS, mWordAndMeaning);
        intent.putExtras(b);
        intent.setClass(this, WordQuizSummaryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(USER_QUIZ_ANSWERS, this.mUserAnswer);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {

            super(fm);
            Timber.i("Inside MyPagerAdapter");

        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Timber.i("Inside MyPagerAdapter WordQuizPracticeActivity");
            boolean mLastViewPager;
            if (position >= 3) {
                Timber.i("last page");
                mLastViewPager = true;
            } else {
                Timber.i("not the last page");
                mLastViewPager = false;
            }
            mCursor.moveToPosition(position);
            String word = mCursor.getString(WordQuizLoader.Query.COLUMN_WORD);
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            String meaning = mCursor.getString(WordQuizLoader.Query.COLUMN_MEANING);
            meaning = meaning.substring(0, 1).toUpperCase() + meaning.substring(1).toLowerCase();
            if (mWordAndMeaning == null) {
                mWordAndMeaning = new HashMap<>();
            }
            mWordAndMeaning.put(meaning, word);
            return WordQuizFragment.newInstance(mCursor.getString(WordQuizLoader.Query.COLUMN_MEANING), mCursor.getString(WordQuizLoader.Query.COLUMN_WORD), mCursor.getLong(WordQuizLoader.Query.COLUMN_ID), mLastViewPager);
        }

        @Override
        public int getCount() {
            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    }
}
