package com.sumitasharma.app.easyvocabulary.wordui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.sumitasharma.app.easyvocabulary.R;
import com.sumitasharma.app.easyvocabulary.fragments.WordPracticeFragment;

import static com.sumitasharma.app.easyvocabulary.util.WordUtil.isOnline;

public class PracticeWordsActivity extends AppCompatActivity implements WordPracticeFragment.PassTheState {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_words);
        if (!isOnline(this)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.practice_word_coordinator_layout), R.string.internet_connectivity,
                    Snackbar.LENGTH_LONG);
            snackbar.show();
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.BLUE);
            return;
        }
        if (savedInstanceState == null) {
            WordPracticeFragment wordPracticeFragment = new WordPracticeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.activity_from_right, R.anim.activity_exit_left);
            transaction.replace(R.id.practice_word_frame_layout, wordPracticeFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        // Log.i(TAG, "Inside onCreateOptionsMenu");
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
    public void passTheSavedState(String state) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PracticeWordsActivity.this, MainActivity.class));
    }
}
