package d3vel0pper.com.timelimiter.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.fragment.DatePickerFragment;
import d3vel0pper.com.timelimiter.fragment.TimePickerFragment;

/**
 * Created by D3vel0pper on 2016/06/18.
 */
public class DatePickActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener
        ,TimePickerDialog.OnTimeSetListener,View.OnClickListener {

    TextView testText;
    String dateData = "";
    Boolean isDataSet = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);
        testText = (TextView)findViewById(R.id.dateText);

        Button testBtn = (Button)findViewById(R.id.testBtn);
        testBtn.setOnClickListener(this);
        Button testBtn2 = (Button)findViewById(R.id.testBtn2);
        testBtn2.setOnClickListener(this);
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
                finish();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth){
        if (isDataSet) {
            dateData = String.valueOf(year) + "/"
                    + String.valueOf(monthOfYear) + "/" + String.valueOf(dayOfMonth)
                    + " " + dateData;
            isDataSet = true;
        } else {
            dateData = String.valueOf(year) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(dayOfMonth);
            isDataSet = true;
        }
        testText.setText(dateData);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute){
        if (isDataSet){
            dateData = dateData +  " " + String.valueOf(hour) + ":" + String.valueOf(minute);
            isDataSet = true;
        } else {
            dateData = String.valueOf(hour) + ":" + String.valueOf(minute);
            isDataSet = true;
        }
        testText.setText(dateData);
    }

}
