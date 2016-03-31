package umbrella.tokyo.jp.umbrella.util;

import android.support.annotation.NonNull;
import android.support.design.BuildConfig;
import android.util.Log;

/**
 * Created by mori on 3/31/16.
 */
public class LogUtil {
    static public void d(@NonNull String tag,@NonNull String text){
        //if(hasLogPermissoin()){
            Log.d(tag,text);
        //}
    }

    static private boolean hasLogPermissoin(){
        if (BuildConfig.DEBUG){
            return true;
        }else{
            return false;
        }
    }
}
