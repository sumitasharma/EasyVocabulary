package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.graphics.Color;
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

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_SEARCH_MEANING;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_SEARCH_WORD;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.STATE_WORD_PRACTICE;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.WORD_DICTIONARY_URL;
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
    private PassTheStateDictionary mPassTheSateDictionary;
    private View mRootView;
    private String mWordForSearch;
    private String mMeaning;

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
                    List<Example> exampleList = response.body();
                    Example example = exampleList.get(0);
                    String meaning = example.getDefinition();
                    if (meaning.isEmpty()) {
                        Snackbar snackbar = Snackbar.make(mRootView.findViewById(R.id.dictionary_coordinator_layout), R.string.meaning_not_available,
                                Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(Color.BLUE);
                        return;
                    }

                    mMeaning = mMeaning.substring(0, 1).toUpperCase() + mMeaning.substring(1);
                    mCardView.setVisibility(View.VISIBLE);
                    dictionarySearchMeaning.setText(mMeaning);
                } else {
                    mCardView.setVisibility(View.VISIBLE);
                    dictionarySearchMeaning.setText(R.string.error_dictionary);
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
        if (savedInstanceState != null) {
            Timber.i("Inside onCreateView DictionaryFragment - " + savedInstanceState.getString(DICTIONARY_SEARCH_MEANING) + savedInstanceState.getString(DICTIONARY_SEARCH_WORD));
            mCardView.setVisibility(View.VISIBLE);
            dictionarySearchMeaning.setText(savedInstanceState.getString(DICTIONARY_SEARCH_MEANING));
            dictionarySearchWord.setText(savedInstanceState.getString(DICTIONARY_SEARCH_WORD));

        }
        return mRootView;
    }


    private String dictionaryEntries() {
        final String language = "en";
        final String word_id = mWordForSearch.toLowerCase(); //word id is case sensitive and lowercase is required
        return WORD_DICTIONARY_URL + "entries/" + language + "/" + word_id;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.i("Inside onSaveInstanceState" + this.mWordForSearch + ":" + this.mMeaning);
        outState.putString(DICTIONARY_SEARCH_WORD, this.mWordForSearch);
        outState.putString(DICTIONARY_SEARCH_MEANING, this.mMeaning);
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
