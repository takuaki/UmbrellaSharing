package umbrella.tokyo.jp.umbrella.http;

import android.content.Context;
import android.location.Location;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Single;
import rx.SingleSubscriber;
import umbrella.tokyo.jp.umbrella.R;

/**
 * Created by mori on 4/2/16.
 */
public class WeatherHttp implements  WeatherListener{


    private static final String TAG = WeatherHttp.class.getSimpleName();
    private URL url = null;
    private String urlStr = "http://api.openweathermap.org/data/2.5/weather?";
    private Context context = null;
    private OkHttpClient client = null;


    public WeatherHttp(Context context){
        this.context = context;
        client = new OkHttpClient.Builder().connectTimeout(5000l, TimeUnit.MILLISECONDS)
                .readTimeout(5000l,TimeUnit.MILLISECONDS).build();
    }

    @Override
    public Single<String> onLoad(Location location) {
        urlStr+= "lat="+location.getLatitude();
        urlStr+= "&lon="+location.getLongitude();
        urlStr+="&APPID="+context.getString(R.string.openweather_key);

        try {
            url = new URL(urlStr);
            final Request request = new Request.Builder().url(url).build();
            return Single.create(new Single.OnSubscribe<String>() {
                @Override
                public void call(final SingleSubscriber<? super String> singleSubscriber) {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            singleSubscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            singleSubscriber.onSuccess(response.body().string());
                        }
                    });
                }
            });
        }catch(MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


}

