package com.example.sumitasharma.easyvocabulary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.easyvocabulary.R;


public class WordQuizFragment extends Fragment {
    private static final String TAG = WordQuizFragment.class.getSimpleName();
    View rootView;

    public WordQuizFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_word_quiz, container, false);
        return rootView;
    }
}
