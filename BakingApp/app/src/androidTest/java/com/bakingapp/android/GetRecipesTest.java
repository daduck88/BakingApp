package com.bakingapp.android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GetRecipesTest {

  @Rule
  public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
      MainActivity.class);

  private IdlingResource mIdlingResource;

  @Before
  public void registerIdlingResource() {
    CountingIdlingResource mainActivityIdlingResource = mActivityRule.getActivity().getEspressoIdlingResourceForMainActivity();
    Espresso.registerIdlingResources(mainActivityIdlingResource);
  }
  @Test
  public void checkGetRecipes() throws Exception {
    onView(ViewMatchers.withId(R.id.list))
        .perform(RecyclerViewActions.actionOnItemAtPosition(1,
            click()));

  }
}
