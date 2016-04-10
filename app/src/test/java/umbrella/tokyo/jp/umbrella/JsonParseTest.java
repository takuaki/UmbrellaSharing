package umbrella.tokyo.jp.umbrella;

import android.app.Application;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import umbrella.tokyo.jp.umbrella.util.WeatherForecastsJson;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by mori on 4/10/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class JsonParseTest {

    private static String readFileFromAssets(String filename,Context c){
        try{
            InputStream is = c.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text  = new String(buffer);
            return text;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testJsonParser() throws  Exception{
        Application context = RuntimeEnvironment.application;
        String string = readFileFromAssets("weatherforecast.txt",context);
        JSONObject jsonObject = new JSONObject(string);
        WeatherForecastsJson weatherForecastsJson = new WeatherForecastsJson(jsonObject);

        //assertion for city
        assertThat("Shuzenji",is(weatherForecastsJson.city.name));
        assertThat(1851632,is(weatherForecastsJson.city.id));
        assertThat("JP",is(weatherForecastsJson.city.country));
        //code
        assertThat("200",is(weatherForecastsJson.cod));
        //message
        assertThat(0.0045f,is(weatherForecastsJson.message));
        //list
        List<WeatherForecastsJson.Item> items= weatherForecastsJson.getListItem();
        assertThat(1,is(items.size()));
        WeatherForecastsJson.Item item = items.get(0);
        assertThat(1406106000,is(item.dt));
        //list main
        assertThat(298.77f,is(item.main.temp));
        assertThat(298.77f,is(item.main.temp_min));
        assertThat(298.774f,is(item.main.temp_max));
        assertThat(1005.93f,is(item.main.pressure));
        assertThat(1018.18f,is(item.main.sea_level));
        assertThat(1005.93f,is(item.main.grnd_level));
        assertThat(87,is(item.main.humidity));
        assertThat(0.26f,is(item.main.temp_kf));
        //list weather
        List<WeatherForecastsJson.Item.Weather> weathers = item.getWeatherList();
        assertThat(1,is(weathers.size()));
        WeatherForecastsJson.Item.Weather weather = weathers.get(0);
        assertThat(804,is(weather.id));
        assertThat("Clouds",is(weather.main));
        assertThat("overcast clouds",is(weather.description));
        // clouds
        assertThat(88,is(item.clouds.all));

        assertThat(5.71f,is(item.wind.speed));
        assertThat(229.501f,is(item.wind.deg));
        //dt_txt
        assertThat("2014-07-23 09:00:00",is(item.dt_txt));

    }

    @Test
    public void test() throws Exception{
        Application context = RuntimeEnvironment.application;
        String string = readFileFromAssets("json.txt",context);
        JSONObject jsonObject = new JSONObject(string);
        Double value=jsonObject.getDouble("value");
        String name =jsonObject.getString("name");
        assertThat(1.0,is(value));
        assertThat("name",is(name));
    }

    @Test(expected = JSONException.class)
    public void testError() throws Exception{
        Application context = RuntimeEnvironment.application;
        String string = readFileFromAssets("json.txt",context);
        JSONObject jsonObject = new JSONObject(string);
        jsonObject.getString("error");
    }

}
