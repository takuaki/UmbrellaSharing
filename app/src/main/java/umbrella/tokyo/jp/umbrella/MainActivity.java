package umbrella.tokyo.jp.umbrella;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import umbrella.tokyo.jp.umbrella.http.WeatherHttp;
import umbrella.tokyo.jp.umbrella.util.LogUtil;
import umbrella.tokyo.jp.umbrella.util.WeatherJson;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback , Callback,LocationListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int zoom = 15;
    private static long  minTime = 1000*10l;
    private static float minDistance = 5;

    private SupportMapFragment mMapFragment = null;
    private  GoogleMap mMap =null;
    private CardView mWeatherCard = null;
    private LocationManager locationManager;
    private WeatherHttp weatherHttp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherHttp = new WeatherHttp(this);
        mWeatherCard = (CardView)findViewById(R.id.weather_card);
        mMapFragment =(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        FloatingActionButton fabMyLocation = (FloatingActionButton) findViewById(R.id.fab_mylocation);
        fabMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = getLastKnownLocation();
                setMyLocation(location);
            }
        });
        FloatingActionButton fabCalling = (FloatingActionButton)findViewById(R.id.fab_call);
        fabCalling.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // calling !!
            }
        });

    }

    /**
     * カメラ更新
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Location location = getLastKnownLocation();
        setMyLocation(location);
    }

    @Override
    protected void onResume() {
        LogUtil.d(TAG,"onResume");
        super.onResume();

    }

    @Override
    public void onStart(){
        super.onStart();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(checkPermission()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minDistance,this);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if(checkPermission()){
            locationManager.removeUpdates(this);
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        LogUtil.d(TAG,"onLocationChanged location "+(location!=null));
        setMyLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LogUtil.d(TAG,"onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        LogUtil.d(TAG,"onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        LogUtil.d(TAG,"onProviderDisabled");
    }



    private void setMyLocation(@Nullable Location location){
        if(location==null) return ;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        weatherHttp.onLoad(location,this);
        //map animation
        mMap.addMarker(new MarkerOptions().position(latLng).title("MyMarker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    private Location getLastKnownLocation(){
        if(!checkPermission()) return null;
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private boolean checkPermission(){
        if(locationManager!=null){
            if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)||
                    ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    //weather callback
    @Override
    @WorkerThread
    public void onFailure(Call call, IOException e) {
        LogUtil.d(TAG,"onFailure");

    }

    @Override
    @WorkerThread
    public void onResponse(Call call, Response response) throws IOException {
        LogUtil.d(TAG,"onResponse");
        try {
            WeatherJson weatherJson = new WeatherJson(new JSONObject(response.body().string()));
            final String city = weatherJson.getName();
            final String weather = weatherJson.getMainWeather();
            final String description = weatherJson.getDescription();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView)mWeatherCard.findViewById(R.id.city)).setText(city);
                    ((TextView)mWeatherCard.findViewById(R.id.description)).setText(description);
                }
            });
        }catch(JSONException e){
            e.printStackTrace();
        }

    }
}
