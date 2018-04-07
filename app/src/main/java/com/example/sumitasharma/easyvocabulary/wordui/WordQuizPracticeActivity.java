package com.example.sumitasharma.easyvocabulary.wordui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.adapter.CursorPagerAdapter;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizFragment;
import com.example.sumitasharma.easyvocabulary.loaders.WordQuizLoader;

import java.util.HashMap;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.USER_QUIZ_ANSWERS;

public class WordQuizPracticeActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>, WordQuizFragment.PassUserChoice, WordQuizFragment.SubmitAnswers {

    private final int QUIZ_LOADER = 101;
    HashMap<String, String> mWordAndMeaning = new HashMap<String, String>();
    boolean mLastViewPager;
    private Cursor mCursor;
    private long mStartId;
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    private HashMap<Long, Boolean> mUserAnswer = new HashMap<Long, Boolean>();
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
        getSupportLoaderManager().initLoader(QUIZ_LOADER, null, this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
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
            mUserAnswer.put(wordId, answer);
        } else {
            mUserAnswer.put(wordId, answer);
        }
    }

    @Override
    public void submitAnswer(boolean submitAnswer) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable(USER_QUIZ_ANSWERS, mUserAnswer);
        intent.putExtras(b);
        intent.setClass(this, WordQuizAnswerActivity.class);
        startActivity(intent);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {

            super(fm);
            Timber.i("Inside MyPagerAdapter");

        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Timber.i("Inside MyPagerAdapter WordQuizPracticeActivity");
            if (mPager.getAdapter().getCount() >= 4)
                mLastViewPager = true;
            else
                mLastViewPager = false;
            mCursor.moveToPosition(position);
            mWordAndMeaning.put(mCursor.getString(WordQuizLoader.Query.COLUMN_MEANING), mCursor.getString(WordQuizLoader.Query.COLUMN_WORD));
            return WordQuizFragment.newInstance(mCursor.getString(WordQuizLoader.Query.COLUMN_MEANING), mCursor.getString(WordQuizLoader.Query.COLUMN_WORD), mCursor.getLong(WordQuizLoader.Query.COLUMN_ID), mLastViewPager);
        }

        @Override
        public int getCount() {
            Timber.i("Inside getCount():" + mCursor.getCount());
            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    }
}
