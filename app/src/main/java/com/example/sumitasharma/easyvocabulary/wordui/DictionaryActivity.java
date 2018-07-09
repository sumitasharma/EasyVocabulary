package com.example.sumitasharma.easyvocabulary.wordui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.DictionaryFragment;

import butterknife.BindView;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.STATE_WORD_DICTIONARY;

public class DictionaryActivity extends AppCompatActivity implements DictionaryFragment.PassTheStateDictionary {
    @BindView(R.id.dictionary_search_word_edit_text)
    EditText dictionarySearchWord;
    @BindView(R.id.dictionary_word_meaning_text)
    TextView dictionarySearchMeaning;
    String word;
    String meaning;
    Fragment mDictionaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("Inside onCreate DictionaryActivity");
        setContentView(R.layout.activity_dictionary);

        if (savedInstanceState == null) {
            DictionaryFragment dictionaryFragment = new DictionaryFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.activity_from_right, R.anim.activity_exit_left);
            transaction.replace(R.id.dictionary_word_frame_layout, dictionaryFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.goto_main:

                Intent intentMain = new Intent(this, MainActivity.class);
                startActivity(intentMain);
                break;
            case R.id.goto_practice:

                Intent intentPractice = new Intent(this, PracticeWordsActivity.class);
                startActivity(intentPractice);
                break;
            case R.id.goto_progress:

                Intent intentProgress = new Intent(this, ProgressActivity.class);
                startActivity(intentProgress);
                break;
            case R.id.goto_dictionary:

                Intent intentDictionary = new Intent(this, DictionaryActivity.class);
                startActivity(intentDictionary);
                break;
            case R.id.goto_quiz:

                Intent intentQuiz = new Intent(this, WordQuizPracticeActivity.class);
                startActivity(intentQuiz);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_WORD_DICTIONARY, "state_dictionary");
    }

    @Override
    public void passTheSavedStateDictionary(String stateDictionary, String word, String meaning) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DictionaryActivity.this, MainActivity.class));
    }

}
