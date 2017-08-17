package id.co.imastudio.bakingapp3;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by idn on 8/17/2017.
 */
@RunWith(AndroidJUnit4.class)
public class UiActivityTest {
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity>
            mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkText_MainActivity() {
        onView(withId(R.id.recycler_baking)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @Test
    public void checkDescriptionIsVisible_DetailRecipeDetailActivity() {
        onView(withId(R.id.recycler_baking)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.detailrecipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.detailrecipe_detail)).check(matches(isDisplayed()));
    }



    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }


}
