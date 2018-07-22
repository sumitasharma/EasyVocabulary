package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.ApiService;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.Example;
import com.example.sumitasharma.easyvocabulary.dictionaryutils.RetroClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_SEARCH_EXAMPLE;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_SEARCH_MEANING;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_SEARCH_TYPE;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_SEARCH_WORD;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_WORD;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.STATE_WORD_PRACTICE;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.isOnline;


public class DictionaryFragment extends Fragment {

    private static final String TAG = DictionaryFragment.class.getSimpleName();
    Context mContext;
    @BindView(R.id.dictionary_search_word_edit_text)
    EditText dictionarySearchWord;
    @BindView(R.id.dictionary_word_meaning_text)
    TextView dictionarySearchMeaning;
    @BindView(R.id.dictionary_card_view)
    CardView mCardView;
    @BindView(R.id.word_dictionary_type)
    TextView dictionaryType;
    @BindView(R.id.word_dictionary_example)
    TextView dictionaryExample;
    @BindView(R.id.word_dictionary_text_example)
    TextView dictionaryTextExample;
    @BindView(R.id.dictionary_text_definition)
    TextView dictionaryTextDefinition;
    @BindView(R.id.word_dictionary)
    TextView dictionaryWord;
    private PassTheStateDictionary mPassTheSateDictionary;
    private View mRootView;
    private String mWordForSearch;
    private String mMeaning;
    private String mType;
    private String mExample;
    private String mSearchWord;

    public DictionaryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @OnClick(R.id.search_button)
    public void searchForMeaning() {

        if (!isOnline(getContext())) {
            Snackbar snackbar = Snackbar.make(mRootView.findViewById(R.id.dictionary_coordinator_layout), R.string.internet_connectivity,
                    Snackbar.LENGTH_LONG);
            snackbar.show();
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.BLUE);
            return;
        }

        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert mgr != null;
        mgr.hideSoftInputFromWindow(dictionarySearchWord.getWindowToken(), 0);
        mWordForSearch = String.valueOf(dictionarySearchWord.getText());
        Timber.i("Before Calling CallbackTask");
        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        // Calling JSON

        Call<List<Example>> call = api.getMyJSON(mWordForSearch);


        // Enqueue Callback will be call when get response...

        call.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                if (response.isSuccessful()) {
                    Example example = null;
                    List<Example> exampleList = response.body();
                    if (exampleList.size() > 0) {
                        example = exampleList.get(0);
                        dictionaryWord.setPaintFlags(dictionaryWord.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    } else {
                        Snackbar snackbar = Snackbar.make(mRootView.findViewById(R.id.dictionary_coordinator_layout), R.string.meaning_not_available,
                                Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(Color.BLUE);
                        return;
                    }
                    String meaning = example.getDefinition();

                    String longestDefinition = meaning;
                    int longestDefinitionIndex = 0;
                    for (int i = 1; i < exampleList.size(); i++) {
                        example = exampleList.get(i);
                        if (example.getDefinition().length() >= longestDefinition.length()) {
                            longestDefinition = example.getDefinition();
                            longestDefinitionIndex = i;
                        }
                    }
                    meaning = exampleList.get(longestDefinitionIndex).getDefinition();

                    if (meaning.isEmpty()) {
                        Snackbar snackbar = Snackbar.make(mRootView.findViewById(R.id.dictionary_coordinator_layout), R.string.meaning_not_available,
                                Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(Color.BLUE);
                        return;
                    }
                    mMeaning = meaning;
                    mMeaning = mMeaning.substring(0, 1).toUpperCase() + mMeaning.substring(1);
                    mMeaning = mMeaning.replaceAll("\\<.*?\\>", "");
                    mType = example.getType();
                    mType = mType.substring(0, 1).toUpperCase() + mType.substring(1);
                    mSearchWord = mWordForSearch;
                    mSearchWord = mSearchWord.substring(0, 1).toUpperCase() + mSearchWord.substring(1);
                    mCardView.setVisibility(View.VISIBLE);
                    dictionaryWord.setText(mSearchWord);
                    dictionarySearchMeaning.setText(mMeaning);
                    if (!mType.isEmpty())
                        dictionaryType.setText("(" + mType + ")");
                    if (example.getExample() != null) {
                        mExample = example.getExample();
                        mExample = mExample.substring(0, 1).toUpperCase() + mExample.substring(1);
                        mExample = mExample.replaceAll("\\<.*?\\>", "");
                        dictionaryTextExample.setVisibility(View.VISIBLE);
                        dictionaryExample.setText(mExample);
                    }


                } else {
                    Snackbar snackbar = Snackbar.make(mRootView.findViewById(R.id.dictionary_coordinator_layout), R.string.meaning_not_available,
                            Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.BLUE);
                }

            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                dictionarySearchMeaning.setText(R.string.error_dictionary);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_dictionary, container, false);
        ButterKnife.bind(this, mRootView);
        if (savedInstanceState != null && savedInstanceState.getString(DICTIONARY_SEARCH_MEANING) != null) {
            Timber.i("Inside onCreateView DictionaryFragment - " + savedInstanceState.getString(DICTIONARY_SEARCH_MEANING) + savedInstanceState.getString(DICTIONARY_SEARCH_WORD));
            mCardView.setVisibility(View.VISIBLE);
            dictionaryWord.setText(savedInstanceState.getString(DICTIONARY_WORD));
            dictionaryWord.setPaintFlags(dictionaryWord.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            dictionarySearchMeaning.setText(savedInstanceState.getString(DICTIONARY_SEARCH_MEANING));
            dictionarySearchWord.setText(savedInstanceState.getString(DICTIONARY_SEARCH_WORD));
            dictionaryType.setText("(" + savedInstanceState.getString(DICTIONARY_SEARCH_TYPE) + ")");
            if (savedInstanceState.getString(DICTIONARY_SEARCH_EXAMPLE) != null) {
                dictionaryTextExample.setVisibility(View.VISIBLE);
                dictionaryExample.setText(savedInstanceState.getString(DICTIONARY_SEARCH_EXAMPLE));
            }

        } else {
            mCardView.setVisibility(View.INVISIBLE);
        }

        return mRootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.i("Inside onSaveInstanceState" + this.mWordForSearch + ":" + this.mMeaning);
        outState.putString(DICTIONARY_SEARCH_WORD, this.mWordForSearch);
        outState.putString(DICTIONARY_SEARCH_MEANING, this.mMeaning);
        outState.putString(DICTIONARY_SEARCH_TYPE, this.mType);
        outState.putString(DICTIONARY_SEARCH_EXAMPLE, this.mExample);
        outState.putString(DICTIONARY_WORD, this.mSearchWord);
        outState.putString(STATE_WORD_PRACTICE, "state_dictionary");
        mPassTheSateDictionary.passTheSavedStateDictionary("state_dictionary", mWordForSearch, mMeaning);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPassTheSateDictionary = (PassTheStateDictionary) context;
    }

    public interface PassTheStateDictionary {
        void passTheSavedStateDictionary(String stateDictionary, String word, String meaning);
    }

}
