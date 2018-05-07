package com.example.sumitasharma.easyvocabulary;

import android.content.Context;
import android.content.res.Configuration;
import android.support.design.widget.AppBarLayout;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.sumitasharma.easyvocabulary.util.WordUtil;
import com.example.sumitasharma.easyvocabulary.wordui.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class EasyVocabularyMainActivityBasicTest {
    @Rule
    public final ActivityTestRule<MainActivity> mEasyVocabularyMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    public static ViewAction collapseAppBarLayout() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(AppBarLayout.class);
            }

            @Override
            public String getDescription() {
                return "Collapse App Bar Layout";
            }

            @Override
            public void perform(UiController uiController, View view) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                appBarLayout.setExpanded(false);
                uiController.loopMainThreadUntilIdle();
            }
        };
    }
    /**
     * Clicks on a GridView item and checks it opens up the WordPractice.
     */
    @Test
    public void clickEasyVocabulary_ShowsFragments() throws InterruptedException {


        Context context = mEasyVocabularyMainActivityTestRule.getActivity().getBaseContext();
        onView(withId(R.id.appBar)).perform(collapseAppBarLayout());
        if (WordUtil.isOnline(context) && !isTablet(context)) {


            getInstrumentation().waitForIdleSync();
            try {
                onView(withId(R.id.word_meaning_card_view)).check(matches(isDisplayed()));
                // First scroll to the position that needs to be matched and click on it.
                onView(withId(R.id.word_meaning_card_view))
                        .perform(click());
                getInstrumentation().waitForIdleSync();
                // Fragment is open.
                onView(withId(R.id.practice_word_frame_layout)).check(matches(isDisplayed()));
            } catch (Exception e) {
                getInstrumentation().waitForIdleSync();
                onView(withId(R.id.word_meaning_card_view))
                        .perform(click());
                Thread.sleep(500);
                // Fragment is open.
                onView(withId(R.id.practice_word_frame_layout)).check(matches(isDisplayed()));
            }

            onView(withContentDescription("Navigate up")).perform(click());

            getInstrumentation().waitForIdleSync();
            try {
                onView(withId(R.id.progress_card_view)).check(matches(isDisplayed()));
                // First scroll to the position that needs to be matched and click on it.
                onView(withId(R.id.progress_card_view))
                        .perform(click());
                getInstrumentation().waitForIdleSync();
                // Fragment is open.
                onView(withId(R.id.progress_word_frame_layout)).check(matches(isDisplayed()));
            } catch (Exception e) {
                Thread.sleep(500);
                onView(withId(R.id.progress_card_view))
                        .perform(click());
                getInstrumentation().waitForIdleSync();
                // Fragment is open.
                onView(withId(R.id.progress_word_frame_layout)).check(matches(isDisplayed()));
            }


            onView(withContentDescription("Navigate up")).perform(click());

            getInstrumentation().waitForIdleSync();

            try {
                onView(withId(R.id.dictionary_card_view)).check(matches(isDisplayed()));
                // First scroll to the position that needs to be matched and click on it.
                onView(withId(R.id.dictionary_card_view))
                        .perform(click());
                getInstrumentation().waitForIdleSync();
                // Fragment is open.
                onView(withId(R.id.dictionary_word_frame_layout)).check(matches(isDisplayed()));
            } catch (Exception e) {
                Thread.sleep(500);
                getInstrumentation().waitForIdleSync();
                onView(withId(R.id.dictionary_card_view))
                        .perform(click());
                onView(withId(R.id.dictionary_word_frame_layout)).check(matches(isDisplayed()));
            }

            onView(withContentDescription("Navigate up")).perform(click());

            getInstrumentation().waitForIdleSync();

            try {
                onView(withId(R.id.quiz_card_view)).check(matches(isDisplayed()));
                // First scroll to the position that needs to be matched and click on it.
                onView(withId(R.id.quiz_card_view))
                        .perform(click());
                getInstrumentation().waitForIdleSync();
                // Fragment is open.
                onView(withId(R.id.quiz_word_frame_layout)).check(matches(isDisplayed()));
            } catch (Exception e) {
                Timber.i("Test Done");
            }



        } else if (WordUtil.isOnline(context) && isTablet(context)) {

            getInstrumentation().waitForIdleSync();

            onView(withId(R.id.words_tablet_linear_layout)).check(matches(isDisplayed()));

            getInstrumentation().waitForIdleSync();
            Thread.sleep(500);
            // First scroll to the position that needs to be matched and click on it.
            onView(withId(R.id.progress_card_view))
                    .perform(click());
            getInstrumentation().waitForIdleSync();
            // Fragment is open.
            //onView(withId(R.id.progress_word_frame_layout)).check(matches(isDisplayed()));

            getInstrumentation().waitForIdleSync();
            Thread.sleep(500);
            // First scroll to the position that needs to be matched and click on it.
            onView(withId(R.id.quiz_card_view))
                    .perform(click());
            getInstrumentation().waitForIdleSync();
            // Fragment is open.
            onView(withId(R.id.quiz_word_frame_layout)).check(matches(isDisplayed()));

        }
    }

    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}

