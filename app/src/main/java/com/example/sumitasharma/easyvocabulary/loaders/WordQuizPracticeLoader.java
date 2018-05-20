package com.example.sumitasharma.easyvocabulary.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizFragment;

import java.util.HashMap;

import butterknife.BindView;
import timber.log.Timber;

public class WordQuizPracticeLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private final HashMap<String, String> mWordAndMeaning = new HashMap<>();
    private final Context mContext;
    private final HashMap<Long, Boolean> mUserAnswer = new HashMap<>();
    @BindView(R.id.pager)
    ViewPager mPager;
    private Cursor mCursor;
    private long mStartId;
    private MyPagerAdapter mPagerAdapter;


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


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {

            super(fm);
            Timber.i("Inside MyPagerAdapter");

        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Timber.i("Inside MyPagerAdapter WordQuizPracticeActivity");
            boolean mLastViewPager;
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
            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    }

}
