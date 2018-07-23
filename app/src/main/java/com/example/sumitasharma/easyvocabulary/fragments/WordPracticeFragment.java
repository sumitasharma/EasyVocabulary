package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.adapter.PracticeWordsAdapter;
import com.example.sumitasharma.easyvocabulary.data.WordContract;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.STATE_WORD_PRACTICE;

public class WordPracticeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final static int LOADER_ID = 101;
    private final LoaderManager.LoaderCallbacks<Cursor> callback = WordPracticeFragment.this;
    TextView mWordPractice;
    View mRootView;
    private RecyclerView mWordPracticeRecyclerView;
    private Context mContext = getContext();
    private PracticeWordsAdapter mAdapter = null;
    private PassTheState mPassTheSate;

    public WordPracticeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.i("Inside WordPracticeFragment onCreateView");
        mRootView = inflater.inflate(R.layout.fragment_word_practice, container, false);
        mWordPracticeRecyclerView = mRootView.findViewById(R.id.recycler_view_practice_words);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mWordPracticeRecyclerView.setLayoutManager(mLinearLayoutManager);
        if (savedInstanceState != null) {
            Timber.i("onCreate Fragment called");
            mWordPracticeRecyclerView.setAdapter(mAdapter);
        } else
            initializeLoader(LOADER_ID, getContext());
        return mRootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("Inside WordPracticeFragment onCreate");
        setRetainInstance(true);
    }

    private void initializeLoader(int loaderId, Context context) {
        Timber.i("Inside initializeLoader");
        int mLoaderId = loaderId;
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
        //String frequencyOfWordsForPractice;
        String levelOfWordsForPractice;
        Timber.i("Inside oncreateloader WordPracticeFragment");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        numberOfWordsForPractice = Integer.parseInt(sharedPreferences.getString(getResources().getString(R.string.number_of_words_key), "4"));
        //frequencyOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.frequency_of_words_key), "Daily"));
        levelOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.level_of_words_for_practice_key), "Easy"));
        //Setup the uri that will get the data I need from my ContentProvider
        Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        String[] level = {levelOfWordsForPractice, String.valueOf(numberOfWordsForPractice)};

        String where = WordContract.WordsEntry.COLUMN_WORD_LEVEL + "=? and " + WordContract.WordsEntry.COLUMN_WORD_PRACTICED + "!= 1";
        String sortOrder = "RANDOM() LIMIT ?";
        //and get a CursorLoader from my ContentProvider
        return new CursorLoader(mContext, loaderUri, null, where, level, sortOrder);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToNext()) {
            Snackbar snackbar = Snackbar.make(mRootView.findViewById(R.id.practice_word_fragment_coordinator_layout), R.string.no_more_data,
                    Snackbar.LENGTH_INDEFINITE).setAction("Refresh", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
                    ContentValues values = new ContentValues();
                    String where = "wordLevel = ?";
                    String[] level = {(PreferenceManager.getDefaultSharedPreferences(mContext).getString(getResources().getString(R.string.level_of_words_for_practice_key), "Easy"))};
                    values.put(WordContract.WordsEntry.COLUMN_WORD_PRACTICED, false);
                    mContext.getContentResolver().update(loaderUri, values, where, level);
                }
            });
            snackbar.show();
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.BLUE);
            Timber.i("no more data");
        }
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
        outState.putString(STATE_WORD_PRACTICE, getResources().getString(R.string.word_practice_state));
        mPassTheSate.passTheSavedState(getResources().getString(R.string.word_practice_state));
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
