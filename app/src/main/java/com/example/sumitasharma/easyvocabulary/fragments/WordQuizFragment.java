package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.loaders.WordQuizLoader;
import com.example.sumitasharma.easyvocabulary.wordui.WordQuizPracticeActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD_ID;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_WORD_MEANING;


public class WordQuizFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = WordQuizFragment.class.getSimpleName();
    private final static int LOADER_ID = 102;
    HashMap<String, String> mWordAndMeaning = null;
    View rootView;
    @BindView(R.id.radio)
    RadioGroup radioButtonGroup;
    @BindView(R.id.word_quiz_meaning)
    TextView quizWordMeaning;
    private Context mContext = getContext();
    private int mLoaderId;
    private Cursor mCursor;
    private long mWordId;
    private String mWord;
    private String mWordMeaning;
    private View mRootView;


    public WordQuizFragment() {

    }

    public static WordQuizFragment newInstance(String wordMeaning, String word, long word_id) {
        Timber.i("Inside WordQuizFragment newInstance");
        Bundle arguments = new Bundle();
        arguments.putLong(QUIZ_WORD_ID, word_id);
        arguments.putString(QUIZ_WORD, word);
        arguments.putString(QUIZ_WORD_MEANING, wordMeaning);
        WordQuizFragment fragment = new WordQuizFragment();
        fragment.setArguments(arguments);
        return fragment;
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

        setHasOptionsMenu(true);
    }

    public WordQuizPracticeActivity getActivityCast() {
        return (WordQuizPracticeActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // In support library r8, calling initLoader for a fragment in a FragmentPagerAdapter in
        // the fragment's onCreate may cause the same LoaderManager to be dealt to multiple
        // fragments because their mIndex is -1 (haven't been added to the activity yet). Thus,
        // we do this in onActivityCreated.
        //getLoaderManager().initLoader(LOADER_ID, null,this);
        initializeLoader(LOADER_ID, mContext);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_word_quiz, container, false);
        ButterKnife.bind(this, mRootView);
        Timber.i("Inside onCreateView WordQuizFragment");
//        //mPhotoContainerView = mRootView.findViewById(R.id.photo_container);
//
//        mRootView.findViewById(R.id.share_fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
//                        .setType("text/plain")
//                        .setText("Some sample text")
//                        .getIntent(), getString(R.string.action_share)));
//            }
//        });
//
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
    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        //Check for the selected radioButton

//        int radioButtonId = radioButtonGroup.getCheckedRadioButtonId();
//        View radioButton = radioButtonGroup.findViewById(radioButtonId);
//        int index = radioButtonGroup.indexOfChild(radioButton);
//        RadioButton radio = (RadioButton) radioButtonGroup.getChildAt(index);
//        String selectedWord = radio.getText().toString();

        quizWordMeaning.setText(mWordMeaning);
        Timber.i("quizwordmeaning" + mWordMeaning);
        // mWordAndMeaning= new HashMap<>(mCursor.getString(WordQuizLoader.Query.COLUMN_WORD), mCursor.getString(WordQuizLoader.Query.COLUMN_MEANING));

//        TextView titleView = (TextView) mRootView.findViewById(R.id.article_title);
//        TextView bylineView = (TextView) mRootView.findViewById(R.id.article_byline);
//        //bylineView.setMovementMethod(new LinkMovementMethod());
//        TextView bodyView = (TextView) mRootView.findViewById(R.id.article_body);
//        collapsingToolbarLayout = (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapse_toolbar);
//
//
//        bodyView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Rosario-Regular.ttf"));
//
//        if (mCursor != null) {
////            mRootView.setAlpha(0);
////            mRootView.setVisibility(View.VISIBLE);
////            mRootView.animate().alpha(1);
//            String article_title = mCursor.getString(ArticleLoader.Query.TITLE);
//            titleView.setText(article_title);
//
//
//            collapsingToolbarLayout.setTitle(article_title);
//            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
//            Date publishedDate = parsePublishedDate();
//            if (!publishedDate.before(START_OF_EPOCH.getTime())) {
//                bylineView.setText(Html.fromHtml(
//                        DateUtils.getRelativeTimeSpanString(
//                                publishedDate.getTime(),
//                                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
//                                DateUtils.FORMAT_ABBREV_ALL).toString()
//                                + " by <font color='#ffffff'>"
//                                + mCursor.getString(ArticleLoader.Query.AUTHOR)
//                                + "</font>"));
//
//            } else {
//                // If date is before 1902, just show the string
//                bylineView.setText(Html.fromHtml(
//                        outputFormat.format(publishedDate) + " by <font color='#ffffff'>"
//                                + mCursor.getString(ArticleLoader.Query.AUTHOR)
//                                + "</font>"));
//
//            }
//            String bodyText = mCursor.getString(ArticleLoader.Query.BODY);
//            bodyView.setText(Html.fromHtml(bodyText.replaceAll("(\r\n|\n)", "<br />")));
//
//            String photoUrl = mCursor.getString(ArticleLoader.Query.PHOTO_URL);
//            Glide.with(getActivity().getApplicationContext())
//                    .load(photoUrl)
//                    .override(600, 200) // resizes the image to these dimensions (in pixel). does not respect aspect ratio
//                    .centerCrop()
//                    .into(mPhotoView);
//            Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar_id);
//
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // back button pressed
//                    NavUtils.navigateUpFromSameTask(getActivity());
//                }
//            });
//        }
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return WordQuizLoader.newInstanceForWordId(getActivity(), mWordId);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (!isAdded()) {
            if (data != null) {
                data.close();
            }
            return;
        }

        mCursor = data;
        if (mCursor != null && !mCursor.moveToFirst()) {
            Log.e(TAG, "Error reading item detail cursor");
            mCursor.close();
            mCursor = null;
        }

        bindViews();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mCursor = null;
        bindViews();
    }


}