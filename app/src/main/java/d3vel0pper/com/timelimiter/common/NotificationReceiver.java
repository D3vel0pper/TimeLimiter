package d3vel0pper.com.timelimiter.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by HotFlush on 2016/06/29.
 */
public class NotificationReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra("MESSAGE");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
