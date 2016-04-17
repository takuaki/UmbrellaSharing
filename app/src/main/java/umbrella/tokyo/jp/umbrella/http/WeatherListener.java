package umbrella.tokyo.jp.umbrella.http;

import android.location.Location;

import rx.Single;

/**
 * Created by mori on 4/2/16.
 */
public interface WeatherListener  {
    Single<String> onLoad(Location location);
}
