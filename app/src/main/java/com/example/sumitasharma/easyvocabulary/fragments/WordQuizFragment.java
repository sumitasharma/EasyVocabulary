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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.adapter.QuizPracticeAdapter;
import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.util.Words;

import butterknife.BindView;
import timber.log.Timber;


public class WordQuizFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, QuizPracticeAdapter.WordMeaningClickListener {
    private static final String TAG = WordQuizFragment.class.getSimpleName();
    private final static int LOADER_ID = 102;
    private final LoaderManager.LoaderCallbacks<Cursor> callback = WordQuizFragment.this;
    View rootView;
    @BindView(R.id.radio)
    RadioGroup radioButtonGroup;
    @BindView(R.id.recycler_view_word_quiz)
    RecyclerView mWordsRecyclerView;
    private Context mContext = getContext();
    private int mLoaderId;
    private Words[] words = null;

    public WordQuizFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_word_quiz, container, false);
        mWordsRecyclerView = rootView.findViewById(R.id.recycler_view_word_quiz);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mWordsRecyclerView.setLayoutManager(mLinearLayoutManager);
        initializeLoader(LOADER_ID, getContext());

        return rootView;
    }

    private void initializeLoader(int loaderId, Context context) {
        Timber.i("Inside initializeLoader");
        this.mLoaderId = loaderId;
        this.mContext = context;
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> wordPracticeLoader = loaderManager.getLoader(mLoaderId);
        if (wordPracticeLoader == null) {
            loaderManager.initLoader(mLoaderId, null, callback);
        } else {
            loaderManager.restartLoader(mLoaderId, null, callback);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Timber.i("Inside onCreateLoader WordQuizFragment");
        Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        String where = "wordPracticed = 'true'";
        String sortBy = "RANDOM() LIMIT 5";
        CursorLoader cursorLoader = new CursorLoader(mContext, loaderUri, null, where, null, sortBy);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int position = 0;
        Timber.i("Inside onLoadFinished WordQuizFragment");
        // Getting the data in Words class Array
        while (data.moveToNext()) {

            words[position].setWord(data.getString(data.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
            words[position].setWord(data.getString(data.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING)));
            position++;
        }
        // Getting the words and meaning in a string array
        String[] mWordsOptions = null;
        data.moveToFirst();
        position = 0;
        while (data.moveToNext()) {
            mWordsOptions[position] = words[position].getWord();
            position++;
        }
        Timber.i("Before Adapter");
        QuizPracticeAdapter quizPracticeAdapter = new QuizPracticeAdapter(mContext, words, this, mWordsOptions);
        mWordsRecyclerView.setAdapter(quizPracticeAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClickWord(int wordPosition) {
        int radioButtonId = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonId);
        int index = radioButtonGroup.indexOfChild(radioButton);
        RadioButton radio = (RadioButton) radioButtonGroup.getChildAt(index);
        String selectedWord = radio.getText().toString();
    }
}
