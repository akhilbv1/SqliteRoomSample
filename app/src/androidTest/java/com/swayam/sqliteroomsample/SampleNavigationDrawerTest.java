package com.swayam.sqliteroomsample;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.swayam.sqliteroomsample.Activities.SampleNavigationDrawer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**Created by akhil on 27/2/18.
 */

@RunWith(AndroidJUnit4.class)
public class SampleNavigationDrawerTest {

  @Rule
  public ActivityTestRule<SampleNavigationDrawer> sampleNavigationDrawerActivity = new ActivityTestRule<SampleNavigationDrawer>(SampleNavigationDrawer.class){

                                                                                          @Override protected Intent getActivityIntent() {
                                                                                             Context targetContext = getInstrumentation().getTargetContext();
                                                                                             Intent intent = new Intent(targetContext,SampleNavigationDrawer.class);
                                                                                             intent.putExtra("userid","qNtlr");
                                                                                             return intent;
                                                                                                 }};

  @Test
  public void check_Open_NavigationDrawer_All(){
    int sleep_time = 500;
    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_chng_pass));
    onView(withId(R.id.change_password_fragment)).check(matches(isDisplayed()));
    SystemClock.sleep(sleep_time);

    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile));
    onView(withId(R.id.fragment_accountinfo)).check(matches(isDisplayed()));
    SystemClock.sleep(sleep_time);

    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_users_list));
    onView(withId(R.id.userslist_fragment)).check(matches(isDisplayed()));
    SystemClock.sleep(sleep_time);


    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_about));
    onView(withId(R.id.aboutus_fragment)).check(matches(isDisplayed()));
    SystemClock.sleep(sleep_time);


    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_signout));
    onView(withId(R.id.sqliteroomlogin)).check(matches(isDisplayed()));
  }
}
