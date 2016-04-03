package umbrella.tokyo.jp.umbrella.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import umbrella.tokyo.jp.umbrella.MainActivity;
import umbrella.tokyo.jp.umbrella.R;
import umbrella.tokyo.jp.umbrella.util.LogUtil;

/**
 * Created by mori on 4/3/16.
 */
public class MyGcmListenerService extends GcmListenerService{

    private static final String TAG = MyGcmListenerService.class.getSimpleName();

    /**
     * Called when message is received.
     */
    //start receive_message
    @Override
    public void onMessageReceived(String from,Bundle data){
        String message = data.getString("message");
        LogUtil.d(TAG,"From:"+from);
        LogUtil.d(TAG,"Message:"+message);
        if(from.startsWith("/topics/")){
            //message received from some topic.
        }else{
            // normal downstream message.
        }
        sendNotification(message);

    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_manage)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
