package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.WORD_DICTIONARY_URL;


public class DictionaryFragment extends Fragment {

    private static final String TAG = DictionaryFragment.class.getSimpleName();
    Context mContext;
    View rootView;
    @BindView(R.id.dictionary_search_word_edit_text)
    EditText dictionarySearchWord;
    @BindView(R.id.dictionary_word_meaning_text)
    TextView dictionarySearchMeaning;
    private String wordForSearch;
    private String meaning;

    public DictionaryFragment() {

    }

    @OnClick(R.id.search_button)
    public void searchForMeaning() {
        wordForSearch = String.valueOf(dictionarySearchWord.getText());
        Timber.i("Before Calling CallbackTask");
        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<Example> call = api.getMyJSON(wordForSearch);

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    Example example = response.body();
                    List<String> definitions = example.getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getSenses().get(0).getDefinitions();
                    for (String definition : definitions) {
                        meaning = definition;
                        Timber.i("Inside onResponse successful " + meaning);

                    }
                    meaning = meaning.substring(0, 1).toUpperCase() + meaning.substring(1);
                    dictionarySearchMeaning.setText(meaning);
                } else {
                    dictionarySearchMeaning.setText(R.string.error_dictionary);
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                dictionarySearchMeaning.setText(R.string.error_dictionary);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dictionary, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    private String dictionaryEntries() {
        final String language = "en";
        final String word_id = wordForSearch.toLowerCase(); //word id is case sensitive and lowercase is required
        return WORD_DICTIONARY_URL + "entries/" + language + "/" + word_id;
    }

}
