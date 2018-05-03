package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.USER_TOTAL;


public class WordQuizSummaryFragment extends Fragment {
    @BindView(R.id.user_quiz_summary_text_view)
    TextView mUserTotalSummary;
    private View rootView;
    private String mTotal;
    private CorrectAnswers mCorrectAnswers;

    @OnClick(R.id.correct_answers)
    public void correctAnswers() {
        mCorrectAnswers.callCorrectAnswerActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_word_quiz_summary, container, false);
        Timber.i("Inside WordQuizSummaryFragment");
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            Timber.i("mTotal" + mTotal);
            mTotal = getArguments().getString(USER_TOTAL);
        }
        Timber.i("Total " + mTotal);
        mUserTotalSummary.setText(mTotal);
        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCorrectAnswers = (CorrectAnswers) context;
    }

    public interface CorrectAnswers {
        void callCorrectAnswerActivity();

        ;
    }
}
