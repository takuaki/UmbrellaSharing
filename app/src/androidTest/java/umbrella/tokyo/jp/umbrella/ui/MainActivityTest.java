package umbrella.tokyo.jp.umbrella.ui;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import umbrella.tokyo.jp.umbrella.MainActivity;
import umbrella.tokyo.jp.umbrella.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by mori on 3/29/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity>  mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void pushTab() throws Exception{
        onView(withId(R.id.toolbar)).perform(ViewActions.click());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
    }

}
