package com.sumitasharma.app.easyvocabulary;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import com.sumitasharma.app.easyvocabulary.util.WordUtil;
import com.sumitasharma.app.easyvocabulary.wordui.MainActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EasyVocabularyWiFiTest {


    @Rule
    public final ActivityTestRule<MainActivity> mEasyVocabularyMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testWhenInternetIsAvailable() throws Exception {
        Context context = mEasyVocabularyMainActivityTestRule.getActivity().getBaseContext();
        if (!WordUtil.isOnline(context)) {
            getInstrumentation().waitForIdleSync();
            Thread.sleep(500);
            Assert.assertEquals(false, WordUtil.isOnline(context));
            onView(withText(R.string.internet_connectivity))
                    .check(matches(isDisplayed()));
        } else {
            getInstrumentation().waitForIdleSync();
            Thread.sleep(500);
            onView(withId(R.id.word_main_fragment)).check(matches(isDisplayed()));
        }

    }

}
