package com.oqupie.portalfinderexample;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import android.view.View;

import com.oqupie.oqupiesupport.WebViewActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.web.sugar.Web.onWebView;


public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<WebViewActivity> mWebViewActivityRule = new ActivityTestRule<WebViewActivity>(WebViewActivity.class, false, false) {
        @Override
        protected void afterActivityLaunched() {
            // Technically we do not need to do this - WebViewActivity has javascript turned on.
            // Other WebViews in your app may have javascript turned off, however since the only way
            // to automate WebViews is through javascript, it must be enabled.
            onWebView().forceJavascriptEnabled();
        }
    };

    /**
     * Perform action of waiting for a specific time.
     */
    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    @Test
    public void test1() {

        onView(withId(R.id.buttonDetectAppInfo)).perform(click());
        onView(isRoot()).perform(waitFor(2000));

        onView(withId(R.id.buttonOpenWebView)).perform(click());
        onView(isRoot()).perform(waitFor(10000));

        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(waitFor(2000));

        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(waitFor(2000));
    }
}
