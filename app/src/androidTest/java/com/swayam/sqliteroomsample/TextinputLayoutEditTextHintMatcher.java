package com.swayam.sqliteroomsample;

import android.support.design.widget.TextInputLayout;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 Created by akhil on 23/2/18.
 */

public class TextinputLayoutEditTextHintMatcher {

    public static Matcher<View> hasTextInputLayoutHintText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getHint();

                if (error == null) {
                    return false;
                }

                String hint = error.toString();

                return expectedErrorText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}

