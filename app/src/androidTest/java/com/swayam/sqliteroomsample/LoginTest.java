package com.swayam.sqliteroomsample;

/*Created by akhil on 21/2/18*/

import android.app.Activity;
import android.os.SystemClock;
import android.support.design.widget.TextInputEditText;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;
import android.widget.EditText;

import com.swayam.sqliteroomsample.Activities.SampleNavigationDrawer;
import com.swayam.sqliteroomsample.Activities.SqliteRoomLoginActivity;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static com.swayam.sqliteroomsample.TextinputLayoutEditTextErrorMatcher.onErrorViewWithinTilWithId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LoginTest  {

    @Rule
  public IntentsTestRule<SqliteRoomLoginActivity> login = new IntentsTestRule<SqliteRoomLoginActivity>
                                                         (SqliteRoomLoginActivity.class);


   @Test
    public void loginfail(){
        onView(withId(R.id.etEmail)).perform(typeText("akhilbv1@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("123456798798"),closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

   }

    @Test
    public void loginsuccess(){
        onView(withId(R.id.etEmail)).perform(typeText("akhil@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("1234567"),closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
      SystemClock.sleep(5000);
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
       // onView(withId(R.id.txtMobileNum)).check(matches(allOf(isDescendantOfA(withId(R.id.fragment_accountinfo)),isDisplayed())));
    }

    @Test
    public void validate_Hint_EditText(){
        String emailHint = login.getActivity().getResources().getString(R.string.reg_email);
        String passwordHint = login.getActivity().getResources().getString(R.string.reg_password);
        onView(withId(R.id.textinputlayout_email)).check(matches(TextinputLayoutEditTextHintMatcher.hasTextInputLayoutHintText(emailHint)));
        onView(withId(R.id.textinputlayout_password)).check(matches(TextinputLayoutEditTextHintMatcher.hasTextInputLayoutHintText(passwordHint)));
    }

    @Test
    public void validate_Empty_EditText(){
        String error_email = login.getActivity().getResources().getString(R.string.empty_email);

        onView(withId(R.id.etEmail)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(""),closeSoftKeyboard());
        onErrorViewWithinTilWithId(R.id.textinputlayout_email).check(matches(withText(error_email)));
       onView(withId(R.id.textinputlayout_email)).check(matches(TextinputLayoutEditTextErrorMatcher.hasTextInputLayoutErrorText(error_email)));
    }

  public Activity getActivityInstance(){
    final Activity[] currentActivity = new Activity[1];
      getInstrumentation().runOnMainSync(new Runnable() {
      public void run() {
        Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
        if (resumedActivities.iterator().hasNext()){
          currentActivity[0] = (Activity) resumedActivities.iterator().next();
        }
      }
    });

    return currentActivity[0];
  }


}
