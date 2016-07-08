package d3vel0pper.com.timelimiter.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.listener.ConfirmDialogListener;
import d3vel0pper.com.timelimiter.common.listener.DialogTeller;
import d3vel0pper.com.timelimiter.fragment.CustomDialogFragment;
import d3vel0pper.com.timelimiter.fragment.DatePickerFragment;
import d3vel0pper.com.timelimiter.fragment.TimePickerFragment;

/**
 * Created by D3vel0pper on 2016/06/18.
 */
public class DatePickActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener
        ,TimePickerDialog.OnTimeSetListener,View.OnClickListener,TextWatcher,ConfirmDialogListener {

    public TextView allDataText,startText,endText,startGuide,endGuide;
    public String startDateData,startTimeData,endDateData
            ,endTimeData,bothStartData,bothEndData,allData,TAG;
    public EditText titleText,placeText,descriptionText;
    private DialogTeller dialogTeller;

    @Override
    public void onCreate(Bundle savedInstanceState){
        String[] data = getTimeNow();

        startTimeData = data[1];
        startDateData = data[0];
        endTimeData = data[1];
        endDateData = data[0];

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_date_pick);

        //allDataText = (TextView)findViewById(R.id.allDataText);
        startText = (TextView)findViewById(R.id.startText);
        startText.setText(data[0] + " " + data[1]);
        endText = (TextView)findViewById(R.id.endText);
        endText.setText(data[0] + " " + data[1]);
        startGuide = (TextView)findViewById(R.id.startGuide);
        endGuide = (TextView)findViewById(R.id.endGuide);

        titleText = (EditText)findViewById(R.id.titleText);
        titleText.addTextChangedListener(this);
        placeText = (EditText)findViewById(R.id.placeText);
        placeText.addTextChangedListener(this);
        descriptionText = (EditText)findViewById(R.id.descriptionText);
        descriptionText.addTextChangedListener(this);
        descriptionText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER){
                    // Process when Enter Key Pressed
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        Button startDateBtn = (Button)findViewById(R.id.startDateBtn);
        startDateBtn.setOnClickListener(this);
        Button startTimeBtn = (Button)findViewById(R.id.startTimeBtn);
        startTimeBtn.setOnClickListener(this);
        Button endDateBtn = (Button)findViewById(R.id.endDateBtn);
        endDateBtn.setOnClickListener(this);
        Button endTimeBtn = (Button)findViewById(R.id.endTimeBtn);
        endTimeBtn.setOnClickListener(this);
        Button endBtn = (Button)findViewById(R.id.endBtn);
        endBtn.setOnClickListener(this);

        //Registering ConfirmDialogListener
        dialogTeller = DialogTeller.getInstance();
        dialogTeller.setListener(this);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.startDateBtn:
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.show(getSupportFragmentManager(),"startDatePicker");
                break;
            case R.id.startTimeBtn:
                TimePickerFragment tpf = new TimePickerFragment();
                tpf.show(getSupportFragmentManager(),"startTimePicker");
                break;
            case R.id.endDateBtn:
                DatePickerFragment pickerEndDate = new DatePickerFragment();
                pickerEndDate.show(getSupportFragmentManager(),"endDatePicker");
                break;
            case R.id.endTimeBtn:
                TimePickerFragment pickerEndTime = new TimePickerFragment();
                pickerEndTime.show(getSupportFragmentManager(),"endTimePicker");
                break;
            case R.id.endBtn:
                //Null Check
                String temp;
                temp = titleText.getText().toString();
                if(temp.length() == 0){
                    titleText.setText("-");
                }
                temp = placeText.getText().toString();
                if(temp.length() == 0){
                    placeText.setText("-");
                }
                temp = descriptionText.getText().toString();
                if(temp.length() == 0){
                    descriptionText.setText("-");
                }
                //Set All Data
                allData = titleText.getText() + "\n" + startGuide.getText() + "\n" + startText.getText() + "\n"
                        + endGuide.getText() + "\n" + endText.getText() + "\n"
                        + placeText.getText() + "\n" + descriptionText.getText();
                //Create Dialog
                CustomDialogFragment cdf = new CustomDialogFragment();
                cdf.show(getSupportFragmentManager(),"register");
                break;
        }
    }

    @Override
    public void onTextChanged(CharSequence sequence,int start,int count,int after){
    }

    @Override
    public void beforeTextChanged(CharSequence sequence,int start,int count,int after){
    }

    @Override
    public void afterTextChanged(Editable editable){
        allData = titleText.getText() + "\n" + startGuide.getText() + "\n" + startText.getText() + "\n"
                + endGuide.getText() + "\n" + endText.getText() + "\n"
                + placeText.getText() + "\n" + descriptionText.getText();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth){

        if(TAG.equals("startDatePicker")){
            startDateData = String.valueOf(year) + "/"
                    + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth);
            bothStartData = startDateData + " " + startTimeData;
            startText.setText(bothStartData);

        } else if(TAG.equals("endDatePicker")){
            endDateData = String.valueOf(year) + "/"
                    + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth);
            bothEndData = endDateData + " " + endTimeData;
            endText.setText(bothEndData);
        }
        //Put All Data
        this.allData = titleText.getText() + "\n" + startGuide.getText() + "\n" + startText.getText() + "\n"
                + endGuide.getText() + "\n" + endText.getText() + "\n"
                + placeText.getText() + "\n" + descriptionText.getText();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute){
        if(TAG.equals("startTimePicker")){
            if(hour < 10){
                if(minute < 10){
                    startTimeData = "0" + String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                }else {
                    startTimeData = "0" + String.valueOf(hour) + ":" + String.valueOf(minute);
                }
            }else {
                if (minute < 10) {
                    startTimeData = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                } else {
                    startTimeData = String.valueOf(hour) + ":" + String.valueOf(minute);
                }
            }
            bothStartData = startDateData + " " + startTimeData;
            startText.setText(bothStartData);
        } else if(TAG.equals("endTimePicker")){
            if(hour < 10){
                if(minute < 10){
                    endTimeData = "0" + String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                }else {
                    endTimeData = "0" + String.valueOf(hour) + ":" + String.valueOf(minute);
                }
            }else {
                if (minute < 10) {
                    endTimeData = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                } else {
                    endTimeData = String.valueOf(hour) + ":" + String.valueOf(minute);
                }
            }
            bothEndData = endDateData + " " + endTimeData;
            endText.setText(bothEndData);
        }
        //Put All Data
        allData = titleText.getText() + "\n" + startGuide.getText() + "\n" + startText.getText() + "\n"
                + endGuide.getText() + "\n" + endText.getText() + "\n"
                + placeText.getText() + "\n" + descriptionText.getText();
    }

    public String getAllData(){
        return this.allData;
    }

    public String[] getTimeNow(){
        /**
         *       [0] -> date (yyyy/MM/dd)
         *       [1] -> time (hh:mm)
         */
        //Set default time and date
        String[] data;
        String temp;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPAN);
        temp = format.format(date);
        data = temp.split(" ");
        return data;
    }

    public void onConfirmDialog(String TAG){
        this.TAG = TAG;
    }

}
