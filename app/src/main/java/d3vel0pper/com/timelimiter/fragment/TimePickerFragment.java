package d3vel0pper.com.timelimiter.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import d3vel0pper.com.timelimiter.activity.DatePickActivity;
import d3vel0pper.com.timelimiter.activity.MainActivity;
import d3vel0pper.com.timelimiter.common.listener.DialogTeller;

/**
 * Created by D3vel0pper on 2016/06/21.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        DialogTeller dialogTeller = DialogTeller.getInstance();
        dialogTeller.InformDialog(getTag());
        int hour;
        int minute;
        if(getTag().equals("endTimePicker")){
            DatePickActivity parent = (DatePickActivity) getActivity();
            hour = Integer.parseInt(parent.endTimeData.split(":")[0]);
            minute = Integer.parseInt(parent.endTimeData.split(":")[1]);
        }else {
            final Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
        }
        return new TimePickerDialog(getActivity(),(DatePickActivity)getActivity(),hour,minute,true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker,int hourOfDay,int minute){
    }
}
