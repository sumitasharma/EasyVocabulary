package com.example.sumitasharma.easyvocabulary.wordui;

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

import timber.log.Timber;

public class WordQuizPracticeActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int QUIZ_LOADER = 101;
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
//        WordQuizFragment wordQuizFragment = new WordQuizFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.quiz_word_frame_layout, wordQuizFragment).commit();
        //  Instantiate a ViewPager and a PagerAdapter.
        Timber.i("Inside oncreate WordQuizPractice activity");
        getSupportLoaderManager().initLoader(QUIZ_LOADER, null, this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);


//        if (savedInstanceState == null) {
//            if (getIntent() != null && getIntent().getData() != null) {
//                mStartId = getIntent().getData();
//                mSelectedItemId = mStartId;
//            }
//        }


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return WordQuizLoader.newWordQuizInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        mPager.setAdapter(mPagerAdapter);
        //mPagerAdapter.notifyDataSetChanged();
//            mCursor.moveToFirst();
        Timber.i("Inside onloadfinished WordQuizPracticeActivity" + mCursor.getCount());
//            // TODO: optimize
//            while (!mCursor.isAfterLast()) {
//                if (mCursor.getLong(WordQuizLoader.Query.COLUMN_ID) == mStartId) {
//                    final int position = mCursor.getPosition();
//                    mPager.setCurrentItem(position, false);
//                    break;
//                }
//                mCursor.moveToNext();
//            }
//            mStartId = 0;

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
            mCursor.moveToPosition(position);
            return WordQuizFragment.newInstance(mCursor.getString(WordQuizLoader.Query.COLUMN_MEANING), mCursor.getString(WordQuizLoader.Query.COLUMN_WORD), mCursor.getLong(WordQuizLoader.Query.COLUMN_ID));
        }

        @Override
        public int getCount() {
            Timber.i("Inside getCount():" + mCursor.getCount());
            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    }
}
