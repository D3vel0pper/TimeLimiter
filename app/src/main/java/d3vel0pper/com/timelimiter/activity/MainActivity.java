package d3vel0pper.com.timelimiter.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import d3vel0pper.com.timelimiter.CallBackToFragment;
import d3vel0pper.com.timelimiter.fragment.DatePickerFragment;
import d3vel0pper.com.timelimiter.fragment.NavigationDrawerFragment;
import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.MyCalendar;
import d3vel0pper.com.timelimiter.fragment.TimePickerFragment;


public class MainActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    public String dateData;
    public TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testText = (TextView)findViewById(R.id.dateText);

        Button testBtn = (Button)findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(getSupportFragmentManager(),"datePicker");
            }
        });
        Button testBtn2 = (Button)findViewById(R.id.testBtn2);
        testBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TimePickerFragment dialog = new TimePickerFragment();
                dialog.show(getSupportFragmentManager(),"timePicker");
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    @Override
    public void onDateSet(DatePicker datePicker,int year, int monthOfYear,int dayOfMonth){
        dateData = String.valueOf(year) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(dayOfMonth);
        testText.setText(dateData);
    }

    @Override
    public void onTimeSet(TimePicker timePicker,int hour, int minute){
        dateData =dateData +  " " + String.valueOf(hour) + ":" + String.valueOf(minute);
        testText.setText(dateData);
    }

}
