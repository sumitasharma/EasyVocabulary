package com.example.sumitasharma.easyvocabulary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.easyvocabulary.R;


public class DictionaryFragment extends Fragment {

    private static final String TAG = DictionaryFragment.class.getSimpleName();
    View rootView;

    public DictionaryFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_dictionary, container, false);
        return rootView;
    }
}
