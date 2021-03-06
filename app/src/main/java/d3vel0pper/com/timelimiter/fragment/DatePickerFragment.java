package d3vel0pper.com.timelimiter.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.DatePickActivity;
import d3vel0pper.com.timelimiter.activity.MainActivity;
import d3vel0pper.com.timelimiter.common.listener.DialogTeller;

/**
 * Created by D3velopper on 2016/06/16.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public DatePickerFragment(){
    }

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
        DialogTeller dialogTeller = DialogTeller.getInstance();
        dialogTeller.InformDialog(getTag());
        int year;
        int month;
        int day;
        final Calendar c = Calendar.getInstance();
        if(getTag().equals("endDatePicker")){
            DatePickActivity parent = (DatePickActivity) getActivity();
            year = Integer.parseInt(parent.startDateData.split("/")[0]);
            month = Integer.parseInt(parent.startDateData.split("/")[1]);
            day = Integer.parseInt(parent.startDateData.split("/")[2]);
        } else {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        return new DatePickerDialog(getActivity(),(DatePickActivity)getActivity(),year,month,day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker,int year, int monthOfYear, int dayOfMonth){
    }

}
