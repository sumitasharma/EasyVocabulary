package com.example.sumitasharma.easyvocabulary.adapter;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
    private final Cursor mWordsCursor;

    public PracticeWordsAdapter(Context context, Cursor words) {
        //Timber.i( "Inside RecipeStepsAdapter Constructor");
        Timber.i("Inside PracticeWordsAdapter Constructor");
        this.mContext = context;
        this.mWordsCursor = words;
    }

    @Override
    public RecyclerViewHolderWords onCreateViewHolder(ViewGroup parent, int viewType) {
        Timber.i("Inside oncreateviewholder practicewordadapter");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_practice_view_holder, parent, false);
        return new RecyclerViewHolderWords(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderWords holder, int position) {
        String wordPractice;
        String wordMeaning;
        if (mWordsCursor.moveToPosition(position) && mWordsCursor.getCount() >= 1) {
            wordPractice = mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD));
            wordMeaning = mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING));
            wordPractice = wordPractice.substring(0, 1).toUpperCase() + wordPractice.substring(1).toLowerCase();
            wordMeaning = wordMeaning.substring(0, 1).toUpperCase() + wordMeaning.substring(1).toLowerCase();
            holder.mWordPractice.setText(wordPractice);
            holder.mWordMeaning.setText(wordMeaning);
        }
        // Update the rows seen by user as practiced.
        Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        ContentValues values = new ContentValues();


        Timber.i("Updating Practiced Word" + mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
        values.put(WordContract.WordsEntry.COLUMN_WORD_PRACTICED, true);
        String[] selectArgs = {mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD))};
        mContext.getContentResolver().update(loaderUri, values, WordContract.WordsEntry.COLUMN_WORD + "= ?", selectArgs);


    }

    @Override
    public int getItemCount() {
        //Timber.i( "getItemCount Called. Size is:" + mStep.size());
        Timber.i("getItemCount Called. Size is:" + mWordsCursor.getCount());
        return mWordsCursor.getCount();
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

