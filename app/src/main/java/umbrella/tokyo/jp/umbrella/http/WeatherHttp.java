package umbrella.tokyo.jp.umbrella.http;

import android.content.Context;
import android.location.Location;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import umbrella.tokyo.jp.umbrella.R;
import umbrella.tokyo.jp.umbrella.util.LogUtil;

/**
 * Created by mori on 4/2/16.
 */
public class WeatherHttp implements  WeatherListener{


    private static final String TAG = WeatherHttp.class.getSimpleName();
    private URL url = null;
    private Context context = null;
    private OkHttpClient client = new OkHttpClient();


    public WeatherHttp(Context context){
        this.context = context;
    }

    @Override
    public void onLoad(Location location, Callback callback) {
        String urlStr = "http://api.openweathermap.org/data/2.5/weather?";
        urlStr+= "lat="+location.getLatitude();
        urlStr+= "&lon="+location.getLongitude();
        urlStr+="&APPID="+context.getString(R.string.openweather_key);
        LogUtil.d(TAG,"url is "+urlStr);

        try {
            url = new URL(urlStr);
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(callback);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}

