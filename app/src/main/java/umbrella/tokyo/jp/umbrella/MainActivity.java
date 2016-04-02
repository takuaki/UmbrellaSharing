package umbrella.tokyo.jp.umbrella;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.PermissionChecker;
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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback , Callback{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int zoom = 15;
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

        FloatingActionButton fabMyLocation = (FloatingActionButton) findViewById(R.id.fab_mylocation);
        fabMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMyLocation(getBaseContext());
            }
        });
        FloatingActionButton fabCalling = (FloatingActionButton)findViewById(R.id.fab_call);
        fabCalling.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // calling !!
            }
        });

        mWeatherCard = (CardView)findViewById(R.id.weather_card);
        mMapFragment =(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        setMyLocation(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mMap!=null){
            setMyLocation(this);
        }
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


    private void setMyLocation(@NonNull Context context){
        if(PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            throw new RuntimeException("permission denied : ACCESS_FILE_LOCATION");
        }
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        weatherHttp.onLoad(location,this);
        //map animation
        mMap.addMarker(new MarkerOptions().position(latLng).title("MyMarker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
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
