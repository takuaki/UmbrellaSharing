package umbrella.tokyo.jp.umbrella.util;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mori on 4/10/16.
 */
final public class WeatherForecastsJson{

    public final String cod;
    public final float message;
    public final City city;
    public final int  cnt ;
    private List<Item> itemList = new ArrayList<>();

    public WeatherForecastsJson(JSONObject jsonObject) throws JSONException{
        this.cod = jsonObject.getString("cod");
        this.message = (float) jsonObject.getDouble("message");
        this.cnt = jsonObject.getInt("cnt");
        this.city = new City(jsonObject.getJSONObject("city"));
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for(int i=0;i<jsonArray.length();i++){
            itemList.add(new Item(jsonArray.getJSONObject(i)));
        }
    }

    public String getCityName(){
        return this.city.name;
    }
    public List<Item> getListItem(){
        return Collections.unmodifiableList(this.itemList);
    }

    public class City{
        public Integer id;
        public String name;
        public Coord coord;
        public String country;

        City(JSONObject object) throws JSONException{
            this.id = object.getInt("id");
            this.name = object.getString("name");
            this.coord = new Coord(object.getJSONObject("coord"));
            this.country = object.getString("country");
        }

        public class Coord{
            float lat;
            float lon;
            public Coord(JSONObject object) throws JSONException{
                this.lat = (float) object.getDouble("lat");
                this.lon = (float) object.getDouble("lon");
            }
        }
    }

    public class Item{
        public int dt ;
        public Main main;
        private List<Weather> weatherList = new ArrayList<>();
        public Clouds clouds;
        @Nullable  public Wind   wind;
        @Nullable  public Rain   rain;
        @Nullable  public Snow   snow;
        public String dt_txt;



        public Item(JSONObject jsonObject) throws JSONException{
            this.dt = jsonObject.getInt("dt");
            this.main=  new Main(jsonObject.getJSONObject("main"));
            this.clouds = new Clouds(jsonObject.getJSONObject("clouds"));
            this.wind = new Wind(jsonObject.getJSONObject("wind"));
            //these two options are optional.. can be null
            try {
                this.rain = new Rain(jsonObject.getJSONObject("rain"));
                this.snow = new Snow(jsonObject.getJSONObject("snow"));
            }catch(JSONException e){
                e.printStackTrace();
            }
            this.dt_txt = jsonObject.getString("dt_txt");
            JSONArray array = jsonObject.getJSONArray("weather");
            for(int i=0;i<array.length();i++){
                this.weatherList.add(new Weather(array.getJSONObject(i)));
            }
        }

        public List<Weather> getWeatherList(){
            return Collections.unmodifiableList(weatherList);
        }

        public class Main{
            public float temp ;
            public float temp_min;
            public float temp_max;
            public float pressure;
            public float sea_level;
            public float grnd_level;
            public int humidity;
            public float temp_kf;
            public Main(JSONObject jsonObject) throws JSONException{
                temp = (float)jsonObject.getDouble("temp");
                temp_min = (float)jsonObject.getDouble("temp_min");
                temp_max = (float)jsonObject.getDouble("temp_max");
                pressure = (float)jsonObject.getDouble("pressure");
                sea_level = (float)jsonObject.getDouble("sea_level");
                grnd_level = (float)jsonObject.getDouble("grnd_level");
                humidity = jsonObject.getInt("humidity");
                temp_kf = (float) jsonObject.getDouble("temp_kf");
            }
        }
        public final class Weather{
            public int id;
            public String main;
            public String description;
            public String icon;
            public Weather(JSONObject jsonObject) throws JSONException{
                this.id = jsonObject.getInt("id");
                this.main = jsonObject.getString("main");
                this.description = jsonObject.getString("description");
                this.icon  = jsonObject.getString("icon");
            }
        }
        public class Clouds{
            public int all;
            public Clouds(JSONObject jsonObject) throws  JSONException{
                this.all = jsonObject.getInt("all");
            }
        }
        public class Wind{
            public float speed;
            public float deg;
            public Wind(@Nullable JSONObject jsonObject) throws JSONException{
                this.speed =  (float)jsonObject.getDouble("speed");
                this.deg = (float)jsonObject.getDouble("deg");
            }
        }
        public class Rain{
            public float _3h;
            public Rain(@Nullable JSONObject jsonObject) throws JSONException{
                if(jsonObject==null){
                    return;
                }
                this._3h = (float)jsonObject.getDouble("3h");
            }
        }
        public class Snow{
            public float _3h;
            public Snow(@Nullable JSONObject jsonObject) throws  JSONException{
                if(jsonObject==null){
                    return;
                }
                this._3h = (float)jsonObject.getDouble("3h");
            }
        }
    }

}
