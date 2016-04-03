package umbrella.tokyo.jp.umbrella.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import umbrella.tokyo.jp.umbrella.R;
import umbrella.tokyo.jp.umbrella.util.LogUtil;

/**
 * Created by mori on 4/3/16.
 *
 * GCM用にGoogleサーバーにアプリケーションを登録する
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = RegistrationIntentService.class.getSimpleName();
    private static final String [] TOPICS ={"global"};

    public RegistrationIntentService(){super(TAG);}

    @Override
    protected void onHandleIntent(Intent intent){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try{
            LogUtil.d(TAG,"onHandleIntent");
            //start register_for gcm
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
            //end token

            sendRegistrationToServer(token);
            //Subscribe to topic channles
            subscribeTopics(token);
            sharedPreferences.edit().putBoolean(SharedPreferenceKey.SENT_TOKEN_TO_SEVER,true).apply();

        }catch(Exception e){
            sharedPreferences.edit().putBoolean(SharedPreferenceKey.SENT_TOKEN_TO_SEVER,false).apply();;
        }
        // Notify UI that registration has completed
        Intent registrationComplete = new Intent(SharedPreferenceKey.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token
     * maintained by your application
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token){
        //add custom implementation , as needed
    }

    private void subscribeTopics(String token) throws IOException{
        LogUtil.d(TAG,"subscribeTopics "+token);
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for(String topic:TOPICS){
            pubSub.subscribe(token,"/topics/"+topic,null);
        }
    }



}
