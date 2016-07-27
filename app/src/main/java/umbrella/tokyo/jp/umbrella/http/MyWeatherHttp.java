package umbrella.tokyo.jp.umbrella.http;

import android.content.Context;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by mori on 7/28/16.
 */
public class MyWeatherHttp implements Callback {

    private Context mContext;
    private HttpUrl mHttpUrl;
    private OkHttpClient mClient;
    private ResponseListener mListener;

    public MyWeatherHttp(Context context) {
        mContext = context;
        mClient = new OkHttpClient.Builder().build();
    }

    public void setUrl(HttpUrl httpUrl) {
        this.mHttpUrl = httpUrl;
    }

    public void executeAsync(ResponseListener listener) {
        mListener = listener;
        Request request = new Request.Builder().url(mHttpUrl).build();
        mClient.newCall(request).enqueue(this);
    }

    public String execute() throws IOException {
        Request request = new Request.Builder().url(mHttpUrl).build();
        Response response = mClient.newCall(request).execute();
        ResponseBody body = response.body();
        return body.string();

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            mListener.onSuccess(response.code());
        } else {
            mListener.onFailure();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        mListener.onFailure();
    }

    public interface ResponseListener {
        void onSuccess(int code);

        void onFailure();
    }
}
