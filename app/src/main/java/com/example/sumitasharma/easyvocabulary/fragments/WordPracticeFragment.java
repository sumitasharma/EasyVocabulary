package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

public class WordPracticeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = WordPracticeFragment.class.getSimpleName();
    private final LoaderManager.LoaderCallbacks<Cursor> callback = WordPracticeFragment.this;
    // This is the Adapter being used to display the list's data.
    View rootView;
    // If non-null, this is the current filter the user has provided.
    String mCurFilter;
    RecyclerView mWordPracticeRecyclerView;
    private int mLoaderId = 101;
    private Context mContext = getContext();
    private PracticeWordsAdapter mAdapter;

    public WordPracticeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.i("Inside CreateView WordPracticeFragment");
        rootView = inflater.inflate(R.layout.fragment_word_practice, container, false);
        mWordPracticeRecyclerView = rootView.findViewById(R.id.recycler_view_practice_words);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mWordPracticeRecyclerView.setLayoutManager(mLinearLayoutManager);
        initializeLoader(mLoaderId, getContext());
        return rootView;
    }

    private void initializeLoader(int loaderId, Context context) {
        // Timber.i( "Inside initializeLoader");
        Timber.i("Inside initializeLoader");
        this.mLoaderId = loaderId;
        this.mContext = context;
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        //Loader<String> movieSearchLoader = loaderManager.getLoader(mLoaderId);
        //if (movieSearchLoader == null) {
        loaderManager.initLoader(mLoaderId, null, callback);
        // } else {
        ////   loaderManager.restartLoader(mLoaderId, null, callback);
        // }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Timber.i("Inside oncreateloader WordPracticeFragment");

        //Setup the uri that will get the data I need from my ContentProvider
        Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(mContext, loaderUri, null, null, null, null);

        //and get a CursorLoader from my ContentProvider
        return cursorLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //When the loader has loaded some data (either initially, or the
        //datasource has changed and a new cursor is being provided),
        //Then we'll swap out the curser in our recyclerview's adapter
        // and we'll create the adapter if necessary
        // if (mCursorAdapter == null) {
        //   mCursorAdapter = new PracticeWordsAdapter(mContext,data);
        mAdapter = new PracticeWordsAdapter(mContext, data);
        mWordPracticeRecyclerView.setAdapter(mAdapter);
        // }

        // mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //If the loader is reset, we need to clear out the
        //current cursor from the adapter.
        // mAdapter.swapCursor(null);
    }

}
