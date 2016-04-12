package umbrella.tokyo.jp.umbrella;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLocationManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by mori on 4/13/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class MainEndToEndTest {

    private MainActivity mainActivity = null;
    private Context mContext = null;
    private LocationManager locationManager = null;
    private ShadowLocationManager shadowLocationManager =null;
    private String MOCK_PROVIDER = "TEST";

    @Before
    public void setUp(){
        mContext = RuntimeEnvironment.application;
        //mainActivity = Robolectric.setupActivity(MainActivity.class);
        locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        shadowLocationManager = Shadows.shadowOf(locationManager);
    }

    @Test
    public void MainActivityEndToEndTest(){
        Location expectedLocation = location(MOCK_PROVIDER,12.0,20.0);
        shadowLocationManager.simulateLocation(expectedLocation);
        //Location actualLocatoin = mainActivi
        Location actualLocation = locationManager.getLastKnownLocation(MOCK_PROVIDER);
        assertThat(expectedLocation,is(actualLocation));
    }

    private Location location(String provider,double latitude,double longitude){
        Location location = new Location(provider);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setTime(System.currentTimeMillis());
        return location;
    }

}
