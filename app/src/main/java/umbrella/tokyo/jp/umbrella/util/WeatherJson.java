package umbrella.tokyo.jp.umbrella.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mori on 4/2/16.
 */
public class WeatherJson {
    private JSONObject coord ;
    private JSONObject weather ;
    private JSONObject main ;

    //from coord
    private float lon ;
    private float lat;
    //from weather
    private String main_weather;
    private String description;
    //from main
    private float temp;
    private float humidity;
    private float pressure;
    private float temp_min;
    private float temp_max;

    private String name;

    public WeatherJson(final JSONObject jsonObject) throws JSONException{
        coord = jsonObject.getJSONObject("coord");
        weather = jsonObject.getJSONArray("weather").getJSONObject(0);
        main = jsonObject.getJSONObject("main");

        name = jsonObject.getString("name");

        lon = (float)coord.getDouble("lon");
        lat = (float)coord.getDouble("lat");

        main_weather = weather.getString("main");
        description = weather.getString("description");

        temp  = (float)main.getDouble("temp");
        humidity = (float)main.getDouble("humidity");
        pressure = (float)main.getDouble("pressure");
        temp_min = (float)main.getDouble("temp_min");
        temp_max = (float)main.getDouble("temp_max");

    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }

    public String getMainWeather() {
        return main_weather;
    }

    public String getDescription() {
        return description;
    }

    public float getTemp() {
        return temp;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getTempMin() {
        return temp_min;
    }

    public float getTempMax() {
        return temp_max;
    }

    public String getName() {
        return name;
    }
}
