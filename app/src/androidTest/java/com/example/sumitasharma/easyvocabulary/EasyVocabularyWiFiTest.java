package com.example.sumitasharma.easyvocabulary;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import com.example.sumitasharma.easyvocabulary.util.WordUtil;
import com.example.sumitasharma.easyvocabulary.wordui.MainActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

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
            Assert.assertEquals(false, WordUtil.isOnline(context));
            onView(withText(R.string.internet_connectivity))
                    .check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.word_main_fragment)).check(matches(isDisplayed()));
        }

    }

}
