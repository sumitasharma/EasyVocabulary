package com.sumitasharma.app.easyvocabulary.adapter;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sumitasharma.app.easyvocabulary.R;
import com.sumitasharma.app.easyvocabulary.data.WordContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class PracticeWordsAdapter extends RecyclerView.Adapter<PracticeWordsAdapter.RecyclerViewHolderWords> {

    private final static String TAG = PracticeWordsAdapter.class.getSimpleName();
    private final Context mContext;
    private Cursor mWordsCursor;

    public PracticeWordsAdapter(Context context, Cursor words) {
        Timber.i("Inside PracticeWordsAdapter Constructor");
        this.mContext = context;
        this.mWordsCursor = words;
    }

    @Override
    public RecyclerViewHolderWords onCreateViewHolder(ViewGroup parent, int viewType) {
        Timber.i("Inside onCreateViewHolder PracticeWordsAdapter");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_practice_view_holder, parent, false);
        return new RecyclerViewHolderWords(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderWords holder, int position) {
        String wordPractice;
        String wordMeaning;
        String wordType;
        String wordExample;
        if (mWordsCursor.moveToPosition(position) && mWordsCursor.getCount() >= 1) {
            wordPractice = mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD));
            wordMeaning = mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING));
            wordType = mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_TYPE));
            wordExample = mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_EXAMPLE));
            wordPractice = wordPractice.substring(0, 1).toUpperCase() + wordPractice.substring(1).toLowerCase();
            wordMeaning = wordMeaning.substring(0, 1).toUpperCase() + wordMeaning.substring(1).toLowerCase();
            wordMeaning = wordMeaning.replaceAll("\\<.*?\\>", "");
            holder.mWordPractice.setText(wordPractice);
            holder.mWordPractice.setPaintFlags(holder.mWordPractice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.mWordMeaning.setText(wordMeaning);
            if (wordType != null) {
                wordType = wordType.substring(0, 1).toUpperCase() + wordType.substring(1).toLowerCase();
                holder.mWordType.setText("(" + wordType + ")");

            }
            if (wordExample != null) {
                holder.mWordTextExample.setVisibility(View.VISIBLE);
                wordExample = wordExample.replaceAll("\\<.*?\\>", "");
                // wordExample = android.text.Html.fromHtml(wordExample, FROM_HTML_MODE_LEGACY).toString();
                wordExample = wordExample.substring(0, 1).toUpperCase() + wordExample.substring(1).toLowerCase();
                holder.mWordExample.setText(wordExample);
            }
        }
        // Update the rows seen by user as practiced.
        Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        ContentValues values = new ContentValues();
        Timber.i("Updating Practiced Word" + mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
        values.put(WordContract.WordsEntry.COLUMN_WORD_PRACTICED, true);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        values.put(WordContract.WordsEntry.COLUMN_LAST_UPDATED, date);
        String[] selectArgs = {mWordsCursor.getString(mWordsCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD))};
        mContext.getContentResolver().update(loaderUri, values, WordContract.WordsEntry.COLUMN_WORD + "= ?", selectArgs);


    }

    @Override
    public int getItemCount() {
        Timber.i("getItemCount Called. Size is:" + mWordsCursor.getCount());
        return mWordsCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        mWordsCursor = cursor;
        notifyDataSetChanged();
    }

    class RecyclerViewHolderWords extends RecyclerView.ViewHolder {
        private final TextView mWordPractice;
        private final TextView mWordMeaning;
        private final TextView mWordType;
        private final TextView mWordExample;
        private final TextView mWordTextExample;


        RecyclerViewHolderWords(View itemView) {
            super(itemView);
            mWordPractice = itemView.findViewById(R.id.word_practice);
            mWordMeaning = itemView.findViewById(R.id.word_practice_meaning);
            mWordType = itemView.findViewById(R.id.word_type);
            mWordExample = itemView.findViewById(R.id.word_practice_example);
            mWordTextExample = itemView.findViewById(R.id.word_text_example);
        }

    }

}

