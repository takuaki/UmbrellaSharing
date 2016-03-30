package umbrella.tokyo.jp.umbrella;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by mori on 3/30/16.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private  GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstance){
        //Inflate the layout for this fragment

        return inflater.inflate(R.layout.map_fragment,null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

    }

}
