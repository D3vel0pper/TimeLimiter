package d3vel0pper.com.timelimiter.common;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.MainActivity;

/**
 * Created by D3vel0pper on 2016/06/29.
 */
public class NotificationReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //get values from intent
        String message = intent.getStringExtra("MESSAGE");
        int id = intent.getIntExtra("ID",0);
        //set up Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(message);
        //set vibration sound, and light
        builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        //set the action when notification tapped
        Intent targetctivity = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context,R.layout.activity_main,targetctivity,PendingIntent.FLAG_ONE_SHOT
        );
        builder.setContentIntent(contentIntent);
        //set dismissing action when Notification tapped
        builder.setAutoCancel(true);
        //create Notification
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(id,builder.build());

        //Maybe add here to delete schedule

    }
}
