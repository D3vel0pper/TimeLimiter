package d3vel0pper.com.timelimiter.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.listener.DialogTeller;
import d3vel0pper.com.timelimiter.fragment.DatePickerFragment;
import d3vel0pper.com.timelimiter.fragment.EditFragment;
import d3vel0pper.com.timelimiter.fragment.TimePickerFragment;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class EditActivity extends DatePickActivity {
    /**
     * [0] -> id  [1] -> createdAt  [2] -> title  [3] -> startDate  [4] -> endDate
     * [5] -> place  [6] -> description
     */
    private List<String> dataList;
    private TextView startDateText,endDateText;
    private EditText titleText,placeText,descriptionText;
    private Button startDateBtn,startTimeBtn,endDateBtn,endTimeBtn,endBtn;
    private String TAG;
    private DialogTeller dialogTeller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);
//        setContentView(R.layout.activity_edit);
//        getSupportFragmentManager().beginTransaction().add(R.id.container,EditFragment.getInstance(),"EditFragment").commit();
        //if id == -1 -> don't search from realm and set texts
        dataList = new ArrayList<String>();
        int id = getIntent().getIntExtra("id",-1);
        if(id == -1){
            Toast.makeText(this,"cannot find the data",Toast.LENGTH_SHORT).show();
            finish();
        }
        setUpList(id);
        startDateText = (TextView)findViewById(R.id.startText);
        startDateText.setText(dataList.get(3));
        endDateText = (TextView)findViewById(R.id.endText);
        endDateText.setText(dataList.get(4));
        titleText = (EditText)findViewById(R.id.titleText);
        titleText.setText(dataList.get(2));
        placeText = (EditText)findViewById(R.id.placeText);
        placeText.setText(dataList.get(5));
        descriptionText = (EditText)findViewById(R.id.descriptionText);
        descriptionText.setText(dataList.get(6));

        startDateBtn = (Button)findViewById(R.id.startDateBtn);
        startDateBtn.setOnClickListener(this);
        startTimeBtn = (Button)findViewById(R.id.startTimeBtn);
        startTimeBtn.setOnClickListener(this);
        endDateBtn = (Button)findViewById(R.id.endDateBtn);
        endDateBtn.setOnClickListener(this);
        endTimeBtn = (Button)findViewById(R.id.endTimeBtn);
        endTimeBtn.setOnClickListener(this);
        endBtn = (Button)findViewById(R.id.endBtn);
        endBtn.setOnClickListener(this);
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
                Toast.makeText(this, "ended correctly", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    /**
     * [0] -> id  [1] -> createdAt  [2] -> title  [3] -> startDate  [4] -> endDate
     * [5] -> place  [6] -> description
     */
    private void setUpList(int id){

        Realm realm = Realm.getDefaultInstance();
        RealmResults<DBData> results;
        RealmQuery<DBData> query = realm.where(DBData.class);
        results = query.equalTo("id",id).findAll().sort("id", Sort.ASCENDING);
        if(results.get(0) == null){
            Toast.makeText(this, "results show null", Toast.LENGTH_SHORT).show();
            finish();
        }
        dataList.add(String.valueOf(results.get(0).getId()));
        dataList.add(results.get(0).getCreatedAt());
        dataList.add(results.get(0).getTitle());
        dataList.add(results.get(0).getStartDate());
        dataList.add(results.get(0).getEndDate());
        dataList.add(results.get(0).getPlace());
        dataList.add(results.get(0).getDescription());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
    }

    }
