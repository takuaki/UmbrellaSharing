package umbrella.tokyo.jp.umbrella;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import umbrella.tokyo.jp.umbrella.util.WeatherUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mori on 4/17/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class WeatherIconUnitTest {


    @Test
    public void testDescription() throws Exception{
        assertThat(WeatherUtil.Description.CLEAR_SKY,is(WeatherUtil.Description.convey("clear sky")));
        assertThat(WeatherUtil.Description.FEW_CLOUDS,is(WeatherUtil.Description.convey("few clouds")));
        assertThat(WeatherUtil.Description.SCATTERED_CLOUDS,is(WeatherUtil.Description.convey("scatter clouds")));
        assertThat(WeatherUtil.Description.BROKEN_CLOUDS,is(WeatherUtil.Description.convey("broken clouds")));
        assertThat(WeatherUtil.Description.SHOWER_RAIN,is(WeatherUtil.Description.convey("shower rain")));
        assertThat(WeatherUtil.Description.RAIN,is(WeatherUtil.Description.convey("rain")));
        assertThat(WeatherUtil.Description.THUNDERSTORM,is(WeatherUtil.Description.convey("thunderstorm")));
        assertThat(WeatherUtil.Description.SNOW,is(WeatherUtil.Description.convey("snow")));
        assertThat(WeatherUtil.Description.MIST,is(WeatherUtil.Description.convey("mist")));
    }

    @Test
    public void testIconResource() throws  Exception{
        Context context = RuntimeEnvironment.application;
        assertThat(context.getString(R.string.wi_day_sunny),is(WeatherUtil.getIconResource(context, WeatherUtil.Description.CLEAR_SKY)));
        assertThat(context.getString(R.string.wi_day_cloudy),is(WeatherUtil.getIconResource(context, WeatherUtil.Description.FEW_CLOUDS)));
        assertThat(context.getString(R.string.wi_cloudy),is(WeatherUtil.getIconResource(context, WeatherUtil.Description.SCATTERED_CLOUDS)));
        assertThat(context.getString(R.string.wi_rain_mix),is(WeatherUtil.getIconResource(context, WeatherUtil.Description.BROKEN_CLOUDS)));
        assertThat(context.getString(R.string.wi_rain),is(WeatherUtil.getIconResource(context, WeatherUtil.Description.RAIN)));
        assertThat(context.getString(R.string.wi_thunderstorm),is(WeatherUtil.getIconResource(context, WeatherUtil.Description.THUNDERSTORM)));
        assertThat(context.getString(R.string.wi_snow),is(WeatherUtil.getIconResource(context, WeatherUtil.Description.SNOW)));
        assertThat(context.getString(R.string.wi_sprinkle),is(WeatherUtil.getIconResource(context, WeatherUtil.Description.MIST)));
    }

}
