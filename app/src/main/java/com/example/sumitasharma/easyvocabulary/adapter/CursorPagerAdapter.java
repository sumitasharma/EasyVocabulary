package com.example.sumitasharma.easyvocabulary.adapter;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizFragment;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD_ID;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD_MEANING;

public class CursorPagerAdapter extends FragmentStatePagerAdapter {
    private Cursor mCursor;

    public CursorPagerAdapter(FragmentManager fm, Cursor c) {
        super(fm);
        mCursor = c;
    }

    @Override
    public Fragment getItem(int position) {
        if (mCursor.moveToPosition(position)) {
            Timber.i("Inside CursorPager Adapter");
            Bundle arguments = new Bundle();
            arguments.putString(QUIZ_WORD_ID, mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_ID)));
            arguments.putString(QUIZ_WORD, mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
            arguments.putString(QUIZ_WORD_MEANING, mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING)));
            WordQuizFragment fragment = new WordQuizFragment();
            fragment.setArguments(arguments);
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }
}
