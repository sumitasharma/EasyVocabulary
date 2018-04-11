package com.example.sumitasharma.easyvocabulary.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.data.WordContract;

import java.util.Random;

import timber.log.Timber;

public class QuizPracticeAdapter extends RecyclerView.Adapter<QuizPracticeAdapter.RecyclerViewHolderQuizPractice> {
    private final Context mContext;
    String meaning = null;
    private Cursor mCursor;


    public QuizPracticeAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public QuizPracticeAdapter.RecyclerViewHolderQuizPractice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_quiz_view_holder, parent, false);
        Timber.i("Inside adapter for quiz");
        return new RecyclerViewHolderQuizPractice(view);
    }

    @Override
    public void onBindViewHolder(QuizPracticeAdapter.RecyclerViewHolderQuizPractice holder, int position) {
        Timber.i("Inside onBindViewHolder ");
        // Setting the word meanings in a TextView
        meaning = mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING));
        meaning = meaning.substring(0, 1).toUpperCase() + meaning.substring(1).toLowerCase();
        Timber.i("meaning :" + meaning);
        holder.mWordQuizMeaning.setText(meaning);

        //        // Getting the words and meaning in a string array
        String[] mWordsOptions = null;
        mCursor.moveToFirst();
        int stringIndex = 0;
        String word;
        if (mCursor != null) {

            while (mCursor.moveToNext()) {
                word = mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD));
                word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                Timber.i("words :" + word);
                mWordsOptions[stringIndex] = word;
                stringIndex++;
            }
        }
        int max = 1;
        Random random = new Random();
        int wordIndex;
        for (int i = 0; i < mCursor.getCount(); i++) {
            wordIndex = random.nextInt(max);
            RadioButton radioButton = (RadioButton) holder.mRadioButtonGroup.getChildAt(wordIndex);
            radioButton.setText(mWordsOptions[wordIndex]);
            // holder.((RadioButton) mRadioButtonGroup.getChildAt(wordIndex)).setText(0) = mWordsOptions[wordIndex];
        }

        //int i = 0;
        //    Timber.i("Inside onLoadFinished WordQuizFragment");
        // Getting the data in Words class Array
//        while (mCursor.moveToNext()) {
//
//            words[i].setWord(mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
//            words[i].setWord(mCursor.getString(mCursor.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_MEANING)));
//            i++;
//        }


    }

    @Override
    public int getItemCount() {
        //   return mWords.length;
        return 0;
    }

    /**
     * Interface to handle clicks on viewHolder
     */
    public interface WordMeaningClickListener {
        void onClickWord(int wordPosition);
    }

    class RecyclerViewHolderQuizPractice extends RecyclerView.ViewHolder {
        // @BindView(R.id.radio)
        private final RadioGroup mRadioButtonGroup;
        // @BindView(R.id.word_quiz_meaning)
        private final TextView mWordQuizMeaning;

        public RecyclerViewHolderQuizPractice(View itemView) {
            super(itemView);
            mRadioButtonGroup = itemView.findViewById(R.id.radio);
            mWordQuizMeaning = itemView.findViewById(R.id.word_quiz_meaning);
            Timber.i("Insider RecyclerViewHolderQuizPractice");


        }
    }


}

