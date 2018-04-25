package com.example.sumitasharma.easyvocabulary.wordui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.ProgressFragment;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ProgressFragment progressFragment = new ProgressFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.progress_word_frame_layout, progressFragment).commit();
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
