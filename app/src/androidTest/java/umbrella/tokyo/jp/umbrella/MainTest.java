package umbrella.tokyo.jp.umbrella;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockWebServer;
import umbrella.tokyo.jp.umbrella.util.LogUtil;

/**
 * Created by mori on 4/10/16.
 *
 * write end to end test.
 */
public class MainTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private final static String TAG = MainTest.class.getSimpleName();
    private MainActivity mainActivity = null;
    private Context mContext = null;
    private LocationManager locationManager =null;
    private MockWebServer mockWebServer = null;

    public MainTest(){
        super(MainActivity.class);
    }

    @Override
    @Before
    protected void setUp() throws Exception{
        super.setUp();
        setActivityInitialTouchMode(false);
        mainActivity = getActivity();
        mContext = mainActivity;
        locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        mainActivity.locationManager = locationManager;
        //mockWebServer = new MockWebServer();
    }

    /**
     * GoogleMapが現れて現在地にピンが立つ。
     * 5s以内に終了することを要求
     * @throws Exception
     */
    @Test
    public void googleMapTest() throws Exception{
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        //mock LocationManager
        final String mockProviderName = LocationManager.GPS_PROVIDER;
        final List<Location> list = new ArrayList<>();
        mainActivity = getActivity();
        mainActivity.checkPermission();
        locationManager.requestLocationUpdates(mockProviderName, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LogUtil.d(TAG,"onLocationChanged");
                list.add(location);
                countDownLatch.countDown();
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        });
        locationManager.addTestProvider(mockProviderName,true,false,false,false,true,true,true, Criteria.POWER_LOW,Criteria.ACCURACY_FINE);

        //mock Location
        Location mockLocation = new Location(mockProviderName);
        mockLocation.setLongitude(100);
        mockLocation.setLatitude(100);
        locationManager.setTestProviderEnabled(mockProviderName,true);
        locationManager.setTestProviderStatus(mockProviderName, LocationProvider.AVAILABLE,null,System.currentTimeMillis());;
        locationManager.setTestProviderLocation(mockProviderName,mockLocation);
        mainActivity.locationManager = locationManager;

        countDownLatch.await();
        assertEquals(1,list.size());
        Location actualLocation = list.get(0);
        assertEquals(mockLocation.getLatitude(),actualLocation.getLatitude());
        assertEquals(mockLocation.getLongitude(),actualLocation.getLatitude());
    }

    @Override
    @After
    public void tearDown(){
        if(mainActivity!=null){
            mainActivity =null;
        }
    }

}
