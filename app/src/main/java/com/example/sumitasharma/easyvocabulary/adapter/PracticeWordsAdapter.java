package com.example.sumitasharma.easyvocabulary.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.data.WordContract;

import timber.log.Timber;

public class PracticeWordsAdapter extends RecyclerView.Adapter<PracticeWordsAdapter.RecyclerViewHolderWords> {

    private final static String TAG = PracticeWordsAdapter.class.getSimpleName();
    private final Context mContext;
    private final Cursor mWords;

    public PracticeWordsAdapter(Context context, Cursor words) {
        //Timber.i( "Inside RecipeStepsAdapter Constructor");
        Timber.i("Inside PracticeWordsAdapter Constructor");
        this.mContext = context;
        this.mWords = words;
    }

    @Override
    public RecyclerViewHolderWords onCreateViewHolder(ViewGroup parent, int viewType) {
        Timber.i("Inside oncreateviewholder practicewordadapter");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_practice_view_holder, parent, false);
        return new RecyclerViewHolderWords(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderWords holder, int position) {
        holder.mWordPractice.setText(mWords.getString(mWords.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
        holder.mWordMeaning.setText(mWords.getString(mWords.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING)));

    }

    @Override
    public int getItemCount() {
        //Timber.i( "getItemCount Called. Size is:" + mStep.size());
        Timber.i("getItemCount Called. Size is:" + mWords.getCount());
        return mWords.getCount();
    }


    class RecyclerViewHolderWords extends RecyclerView.ViewHolder {
        private final TextView mWordPractice;
        private final TextView mWordMeaning;


        RecyclerViewHolderWords(View itemView) {
            super(itemView);
            mWordPractice = itemView.findViewById(R.id.word_practice);
            mWordMeaning = itemView.findViewById(R.id.word_practice_meaning);
        }

    }
}

