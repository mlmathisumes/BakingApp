package com.example.bakingapp;

import android.view.View;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.bakingapp.common.Common;
import com.example.bakingapp.ui.MainActivity;
import com.example.bakingapp.ui.RecipeDetailActivity;

import org.hamcrest.Matchers;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;

@RunWith(AndroidJUnit4.class)
public class RecipeClickThruTest {


    private IdlingResource idlingResource;

    // Register IdlingResource
    @Before
    public void registerIdlingResource() {
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                idlingResource = activity.getIldingResource();
                IdlingRegistry.getInstance().register(idlingResource);
            }

        });
    }

        @Test
    public void selectRecipeTest(){
        onView(ViewMatchers.withId(R.id.rv_recipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(allOf(Matchers.<View>instanceOf(TextView.class),
            withParent(withResourceName("action_bar")))).check(matches(withText("Nutella Pie")));

        onView(ViewMatchers.withId(R.id.ec_ingredients)).perform(click());

        onView(ViewMatchers.withId(R.id.rc_step)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.view_pager)).perform(customSwipeLeft());

        onView(withId(R.id.btn_next)).perform(click());

    }

    // Custom ViewAction swipe function for viewPager
    private ViewAction customSwipeRight() {
        return  new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_LEFT, GeneralLocation.BOTTOM_RIGHT, Press.FINGER);
    }

    // Custom ViewAction swipe function for viewPager
    private ViewAction customSwipeLeft() {
        return  new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_RIGHT, GeneralLocation.BOTTOM_LEFT, Press.FINGER);
    }

    // Unregister IdlingResource

    @After
    public void unregisterIdlingResources(){
        if(idlingResource != null){
            IdlingRegistry.getInstance().unregister(idlingResource);

        }
    }


}
