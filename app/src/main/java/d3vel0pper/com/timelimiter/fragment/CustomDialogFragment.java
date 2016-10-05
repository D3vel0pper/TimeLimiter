package d3vel0pper.com.timelimiter.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.DatePickActivity;
import d3vel0pper.com.timelimiter.activity.EditActivity;
import d3vel0pper.com.timelimiter.activity.MainActivity;
import d3vel0pper.com.timelimiter.activity.SettingActivity;
import d3vel0pper.com.timelimiter.common.Calculator;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.MyCalendar;
import d3vel0pper.com.timelimiter.common.Notificationer;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by D3vel0pper on 2016/06/21.
 */
public class CustomDialogFragment extends DialogFragment {
    public CustomDialogFragment(){}
    static private String dataString;
    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //set Attributes
        Dialog dialog = getDialog();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float dialogWidth = 300 * metrics.scaledDensity;
        layoutParams.width = (int)dialogWidth;
        dialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Activity parent = getActivity();
        View view;
        if(getTag().equals("register")){
            view = registerCase(inflater,container,savedInstanceState,(DatePickActivity)parent);
        } else if(getTag().equals("edit")){
            view = editCase(inflater,container,savedInstanceState,(EditActivity)parent);
        } else if(getTag().equals("setting0") || getTag().equals("setting1") || getTag().equals("setting2")){
           view = settingCase(inflater,container,savedInstanceState,(SettingActivity) parent);
        } else {
            view = defaultCase(inflater,container,savedInstanceState,(MainActivity)parent);
        }
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstantState){
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        return dialog;
    }

    private View defaultCase(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState,MainActivity parent){
        View view = inflater.inflate(R.layout.fragment_list_dialog,container,false);
        String[] listData = {"edit","delete","complete"};
        final int parentItemPosition = parent.itemPosition;
        final MainActivity passParent = parent;
        ListView listView = (ListView)view.findViewById(R.id.listView);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(parent,R.layout.default_list_item_layout,listData);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Realm realm;
                RealmQuery<DBData> query;
                switch(position){
                    case 0:
                        realm = Realm.getDefaultInstance();
                        RealmResults<DBData> res;
                        query = realm.where(DBData.class);
                        res = query.findAll();
                        Intent intent = new Intent(getActivity().getApplicationContext(),EditActivity.class);
                        intent.putExtra("id",res.get(parentItemPosition).getId());
                        realm.close();
                        startActivity(intent);
                        break;
                    case 1:
                        realm = Realm.getDefaultInstance();
                        final RealmResults<DBData> results;
                        //Search that match to item's Id
                        results = realm.where(DBData.class)
                                .equalTo("id",(int)passParent.listView.getItemIdAtPosition(parentItemPosition))
                                .findAll();
                        Notificationer.cancelLocalNotification(getActivity(),results.get(0).getId());
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                results.get(0).deleteFromRealm();
                            }
                        });
                        passParent.realmAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        break;
                }
                dismiss();
            }
        });
        return view;
    }

    private View registerCase(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState, DatePickActivity parent){
        View view = inflater.inflate(R.layout.fragment_register_dialog,container,false);
        TextView confirmText = (TextView)view.findViewById(R.id.confirmText);
        if(dataString == null){
            dataString = "";
        }
        dataString = parent.getAllData();
        String confirmString = "Do U want to register the date below?\n" + dataString;
        confirmText.setText(confirmString);
        Button confirmBtn = (Button)view.findViewById(R.id.confirmBtn);
        confirmBtn.setText("Yes !");
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerData();
            }
        });
        return view;
    }

    private View editCase(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState, DatePickActivity parent){
        View view = inflater.inflate(R.layout.fragment_register_dialog,container,false);
        TextView confirmText = (TextView)view.findViewById(R.id.confirmText);
        if(dataString == null){
            dataString = "";
        }
        dataString = parent.getAllData();
        String confirmString = "Do U want to register the date below?\n" + dataString;
        confirmText.setText(confirmString);
        confirmText.setGravity(Gravity.CENTER);
        Button confirmBtn = (Button)view.findViewById(R.id.confirmBtn);
        confirmBtn.setText("Yes !");
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerData();
            }
        });
        return view;
    }

    private boolean isRegistable(SharedPreferences preferences,Calculator calcedCalc,int dayTotal,int weekTotal,int monthTotal){

        if(((dayTotal + calcedCalc.getAllGapInHour())
                < Integer.parseInt(preferences.getString("maxHourPerDay",String.valueOf(Integer.MAX_VALUE))))
                && ((weekTotal + calcedCalc.getAllGapInHour())
                < Integer.parseInt(preferences.getString("maxHourPerWeek",String.valueOf(Integer.MAX_VALUE))))
                && ((monthTotal + calcedCalc.getAllGapInHour())
                < Integer.parseInt(preferences.getString("maxHourPerMonth",String.valueOf(Integer.MAX_VALUE))))){
            return true;
        }
        return false;
    }

    private View settingCase(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState, final SettingActivity parent){
        View view = inflater.inflate(R.layout.change_setting_dialog,container,false);
        final EditText editText = (EditText)view.findViewById(R.id.settingText);
        Button okBtn = (Button)view.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = parent.getSharedPreferences("ConfigData", Context.MODE_PRIVATE);
                switch (getTag()){
                    case "setting0":
                        if(!editText.getText().toString().isEmpty()) {
                            preferences.edit().
                                    putString("maxHourPerDay", editText.getText().toString()).
                                    apply();
                            parent.adapter.notifyDataSetChanged();
                            Toast.makeText(parent, "changed settings", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getActivity(), "please input number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "setting1":
                        if(!editText.getText().toString().isEmpty()) {
                            preferences.edit().
                                    putString("maxHourPerWeek", editText.getText().toString()).
                                    apply();
                            parent.adapter.notifyDataSetChanged();
                            Toast.makeText(parent, "changed settings", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getActivity(), "please input number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "setting2":
                        if(!editText.getText().toString().isEmpty()) {
                            preferences.edit().
                                    putString("maxHourPerMonth", editText.getText().toString()).
                                    apply();
                            parent.adapter.notifyDataSetChanged();
                            Toast.makeText(parent, "changed settings", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getActivity(), "please input number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView(){
        if(realm != null) {
            realm.close();
        }
        super.onDestroyView();
    }

    private void registerData(){
        RegisterInformer registerInformer = RegisterInformer.getInstance();
        registerInformer.setData(dataString);

        if(dataString != null){
            /**
             * data[]
             *      [0] -> title
             *      [1] -> startGuide -> this is not for need X
             *      [2] -> startDate (yyyy/MM/dd hh:mm)
             *      [3] -> endGuide -> this is not for need X
             *      [4] -> endDate (yyyy/MM/dd hh:mm)
             *      [5] -> place
             *      [6] -> description
             */
            String[] data = dataString.split("\n");
            //put to Map
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("title",data[0]);
            dataMap.put("startGuide",data[1]);
            dataMap.put("startDate",data[2]);
            dataMap.put("endGuide",data[3]);
            dataMap.put("endDate",data[4]);
            dataMap.put("place",data[5]);
            dataMap.put("description",data[6]);

            SharedPreferences preferences
                    = getActivity().getSharedPreferences("ConfigData",Context.MODE_PRIVATE);
            realm = Realm.getDefaultInstance();

            //for check is empty
            RealmResults<DBData> results;
            RealmQuery<DBData> query = realm.where(DBData.class);
            results = query.findAll().sort("id", Sort.ASCENDING);

            //start transaction and register
            realm.beginTransaction();
            DBData dbData = realm.createObject(DBData.class);
            int putId = 0;
            if(results.isEmpty()){
                dbData.setId(0);
            } else {
                putId = results.last().getId() + 1;
                dbData.setId(putId);
            }

            //set data
            dbData.setTitle(dataMap.get("title"));
            dbData.setStartDate(dataMap.get("startDate"));
            dbData.setStartDay(dataMap.get("startDate").split(" ")[0]);
            dbData.setEndDate(dataMap.get("endDate"));
            dbData.setEndDay(dataMap.get("endDate").split(" ")[0]);
            dbData.setMonth(dataMap.get("startDate").split(" ")[0].split("/")[1]);
            dbData.setPlace(dataMap.get("place"));
            dbData.setDescription(dataMap.get("description"));

            //get Current Date for CreatedAt
            DatePickActivity parent = (DatePickActivity)getActivity();
            //Use Map
            Map<String, String> timeNowMap = parent.getTimeMap();
            dbData.setCreatedAt(timeNowMap.get("date") + " " + timeNowMap.get("time"));

            //Calculating and Registering sum of scheduled plans
            Calculator dayCalc = new Calculator();
            Calculator calc = new Calculator();
            dayCalc.calcGap(dbData.getStartDate(),dbData.getEndDate());

            int dayTotal = 0;
            results = query.equalTo("startDay",dataMap.get("startDate").split(" ")[0]).notEqualTo("id",putId).findAll();
            for(int i = 0;i <  results.size();i++){
                if(results.get(i).getId() != putId) {
                    calc.calcGap(results.get(i).getStartDate()
                            , results.get(i).getEndDate());
                    dayTotal += calc.getAllGapInHour();
                    calc.reset();
                }
            }

            MyCalendar myCalendar = new MyCalendar();
            myCalendar.setDateFromFormat(dataMap.get("startDate").split(" ")[0]);
            List<String> daysInWeek = myCalendar.getDaysInWeek();
            results = query.equalTo("startDay",daysInWeek.get(MyCalendar.MONDAY))
                    .or().equalTo("startDay",daysInWeek.get(MyCalendar.TUESDAY))
                    .or().equalTo("startDay",daysInWeek.get(MyCalendar.WEDNESDAY))
                    .or().equalTo("startDay",daysInWeek.get(MyCalendar.THURSDAY))
                    .or().equalTo("startDay",daysInWeek.get(MyCalendar.FRIDAY))
                    .or().equalTo("startDay",daysInWeek.get(MyCalendar.SATURDAY))
                    .or().equalTo("startDay",daysInWeek.get(MyCalendar.SUNDAY))
                    .findAll();

            int weekTotal = 0;
            for(int i = 0;i < results.size();i++){
                if(results.get(i).getId() != putId) {
                    calc.calcGap(results.get(i).getStartDate()
                            , results.get(i).getEndDate());
                    weekTotal += calc.getAllGapInHour();
                    calc.reset();
                }
            }

            int monthTotal = 0;
            results = query.equalTo("month",dataMap.get("startDate").split(" ")[0].split("/")[1]).findAll();
            for(int i = 0;i < results.size();i++){
                if(results.get(i).getId() != putId) {
                    calc.calcGap(results.get(i).getStartDate()
                            , results.get(i).getEndDate());
                    monthTotal += calc.getAllGapInHour();
                    calc.reset();
                }
            }

            if(isRegistable(preferences,dayCalc,dayTotal,weekTotal,monthTotal)) {
                realm.commitTransaction();
                //register Notification
//                        if (preferences.getBoolean("notification", true)) {
//                            Notificationer.setLocalNotification(
//                                    getActivity(), dbData.getTitle(), dbData.getId(), dbData.getStartDate()
//                            );
//                        }
                if (Boolean.valueOf(preferences.getString("notification", "true"))) {
                    Notificationer.setLocalNotification(
                            getActivity(), dbData.getTitle(), dbData.getId(), dbData.getStartDate()
                    );
                }

//                        preferences.edit().putInt("nowRegistered", calc.getAllGapInHour()).apply();
                preferences.edit().putString("nowRegistered", String.valueOf(dayCalc.getAllGapInHour())).apply();
                registerInformer.informToActivity();
                getActivity().finish();
            }else{
                realm.cancelTransaction();
                Toast.makeText(parent, "you over the limit of scheduling", Toast.LENGTH_SHORT).show();
                dismiss();
            }

        }
    }

}
