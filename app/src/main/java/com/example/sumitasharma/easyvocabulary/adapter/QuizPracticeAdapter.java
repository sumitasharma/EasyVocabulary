package com.example.sumitasharma.easyvocabulary.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.util.Words;

import butterknife.BindView;
import timber.log.Timber;

public class QuizPracticeAdapter extends RecyclerView.Adapter<QuizPracticeAdapter.RecyclerViewHolderQuizPractice> {
    private final Context mContext;
    private Words[] mWords = null;
    private WordMeaningClickListener mClickPositionListener = null;
    private String[] mWordsOptions;


    public QuizPracticeAdapter(Context context, Words[] words, WordMeaningClickListener meaningClickListener, String[] wordsOptions) {
        this.mContext = context;
        this.mWords = words;
        this.mClickPositionListener = meaningClickListener;
        this.mWordsOptions = wordsOptions;
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
//        holder.wordQuizMeaning.setText((CharSequence) mWords[position]);
//        int max = 1;
//        Random random = new Random();
//        int wordIndex;
//        for(int  i = 0 ; i < mWords.length; i++){
//            wordIndex = random.nextInt(max);
//            holder.radioButtonGroup.getChildAt(wordIndex).setTe = mWordsOptions[wordIndex];
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
        //@BindView(R.id.radio)
        private final RadioGroup mRadioButtonGroup;
        //@BindView(R.id.word_quiz_meaning)
        private final TextView mWordQuizMeaning;

        public RecyclerViewHolderQuizPractice(View itemView) {
            super(itemView);
            mRadioButtonGroup = itemView.findViewById(R.id.radio);
            mWordQuizMeaning = itemView.findViewById(R.id.word_quiz_meaning);
            Timber.i("Insider RecyclerViewHolderQuizPractice");


        }
    }


}

