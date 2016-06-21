package d3vel0pper.com.timelimiter.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

import d3vel0pper.com.timelimiter.activity.MainActivity;

/**
 * Created by HotFlush on 2016/06/21.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
//        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
//        Integer minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),(MainActivity)getActivity(),hour,minute,true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker,int hourOfDay,int minute){
    }
}
