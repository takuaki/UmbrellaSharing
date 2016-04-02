package umbrella.tokyo.jp.umbrella.http;

import android.location.Location;

import okhttp3.Callback;

/**
 * Created by mori on 4/2/16.
 */
public interface WeatherListener  {
    public void onLoad(Location location, Callback callback);
}
