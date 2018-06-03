package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.easyvocabulary.BuildConfig;
import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.util.AdsDisplay;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.PROGRESS_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.WORD_MEANING_CARD_VIEW_IDENTIFIER;

public class WordMainFragment extends Fragment {
    private static final String TAG = WordMainFragment.class.getSimpleName();
    @BindView(R.id.adView)
    AdView adView;
    private PassCardViewInformation mPassCardViewInformation;

    public WordMainFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @OnClick(R.id.word_meaning_card_view)
    public void cardClickPractice() {
        mPassCardViewInformation.cardViewInformation(WORD_MEANING_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.word_main_fragment_text_view_practice)
    public void cardClickPracticeText() {
        mPassCardViewInformation.cardViewInformation(WORD_MEANING_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.quiz_card_view)
    public void cardClickQuiz() {
        mPassCardViewInformation.cardViewInformation(QUIZ_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.word_main_fragment_text_view_quiz)
    public void cardClickQuizText() {
        mPassCardViewInformation.cardViewInformation(QUIZ_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.progress_card_view)
    public void cardClickProgress() {
        mPassCardViewInformation.cardViewInformation(PROGRESS_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.word_main_fragment_choice_text_view_task_progress)
    public void cardClickProgressText() {
        mPassCardViewInformation.cardViewInformation(PROGRESS_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.dictionary_card_view)
    public void cardClickDictionary() {
        mPassCardViewInformation.cardViewInformation(DICTIONARY_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.word_main_fragment_choice_text_view_dictionary)
    public void cardClickDictionaryText() {
        mPassCardViewInformation.cardViewInformation(DICTIONARY_CARD_VIEW_IDENTIFIER);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_word_main, container, false);
        ButterKnife.bind(this, mRootView);
        if (!BuildConfig.PAID_VERSION) {// this is the flag configured in build.gradle
            adView.setVisibility(View.VISIBLE);
            AdsDisplay.displayAds(mRootView, getContext());
        } else {
            adView.setVisibility(View.INVISIBLE);
        }
        return mRootView;
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
