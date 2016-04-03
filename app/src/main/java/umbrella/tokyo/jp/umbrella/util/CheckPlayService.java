package umbrella.tokyo.jp.umbrella.util;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by mori on 4/3/16.
 */
public final class CheckPlayService{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = CheckPlayService.class.getSimpleName();

    static final public boolean checkPlayServices(final Activity context){

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        LogUtil.d(TAG,"onResult "+resultCode);
        if(resultCode != ConnectionResult.SUCCESS){
            if(apiAvailability.isUserResolvableError(resultCode)){
                apiAvailability.getErrorDialog(context,resultCode,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else{
                LogUtil.i(TAG,"This device is not supported");
                context.finish();
            }
            return false;
        }
        return true;
    }

}
