package d3vel0pper.com.timelimiter.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by D3vel0pper on 2016/06/29.
 */
public class Notificationer {
    /**
     *
     * @param context
     * @param message: message for showing
     * @param requestCode: ID in DB
     * @param targetDate: get From registered data
     */
    public static void setLocalNotification(Context context, String message, int requestCode, String targetDate){
        //stand intent
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("MESSAGE", message);
        intent.putExtra("ID",requestCode);
        PendingIntent sender = PendingIntent.getBroadcast(context,  requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //date has time shaped by mills
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setLenient(false);
            sdf.applyPattern("yyyy/MM/dd hh:mm");
            date = sdf.parse(targetDate);
        } catch (java.text.ParseException e){
            Log.e("PARSE ERROR","Date has not formated or correct");
        }
        //set alarm
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), sender);
    }

    /**
     *
     * @param context
     * @param requestCode: position
     */
    public static void cancelLocalNotification(Context context, int requestCode){
        //get intent which registered relate on requestCode
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //cancel alarm
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
