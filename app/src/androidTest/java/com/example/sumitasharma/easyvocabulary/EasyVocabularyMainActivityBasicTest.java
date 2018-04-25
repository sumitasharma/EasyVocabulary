package com.example.sumitasharma.easyvocabulary;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sumitasharma.easyvocabulary.util.WordUtil;
import com.example.sumitasharma.easyvocabulary.wordui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class EasyVocabularyMainActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> mEasyVocabularyMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a GridView item and checks it opens up the WordPractice.
     */
    @Test
    public void clickEasyVocabulary_ShowsFragments() {

        Context context = mEasyVocabularyMainActivityTestRule.getActivity().getBaseContext();
        if (WordUtil.isOnline(context)) {
            // First scroll to the position that needs to be matched and click on it.
            onView(withId(R.id.word_meaning_card_view))
                    .perform(click());
            // Fragment is open.
            onView(withId(R.id.practice_word_frame_layout)).check(matches(isDisplayed()));

            onView(withContentDescription("Navigate up")).perform(click());

            // First scroll to the position that needs to be matched and click on it.
            onView(withId(R.id.progress_card_view))
                    .perform(click());
            // Fragment is open.
            onView(withId(R.id.progress_word_frame_layout)).check(matches(isDisplayed()));

            onView(withContentDescription("Navigate up")).perform(click());

            // First scroll to the position that needs to be matched and click on it.
            onView(withId(R.id.dictionary_card_view))
                    .perform(click());
            // Fragment is open.
            onView(withId(R.id.dictionary_word_frame_layout)).check(matches(isDisplayed()));

            onView(withContentDescription("Navigate up")).perform(click());

            // First scroll to the position that needs to be matched and click on it.
            onView(withId(R.id.quiz_card_view))
                    .perform(click());
            // Fragment is open.
            onView(withId(R.id.quiz_word_frame_layout)).check(matches(isDisplayed()));
        }
    }
}

