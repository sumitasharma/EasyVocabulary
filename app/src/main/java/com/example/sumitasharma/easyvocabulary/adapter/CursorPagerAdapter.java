package com.example.sumitasharma.easyvocabulary.adapter;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.fragments.WordQuizFragment;

public class CursorPagerAdapter extends FragmentStatePagerAdapter {
    private Cursor mCursor;

    public CursorPagerAdapter(FragmentManager fm, Cursor c) {
        super(fm);
        mCursor = c;
    }

    @Override
    public Fragment getItem(int position) {
        if (mCursor.moveToPosition(position)) {
            Bundle arguments = new Bundle();
            arguments.putString(WordQuizFragment.WORD_ID, mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING)));
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
