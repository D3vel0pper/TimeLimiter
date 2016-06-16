package d3vel0pper.com.timelimiter.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import d3vel0pper.com.timelimiter.activity.MainActivity;

/**
 * Created by HotFlush on 2016/06/16.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        MainActivity parent = (MainActivity) getActivity();

        return new DatePickerDialog(getActivity(),parent,year,month,day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker,int year, int monthOfYear, int dayOfMonth){
    }

}
