package d3vel0pper.com.timelimiter.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.listener.ConfirmDialogListener;
import d3vel0pper.com.timelimiter.common.listener.DialogTeller;
import d3vel0pper.com.timelimiter.fragment.CustomDialogFragment;
import d3vel0pper.com.timelimiter.fragment.DatePickerFragment;
//import d3vel0pper.com.timelimiter.fragment.EditFragment;
import d3vel0pper.com.timelimiter.fragment.EditDialogFragment;
import d3vel0pper.com.timelimiter.fragment.TimePickerFragment;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class EditActivity extends DatePickActivity
        implements ConfirmDialogListener,TextWatcher,DatePickerDialog.OnDateSetListener
        ,TimePickerDialog.OnTimeSetListener {
    /**
     * [0] -> id  [1] -> createdAt  [2] -> title  [3] -> startDate  [4] -> endDate
     * [5] -> place  [6] -> description
     */
    public List<String> dataList;
    private Map<String, String> dataMap;
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
        //Use List
        dataList = new ArrayList<String>();
        //Use Map
        dataMap = new HashMap<>();
        int id = getIntent().getIntExtra("id",-1);
        if(id == -1){
            Toast.makeText(this,"cannot find the data",Toast.LENGTH_SHORT).show();
            finish();
        }
        //Use List
        setUpList(id);
        startDateText = (TextView)findViewById(R.id.startText);
        startDateText.setText(dataList.get(3));
        endDateText = (TextView)findViewById(R.id.endText);
        endDateText.setText(dataList.get(4));
        titleText = (EditText)findViewById(R.id.titleText);
        titleText.setText(dataList.get(2));
        titleText.addTextChangedListener(this);
        placeText = (EditText)findViewById(R.id.placeText);
        placeText.setText(dataList.get(5));
        placeText.addTextChangedListener(this);
        descriptionText = (EditText)findViewById(R.id.descriptionText);
        descriptionText.setText(dataList.get(6));
        descriptionText.addTextChangedListener(this);
        //Use Map
        setUpMap(id);
        setUpViews();
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
//                Toast.makeText(this, "ended correctly", Toast.LENGTH_SHORT).show();
//                finish();
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
                allData = titleText.getText() + "\n" + startGuide.getText() + "\n" + startDateText.getText() + "\n"
                        + endGuide.getText() + "\n" + endDateText.getText() + "\n"
                        + placeText.getText() + "\n" + descriptionText.getText();
                //Create Dialog
//                CustomDialogFragment cdf = new CustomDialogFragment();
//                cdf.show(getSupportFragmentManager(),"edit");
                EditDialogFragment edf = new EditDialogFragment();
                edf.show(getSupportFragmentManager(),"edit");
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

    private void setUpMap(int id){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<DBData> results;
        RealmQuery<DBData> query = realm.where(DBData.class);
        results = query.equalTo("id",id).findAll().sort("id", Sort.ASCENDING);
        if(results.get(0) == null){
            Toast.makeText(this, "results show null", Toast.LENGTH_SHORT).show();
            finish();
        }
        dataMap.put("id",String.valueOf(results.get(0).getId()));
        dataMap.put("createdAt",results.get(0).getCreatedAt());
        dataMap.put("title",results.get(0).getTitle());
        dataMap.put("startDate",results.get(0).getStartDate());
        dataMap.put("endDate",results.get(0).getEndDate());
        dataMap.put("place",results.get(0).getPlace());
        dataMap.put("description",results.get(0).getDescription());
    }

    private void setUpViews(){
        //Texts
        startDateText = (TextView)findViewById(R.id.startText);
        startDateText.setText(dataMap.get("startDate"));
        endDateText = (TextView)findViewById(R.id.endText);
        endDateText.setText(dataMap.get("endDate"));
        titleText = (EditText)findViewById(R.id.titleText);
        titleText.setText(dataMap.get("title"));
        titleText.addTextChangedListener(this);
        placeText = (EditText)findViewById(R.id.placeText);
        placeText.setText(dataMap.get("place"));
        placeText.addTextChangedListener(this);
        descriptionText = (EditText)findViewById(R.id.descriptionText);
        descriptionText.setText(dataMap.get("description"));
        descriptionText.addTextChangedListener(this);
        //Buttons
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
        //dialogTeller
        dialogTeller = DialogTeller.getInstance();
        dialogTeller.setListener(this);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        //Use List
        if(this.TAG.equals("startDatePicker")){
            dataList.set(3,String.valueOf(year) + "/"
                    + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth)
                    + " " + dataList.get(3).split(" ")[1]);
            startDateText.setText(dataList.get(3));
        } else if(this.TAG.equals("endDatePicker")){
            dataList.set(4,String.valueOf(year) + "/"
                    + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth)
                    + " " + dataList.get(4).split(" ")[1]);
            endDateText.setText(dataList.get(4));
        }
        //Use Map
        if(this.TAG.equals("startDatePicker")){
            dataMap.put("startDate", String.valueOf(year) + "/"
            + String.valueOf(monthOfYear + 1) + "/"
            + String.valueOf(dayOfMonth) + " "
            + dataMap.get("startDate").split(" ")[1]
            );
            startDateText.setText(dataMap.get("startDate"));
        } else if(this.TAG.equals("endDatePicker")){
            dataMap.put("endDate", String.valueOf(year) + "/"
            + String.valueOf(monthOfYear + 1) + "/"
            + String.valueOf(dayOfMonth) + " "
            + dataMap.get("endDate").split(" ")[1]
            );
            endDateText.setText(dataMap.get("endDate"));
        }
        //Put All Data
        putAllData();
    }

    @Override
    public void onTimeSet(TimePicker timePicker,int hour, int min){
        if(TAG.equals("startTimePicker")){
            if(hour < 10){
                if(min < 10){
                    startTimeData = "0" + String.valueOf(hour) + ":" + "0" + String.valueOf(min);
                }else {
                    startTimeData = "0" + String.valueOf(hour) + ":" + String.valueOf(min);
                }
            }else {
                if (min < 10) {
                    startTimeData = String.valueOf(hour) + ":" + "0" + String.valueOf(min);
                } else {
                    startTimeData = String.valueOf(hour) + ":" + String.valueOf(min);
                }
            }
            //Use List
            startDateText.setText(dataList.get(3).split(" ")[0] + " " + startTimeData);
            //Use Map
            startDateText.setText(dataMap.get("startDate").split(" ")[0] + " " + startTimeData);
        } else if(TAG.equals("endTimePicker")){
            if(hour < 10){
                if(min < 10){
                    endTimeData = "0" + String.valueOf(hour) + ":" + "0" + String.valueOf(min);
                }else {
                    endTimeData = "0" + String.valueOf(hour) + ":" + String.valueOf(min);
                }
            }else {
                if (min < 10) {
                    endTimeData = String.valueOf(hour) + ":" + "0" + String.valueOf(min);
                } else {
                    endTimeData = String.valueOf(hour) + ":" + String.valueOf(min);
                }
            }
            //Use List
            endDateText.setText(dataList.get(4).split(" ")[0] + " " + endTimeData);
            //Use Map
            endDateText.setText(dataMap.get("endDate").split(" ")[0] + " " + endTimeData);
        }
        //Put All Data
        putAllData();
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

    public Map getTimeMap(){
        String[] data;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPAN);
        data = format.format(date).split(" ");
        Map<String, String> map = new HashMap<>();
        map.put("date", data[0]);
        map.put("time", data[1]);
        return map;
    }

    @Override
    public void onConfirmDialog(String TAG){
        this.TAG = TAG;
    }

    @Override
    public void onTextChanged(CharSequence sequence,int start,int count,int after){
    }

    @Override
    public void beforeTextChanged(CharSequence sequence,int start,int count,int after){
    }

    @Override
    public void afterTextChanged(Editable editable){
        putAllData();
    }

    private void putAllData(){
        //Use List
        dataList.set(2,titleText.getText().toString());
        dataList.set(3,startDateText.getText().toString());
        dataList.set(4,endDateText.getText().toString());
        dataList.set(5,placeText.getText().toString());
        dataList.set(6,descriptionText.getText().toString());
        allData = titleText.getText() + "\n" + startGuide.getText() + "\n" + startDateText.getText() + "\n"
                + endGuide.getText() + "\n" + endDateText.getText() + "\n"
                + placeText.getText() + "\n" + descriptionText.getText();
        //Use Map
        dataMap.put("title",titleText.getText().toString());
        dataMap.put("startDate",startDateText.getText().toString());
        dataMap.put("endDate",endDateText.getText().toString());
        dataMap.put("place",placeText.getText().toString());
        dataMap.put("description",descriptionText.getText().toString());
        allData = titleText.getText() + "\n" + startGuide.getText() + "\n" + startDateText.getText() + "\n"
                + endGuide.getText() + "\n" + endDateText.getText() + "\n"
                + placeText.getText() + "\n" + descriptionText.getText();
    }

    public List<String> getDataList(){
        return this.dataList;
    }

    public Map<String, String> getDataMap(){
        return dataMap;
    }

    }
