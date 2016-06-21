package d3vel0pper.com.timelimiter.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.DatePickActivity;
import d3vel0pper.com.timelimiter.activity.MainActivity;

/**
 * Created by HotFlush on 2016/06/16.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public DatePickerFragment(){
    }

    private DatePickerDialog dialog;

//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        super.onCreateView(inflater,container,savedInstanceState);
//
//        View view = inflater.inflate(R.layout.fragment_date_picker,container,false);
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        MainActivity parent = (MainActivity) getActivity();
//
//        dialog = new DatePickerDialog(getActivity(),parent,year,month,day);
//
//        return view;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog
                = new DatePickerDialog(getActivity(),(DatePickActivity)getActivity(),year,month,day);
        return dialog;
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker,int year, int monthOfYear, int dayOfMonth){
    }

}
