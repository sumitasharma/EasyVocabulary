package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.adapter.PracticeWordsAdapter;
import com.example.sumitasharma.easyvocabulary.data.WordContract;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.STATE_WORD_PRACTICE;

public class WordPracticeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = WordPracticeFragment.class.getSimpleName();
    private final static int LOADER_ID = 101;
    private final LoaderManager.LoaderCallbacks<Cursor> callback = WordPracticeFragment.this;
    View rootView;
    RecyclerView mWordPracticeRecyclerView;
    Cursor mData;
    private int mLoaderId;
    private Context mContext = getContext();
    private PracticeWordsAdapter mAdapter = null;
    private LinearLayoutManager mLinearLayoutManager;
    private PassTheState mPassTheSate;

    public WordPracticeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.i("Inside WordPracticeFragment onCreateView");
        rootView = inflater.inflate(R.layout.fragment_word_practice, container, false);
        mWordPracticeRecyclerView = rootView.findViewById(R.id.recycler_view_practice_words);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mWordPracticeRecyclerView.setLayoutManager(mLinearLayoutManager);
        if (savedInstanceState != null) {
            Timber.i("onCreate Fragment called");
            mWordPracticeRecyclerView.setAdapter(mAdapter);
        } else
            initializeLoader(LOADER_ID, getContext());
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("Inside WordPracticeFragment onCreate");
        setRetainInstance(true);
    }

    private void initializeLoader(int loaderId, Context context) {
        Timber.i("Inside initializeLoader");
        this.mLoaderId = loaderId;
        this.mContext = context;
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> wordPracticeLoader = loaderManager.getLoader(mLoaderId);
        if (wordPracticeLoader == null) {
            Timber.i("Inside initLoader");
            loaderManager.initLoader(mLoaderId, null, callback);
        } else {
            Timber.i("Inside restartLoader");
            loaderManager.restartLoader(mLoaderId, null, callback);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int numberOfWordsForPractice;
        String frequencyOfWordsForPractice;
        String levelOfWordsForPractice;
        Timber.i("Inside oncreateloader WordPracticeFragment");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        numberOfWordsForPractice = Integer.parseInt(sharedPreferences.getString(getResources().getString(R.string.number_of_words_key), "4"));
        frequencyOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.frequency_of_words_key), "Daily"));
        levelOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.level_of_words_for_practice_key), "Easy"));
        //Setup the uri that will get the data I need from my ContentProvider
        Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        String[] level = {levelOfWordsForPractice, String.valueOf(numberOfWordsForPractice)};

        String where = WordContract.WordsEntry.COLUMN_WORD_LEVEL + "=? and " + WordContract.WordsEntry.COLUMN_WORD_PRACTICED + "!= 1";
        String sortOrder = "RANDOM() LIMIT ?";
        CursorLoader cursorLoader = new CursorLoader(mContext, loaderUri, null, where, level, sortOrder);
        //and get a CursorLoader from my ContentProvider
        return cursorLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mData = data;
        mAdapter = new PracticeWordsAdapter(mContext, data);
        Timber.i("Setting PracticeWordsAdapter for recycler view");
        mWordPracticeRecyclerView.setAdapter(mAdapter);
        mAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_WORD_PRACTICE, "original");
        mPassTheSate.passTheSavedState("original");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPassTheSate = (PassTheState) context;
    }

    public interface PassTheState {
        void passTheSavedState(String state);
    }
}
