package umbrella.tokyo.jp.umbrella.http;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mori on 7/27/16.
 * <p/>
 * 天気情報を取得するクラスのテスト
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21)
public class WeatherHttpTest {

    private String mockUrl = "weather/get";

    //private OkHttpClient mClient;

    private MyWeatherHttp mMyWeatherHttp;
    private MockWebServer mMockWebServer;
    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = RuntimeEnvironment.application.getApplicationContext();
        mMyWeatherHttp = new MyWeatherHttp(mContext);
        mMockWebServer = new MockWebServer();
    }

    @Test(timeout = 5000l)
    public void testWeatherAPI() throws Exception {
        mMockWebServer.enqueue(new MockResponse().setBody("success"));
        mMockWebServer.start();
        HttpUrl baseUrl = mMockWebServer.url(mockUrl);

        mMyWeatherHttp.setUrl(baseUrl);
        String response = mMyWeatherHttp.execute();
        assertThat(response, is("success"));
        mMockWebServer.shutdown();
    }

    @Test(timeout = 5000l)
    public void testWeatherAPIAsync() throws Exception {
        final CountDownLatch countLatch = new CountDownLatch(1);
        mMockWebServer.enqueue(new MockResponse().setBody("success"));
        mMockWebServer.start();
        HttpUrl baseUrl = mMockWebServer.url(mockUrl);

        mMyWeatherHttp.setUrl(baseUrl);
        mMyWeatherHttp.executeAsync(new MyWeatherHttp.ResponseListener() {
            @Override
            public void onSuccess(int code) {
                assertThat(200, is(code));
                countLatch.countDown();
            }

            @Override
            public void onFailure() {
                countLatch.countDown();
            }
        });
        countLatch.await();
        mMockWebServer.shutdown();
    }


}
