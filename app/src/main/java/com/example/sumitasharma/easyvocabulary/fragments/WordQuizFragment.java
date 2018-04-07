package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.example.sumitasharma.easyvocabulary.loaders.WordQuizLoader;
import com.example.sumitasharma.easyvocabulary.wordui.WordQuizPracticeActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.LAST_VIEW_PAGER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD_ID;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD_MEANING;


public class WordQuizFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = WordQuizFragment.class.getSimpleName();
    private final static int LOADER_ID = 102;

    @BindView(R.id.radio)
    RadioGroup radioButtonGroup;
    @BindView(R.id.radio_quiz_word1)
    RadioButton radioButton1;
    @BindView(R.id.radio_quiz_word2)
    RadioButton radioButton2;
    @BindView(R.id.radio_quiz_word3)
    RadioButton radioButton3;
    @BindView(R.id.radio_quiz_word4)
    RadioButton radioButton4;
    @BindView(R.id.word_quiz_meaning)
    TextView quizWordMeaning;
    @BindView(R.id.submit_button)
    Button submitButton;
    int mLoaderId;
    List<Integer> mIndex = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
    PassUserChoice mPassUserChoice;
    SubmitAnswers mSubmitAnswers;
    boolean mLastViewPager = false;
    private Context mContext = getContext();
    private Cursor mCursor;
    private long mWordId;
    private String mWord;
    private String mWordMeaning;
    private View mRootView;
    public WordQuizFragment() {

    }

    public static WordQuizFragment newInstance(String wordMeaning, String word, long word_id, boolean lastViewPager) {
        Timber.i("Inside WordQuizFragment newInstance");
        Bundle arguments = new Bundle();
        arguments.putLong(QUIZ_WORD_ID, word_id);
        arguments.putString(QUIZ_WORD, word);
        arguments.putString(QUIZ_WORD_MEANING, wordMeaning);
        arguments.putBoolean(LAST_VIEW_PAGER, lastViewPager);
        WordQuizFragment fragment = new WordQuizFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @OnClick(R.id.submit_button)
    public void submitAnswer() {
        mSubmitAnswers.submitAnswer(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(QUIZ_WORD_ID)) {
            mWordId = getArguments().getLong(QUIZ_WORD_ID);
        }
        if (getArguments().containsKey(QUIZ_WORD)) {
            mWord = getArguments().getString(QUIZ_WORD);
        }
        if (getArguments().containsKey(QUIZ_WORD_MEANING)) {
            mWordMeaning = getArguments().getString(QUIZ_WORD_MEANING);
        }
        if (getArguments().containsKey(LAST_VIEW_PAGER)) {

            mLastViewPager = getArguments().getBoolean(LAST_VIEW_PAGER);
            Timber.i("last page ? " + mLastViewPager);
        }

        setHasOptionsMenu(true);
    }

    public WordQuizPracticeActivity getActivityCast() {
        return (WordQuizPracticeActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeLoader(LOADER_ID, mContext);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_word_quiz, container, false);
        ButterKnife.bind(this, mRootView);
        Timber.i("Inside onCreateView WordQuizFragment");
        if (mLastViewPager) {
            submitButton.setVisibility(View.VISIBLE);
        } else {
            submitButton.setVisibility(View.INVISIBLE);
        }
        radioButtonGroup.clearCheck();
        bindViews();
        return mRootView;
    }

    private void initializeLoader(int loaderId, Context context) {
        Timber.i("Inside initializeLoader");
        this.mLoaderId = loaderId;
        this.mContext = context;
        LoaderManager loaderManager = getActivityCast().getSupportLoaderManager();
        Loader<Object> wordPracticeLoader = loaderManager.getLoader(mLoaderId);
        if (wordPracticeLoader == null) {
            getLoaderManager().initLoader(mLoaderId, null, this);
        } else {
            getLoaderManager().restartLoader(mLoaderId, null, this);
        }
        selectRadioButton();
    }


    public void selectRadioButton() {

        int radioButtonId = radioButtonGroup.getCheckedRadioButtonId();

        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioButtonGroup.findViewById(checkedId);
                int index = radioButtonGroup.indexOfChild(radioButton);
                RadioButton radio = (RadioButton) radioButtonGroup.getChildAt(index);
                String selectedWord = radio.getText().toString();
                if (selectedWord == mWord) {
                    Timber.i("User chose the correct word " + selectedWord + mWord);
                    mPassUserChoice.callback(mWordId, true);
                } else {
                    Timber.i("User didn't choose the correct word " + selectedWord + mWord);
                    mPassUserChoice.callback(mWordId, false);
                }
            }

        });
    }


    private void bindViews() {
        if (mRootView == null) {
            return;
        }



        quizWordMeaning.setText(mWordMeaning);
        Timber.i("quizwordmeaning" + mWordMeaning);

    }


    private void bindViewsRadioGroup() {
        if (mRootView == null) {
            return;
        }

        Collections.shuffle(mIndex);
        if (mCursor != null) {
            mCursor.moveToFirst();
            for (int i = 0; i < mIndex.size() - 1; i++) {
                RadioButton radio = (RadioButton) radioButtonGroup.getChildAt(mIndex.get(i));
                String word = mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD));
                radio.setText(word);
                mCursor.moveToNext();
            }
            RadioButton radio = (RadioButton) radioButtonGroup.getChildAt(mIndex.get(mIndex.size() - 1));
            radio.setText(mWord);
        } else
            Timber.i("There is no data in mCursor");
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return WordQuizLoader.newInstanceForWordId(getActivity(), mWordId);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {

        mCursor = data;
        bindViewsRadioGroup();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mCursor = null;
        bindViewsRadioGroup();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPassUserChoice = (PassUserChoice) context;
        mSubmitAnswers = (SubmitAnswers) context;
    }

    public interface PassUserChoice {
        void callback(long wordId, Boolean answer);
    }

    public interface SubmitAnswers {
        void submitAnswer(boolean submitAnswer);
    }

}
