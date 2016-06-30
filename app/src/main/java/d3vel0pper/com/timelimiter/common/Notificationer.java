package d3vel0pper.com.timelimiter.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by D3vel0pper on 2016/06/29.
 */
public class Notificationer {
    /**
     *
     * @param context
     * @param message: message for showing
     * @param requestCode: position
     * @param interval: time to show notification
     */
    public static void setLocalNotification(Context context, String message, int requestCode, int interval){
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("MESSAGE", message);
        PendingIntent sender = PendingIntent.getBroadcast(context,  requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, interval);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }

    /**
     *
     * @param context
     * @param requestCode: position
     */
    public static void cancelLocalNotification(Context context, int requestCode){
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
