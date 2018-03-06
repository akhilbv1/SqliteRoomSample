package com.swayam.sqliteroomsample;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import com.swayam.sqliteroomsample.Activities.SqliteRoomRegistrationActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

/**Created by akhil on 23/2/18.
 */

public class RegistrationActivityTest {

    @Rule
    public  ActivityTestRule<SqliteRoomRegistrationActivity> registration = new ActivityTestRule<SqliteRoomRegistrationActivity>(SqliteRoomRegistrationActivity.class);


    @Test
    public void check_Hint_Edittext(){
        String emailHint = registration.getActivity().getResources().getString(R.string.reg_email);
        String passwordHint = registration.getActivity().getResources().getString(R.string.reg_password);
        String usernameHint = registration.getActivity().getResources().getString(R.string.reg_username);
        String mobilenumberHint = registration.getActivity().getResources().getString(R.string.reg_mobile_num);

        onView(withId(R.id.TextInputUsername)).check(matches(TextinputLayoutEditTextHintMatcher.hasTextInputLayoutHintText(usernameHint)));
        onView(withId(R.id.TextInputEmail)).check(matches(TextinputLayoutEditTextHintMatcher.hasTextInputLayoutHintText(emailHint)));
        onView(withId(R.id.TextInputMobileNum)).check(matches(TextinputLayoutEditTextHintMatcher.hasTextInputLayoutHintText(mobilenumberHint)));
        onView(withId(R.id.TextInputPassword)).check(matches(TextinputLayoutEditTextHintMatcher.hasTextInputLayoutHintText(passwordHint)));
    }

    @Test
    public void check_Registration(){
        onView(withId(R.id.etEmail)).perform(typeText("satya@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.etUserName)).perform(typeText("satya"),closeSoftKeyboard());
        onView(withId(R.id.etMobileNum)).perform(typeText("8341770556"),closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("123465789"),closeSoftKeyboard());
        onView(withId(R.id.etConfPassword)).perform(typeText("123465789"),closeSoftKeyboard());
        onView(withId(R.id.btnSubmit)).perform(click());

        onView(withId(R.id.etPassword)).check(matches(allOf(isDescendantOfA(withId(R.id.sqliteroomlogin)), isDisplayed())));
    }
}
