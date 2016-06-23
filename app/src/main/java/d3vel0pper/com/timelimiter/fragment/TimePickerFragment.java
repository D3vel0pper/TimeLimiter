package d3vel0pper.com.timelimiter.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

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
        final Calendar calendar = Calendar.getInstance();
//        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
//        Integer minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog
                = new TimePickerDialog(getActivity(),(DatePickActivity)getActivity(),hour,minute,true);
        return dialog;
    }

    @Override
    public void onTimeSet(TimePicker timePicker,int hourOfDay,int minute){
    }
}
