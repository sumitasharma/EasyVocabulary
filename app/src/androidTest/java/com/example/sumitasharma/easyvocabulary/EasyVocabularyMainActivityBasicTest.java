package com.example.sumitasharma.easyvocabulary;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sumitasharma.easyvocabulary.wordui.MainActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EasyVocabularyMainActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> mEasyVocabularyMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

//        public static ViewAction collapseAppBarLayout() {
//            return new ViewAction() {
//                @Override
//                public Matcher<View> getConstraints() {
//                    return isAssignableFrom(AppBarLayout.class);
//                }
//
//                @Override
//                public String getDescription() {
//                    return "Collapse App Bar Layout";
//                }
//
//                @Override
//                public void perform(UiController uiController, View view) {
//                    AppBarLayout appBarLayout = (AppBarLayout) view;
//                    appBarLayout.setExpanded(false);
//                    uiController.loopMainThreadUntilIdle();
//                }
//            };
//        }

    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */
//        @Test
//        public void clickWordPracticeMenu_ShowsWordsForPractice() {
//
//
//            // First scroll to the position that needs to be matched and click on it.
//            onView(ViewMatchers.withId(R.id.baking_app_main_recycler_view))
//                    .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
//
//            // First scroll to the position that needs to be matched and click on it.
//            //onView(withId(R.id.recipe_steps_recycler_view)).perform(scrollToPosition(2));
//            onView(ViewMatchers.withId(R.id.recipe_steps_recycler_view))
//                    .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Starting prep."))));
//            onView(ViewMatchers.withId(R.id.recipe_steps_recycler_view))
//                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//            // Checks that the correct step instruction is displayed
//            onView(ViewMatchers.withId(R.id.step_instruction_text_view)).check(matches(withText(containsString("Recipe Introduction"))));
//
//            // Checks that the correct message is displayed for video available
//            onView(allOf(withId(R.id.step_video_player_view), withClassName(CoreMatchers.is(SimpleExoPlayerView.class.getName()))));
//        }
//
//        /**
//         * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
//         */
//        @Test
//        public void clickRecipeStep_ShowsStepVideoInstructionFragmentWithoutVideo() {
//
//
//            // First scroll to the position that needs to be matched and click on it.
//            onView(ViewMatchers.withId(R.id.baking_app_main_recycler_view))
//                    .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
//            onView(withId(R.id.app_bar)).perform(collapseAppBarLayout());
//            // First scroll to the position that needs to be matched and click on it.
//            //onView(withId(R.id.recipe_steps_recycler_view)).perform(scrollToPosition(2));
//            onView(ViewMatchers.withId(R.id.recipe_steps_recycler_view))
//                    .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Starting prep."))));
//            onView(ViewMatchers.withId(R.id.recipe_steps_recycler_view))
//                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
//
//            // Checks that the correct step instruction is displayed
//            onView(ViewMatchers.withId(R.id.step_instruction_text_view)).check(matches(withText(containsString("Preheat the oven to 350°F."))));
//
//            // Checks that the correct message is displayed for video available
//            onView(ViewMatchers.withId(R.id.video_not_available)).check(matches(isDisplayed()));
//
//        }
//    }

}
