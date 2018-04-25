package com.example.sumitasharma.easyvocabulary.loaders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizFragment;
import com.example.sumitasharma.easyvocabulary.wordui.WordQuizSummaryActivity;

import java.util.HashMap;

import butterknife.BindView;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.CORRECT_ANSWERS;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.USER_QUIZ_ANSWERS;

public class WordQuizPracticeLoader implements LoaderManager.LoaderCallbacks<Cursor>, WordQuizFragment.PassUserChoice, WordQuizFragment.SubmitAnswers {
    HashMap<String, String> mWordAndMeaning = new HashMap<String, String>();
    boolean mLastViewPager;
    @BindView(R.id.pager)
    ViewPager mPager;
    Context mContext;
    private Cursor mCursor;
    private long mStartId;
    private MyPagerAdapter mPagerAdapter;
    private HashMap<Long, Boolean> mUserAnswer = new HashMap<Long, Boolean>();


    public WordQuizPracticeLoader(Context context) {
        this.mContext = context;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return WordQuizLoader.newWordQuizInstance(mContext);
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
            Timber.i("answer :" + answer);
        }
    }

    @Override
    public void submitAnswer(boolean submitAnswer) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable(USER_QUIZ_ANSWERS, mUserAnswer);
        b.putSerializable(CORRECT_ANSWERS, mWordAndMeaning);
        intent.putExtras(b);
        intent.setClass(mContext, WordQuizSummaryActivity.class);
        mContext.startActivity(intent);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {

            super(fm);
            Timber.i("Inside MyPagerAdapter");

        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Timber.i("Inside MyPagerAdapter WordQuizPracticeActivity");
            if (position >= 2) {
                Timber.i("last page");
                mLastViewPager = true;
            } else {
                Timber.i("not the last page");
                mLastViewPager = false;
            }
            mCursor.moveToPosition(position);
            mWordAndMeaning.put(mCursor.getString(WordQuizLoader.Query.COLUMN_MEANING), mCursor.getString(WordQuizLoader.Query.COLUMN_WORD));
            return WordQuizFragment.newInstance(mCursor.getString(WordQuizLoader.Query.COLUMN_MEANING), mCursor.getString(WordQuizLoader.Query.COLUMN_WORD), mCursor.getLong(WordQuizLoader.Query.COLUMN_ID), mLastViewPager);
        }

        @Override
        public int getCount() {
            //  Timber.i("Inside getCount():" + mCursor.getCount());
            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    }

}
