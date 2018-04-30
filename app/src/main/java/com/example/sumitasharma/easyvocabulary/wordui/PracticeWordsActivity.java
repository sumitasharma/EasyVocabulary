package com.example.sumitasharma.easyvocabulary.wordui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.WordPracticeFragment;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.isOnline;

public class PracticeWordsActivity extends AppCompatActivity {

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
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.practice_word_frame_layout, wordPracticeFragment).commit();
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
        //    Log.i(TAG, "Inside onOptionsItemSelected");
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

}
