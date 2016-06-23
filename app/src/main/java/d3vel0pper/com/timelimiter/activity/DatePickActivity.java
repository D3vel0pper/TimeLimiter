package d3vel0pper.com.timelimiter.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.fragment.CustomDialogFragment;
import d3vel0pper.com.timelimiter.fragment.DatePickerFragment;
import d3vel0pper.com.timelimiter.fragment.TimePickerFragment;

/**
 * Created by D3vel0pper on 2016/06/18.
 */
public class DatePickActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener
        ,TimePickerDialog.OnTimeSetListener,View.OnClickListener {

    TextView testText;
    String dateData,timeData,bothData;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);

        timeData = "";
        dateData = "";

        testText = (TextView)findViewById(R.id.dateText);

        Button testBtn = (Button)findViewById(R.id.testBtn);
        testBtn.setOnClickListener(this);
        Button testBtn2 = (Button)findViewById(R.id.testBtn2);
        testBtn2.setOnClickListener(this);
        Button endBtn = (Button)findViewById(R.id.endBtn);
        endBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.testBtn:
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.show(getSupportFragmentManager(),"datePicker");
                break;
            case R.id.testBtn2:
                TimePickerFragment tpf = new TimePickerFragment();
                tpf.show(getSupportFragmentManager(),"timePicker");
                break;
            case R.id.endBtn:
                CustomDialogFragment cdf = new CustomDialogFragment();
                cdf.show(getSupportFragmentManager(),"register");
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth){
        dateData = String.valueOf(year) + "/"
                    + String.valueOf(monthOfYear) + "/" + String.valueOf(dayOfMonth);
        bothData = dateData + " " + timeData;
        testText.setText(bothData);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute){
        if(minute < 10){
            timeData = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
        }else {
            timeData = String.valueOf(hour) + ":" + String.valueOf(minute);
        }
        bothData = dateData + " " + timeData;
        testText.setText(bothData);
    }

    public String getBothData(){
        return this.bothData;
    }

}
