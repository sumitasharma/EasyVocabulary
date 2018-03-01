package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.easyvocabulary.R;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.PROGRESS_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.WORD_MEANING_CARD_VIEW_IDENTIFIER;

public class WordMainFragment extends Fragment {
    private static final String TAG = WordMainFragment.class.getSimpleName();
    View rootView;
    CardView wordMeaningCardView;
    CardView quizCardView;
    CardView progressCardView;
    CardView dictionaryCardView;
    private PassCardViewInformation mPassCardViewInformation;

    public WordMainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_word_main, container, false);
        wordMeaningCardView = rootView.findViewById(R.id.word_meaning_card_view);
        wordMeaningCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPassCardViewInformation.cardViewInformation(WORD_MEANING_CARD_VIEW_IDENTIFIER);
            }
        });
        quizCardView = rootView.findViewById(R.id.quiz_card_view);
        quizCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPassCardViewInformation.cardViewInformation(QUIZ_CARD_VIEW_IDENTIFIER);
            }
        });
        progressCardView = rootView.findViewById(R.id.progress_card_view);
        progressCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPassCardViewInformation.cardViewInformation(PROGRESS_CARD_VIEW_IDENTIFIER);
            }
        });
        dictionaryCardView = rootView.findViewById(R.id.dictionary_card_view);
        dictionaryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPassCardViewInformation.cardViewInformation(DICTIONARY_CARD_VIEW_IDENTIFIER);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPassCardViewInformation = (PassCardViewInformation) context;
    }

    public interface PassCardViewInformation {
        void cardViewInformation(String cardViewNumber);
    }

}
