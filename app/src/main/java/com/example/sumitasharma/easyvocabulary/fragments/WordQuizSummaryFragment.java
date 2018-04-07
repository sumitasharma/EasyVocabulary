package com.example.sumitasharma.easyvocabulary.fragments;

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

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.USER_TOTAL;


public class WordQuizSummaryFragment extends Fragment {
    View rootView;
    @BindView(R.id.user_quiz_summary_text_view)
    TextView mUserTotalSummary;
    String mTotal = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_word_quiz_summary, container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments().get(USER_TOTAL) != null) {
            mTotal = (String) getArguments().get(USER_TOTAL);
        }
        mUserTotalSummary.setText(mTotal);
        return rootView;

    }
}
