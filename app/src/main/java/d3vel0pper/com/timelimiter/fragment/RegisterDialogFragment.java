package d3vel0pper.com.timelimiter.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.DatePickActivity;
import d3vel0pper.com.timelimiter.activity.EditActivity;
import d3vel0pper.com.timelimiter.common.Calculator;
import d3vel0pper.com.timelimiter.common.ConstantValues;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.MyCalendar;
import d3vel0pper.com.timelimiter.common.Notificationer;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by D3vel0pper on 2016/12/21.
 */

public class RegisterDialogFragment extends CustomDialogFragment {

    @Override
    public void onCreate(Bundle savedInstantState){
        super.onCreate(savedInstantState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstantState){
        super.onActivityCreated(savedInstantState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getTag().equals("register")){
            DatePickActivity parent = (DatePickActivity)getActivity();
            dataString = parent.getAllData();
            isRepeatable = parent.checkBox.isChecked();

        } else {
            EditActivity parent = (EditActivity)getActivity();
            dataString = parent.getAllData();
        }
        View view = inflater.inflate(R.layout.fragment_register_dialog,container,false);
        TextView confirmText = (TextView)view.findViewById(R.id.confirmText);
        if(dataString == null){
            dataString = "";
        }
        String confirmString = ConstantValues.CONFIRMATION_TEXT_JP + "\n" + dataString;
        confirmText.setText(confirmString);
        Button confirmBtn = (Button)view.findViewById(R.id.confirmBtn);
        confirmBtn.setText(R.string.ok_button);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerData();
            }
        });
        return view;
    }

    private void registerData(){
        RegisterInformer registerInformer = RegisterInformer.getInstance();
        registerInformer.setData(dataString,isRepeatable);

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
                    = getActivity().getSharedPreferences("ConfigData", Context.MODE_PRIVATE);
            realm = realmManager.getRealm(getActivity());

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
            dbData.setDateStartDate(formatWrapper.getFormatedDateWithTime(dataMap.get("startDate")));
            dbData.setDateStartDay(formatWrapper.getFormatedDate(dataMap.get("startDate").split(" ")[0]));
            dbData.setStartDay(dataMap.get("startDate").split(" ")[0]);
            dbData.setDateEndDate(formatWrapper.getFormatedDateWithTime(dataMap.get("endDate")));
            dbData.setDateEndDay(formatWrapper.getFormatedDate(dataMap.get("endDate").split(" ")[0]));
            dbData.setMonth(dataMap.get("startDate").split(" ")[0].split("/")[1]);
            dbData.setPlace(dataMap.get("place"));
            dbData.setDescription(dataMap.get("description"));
            if(isRepeatable){
                dbData.setIsRepeatable(true);
            } else {
                dbData.setIsRepeatable(false);
            }

            //get Current Date for CreatedAt
            DatePickActivity parent = (DatePickActivity)getActivity();
            //Use Map
            Map<String, String> timeNowMap = parent.getTimeMap();
            dbData.setDateCreatedAt(formatWrapper.getFormatedDateWithTime(timeNowMap.get("date") + " " + timeNowMap.get("time")));

            //Calculating and Registering sum of scheduled plans
            Calculator dayCalc = new Calculator();
            Calculator calc = new Calculator();
            dayCalc.calcGap(formatWrapper.getFormatedStringDateWithTime(dbData.getDateStartDate())
                    ,formatWrapper.getFormatedStringDateWithTime(dbData.getDateEndDate()));

            int dayTotal = 0;
            results = query.equalTo("dateStartDay",formatWrapper.getFormatedDate(dataMap.get("startDate").split(" ")[0]))
                    .notEqualTo("id",putId).findAll();
            for(int i = 0;i <  results.size();i++){
                if(results.get(i).getId() != putId) {
                    calc.calcGap(formatWrapper.getFormatedStringDateWithTime(results.get(i).getDateStartDate())
                            , formatWrapper.getFormatedStringDateWithTime(results.get(i).getDateEndDate()));
                    dayTotal += calc.getAllGapInHour();
                    calc.reset();
                }
            }

            MyCalendar myCalendar = new MyCalendar();
            myCalendar.setDateFromFormat(dataMap.get("startDate").split(" ")[0]);
            List<String> daysInWeek = myCalendar.getDaysInWeek();
            results = query.equalTo("dateStartDay",formatWrapper.getFormatedDate(daysInWeek.get(MyCalendar.MONDAY)))
                    .or().equalTo("dateStartDay",formatWrapper.getFormatedDate(daysInWeek.get(MyCalendar.TUESDAY)))
                    .or().equalTo("dateStartDay",formatWrapper.getFormatedDate(daysInWeek.get(MyCalendar.WEDNESDAY)))
                    .or().equalTo("dateStartDay",formatWrapper.getFormatedDate(daysInWeek.get(MyCalendar.THURSDAY)))
                    .or().equalTo("dateStartDay",formatWrapper.getFormatedDate(daysInWeek.get(MyCalendar.FRIDAY)))
                    .or().equalTo("dateStartDay",formatWrapper.getFormatedDate(daysInWeek.get(MyCalendar.SATURDAY)))
                    .or().equalTo("dateStartDay",formatWrapper.getFormatedDate(daysInWeek.get(MyCalendar.SUNDAY)))
                    .findAll();

            int weekTotal = 0;
            for(int i = 0;i < results.size();i++){
                if(results.get(i).getId() != putId) {
                    calc.calcGap(formatWrapper.getFormatedStringDateWithTime(results.get(i).getDateStartDate())
                            , formatWrapper.getFormatedStringDateWithTime(results.get(i).getDateEndDate()));
                    weekTotal += calc.getAllGapInHour();
                    calc.reset();
                }
            }

            int monthTotal = 0;
            results = query.equalTo("month",dataMap.get("startDate").split(" ")[0].split("/")[1]).findAll();
            for(int i = 0;i < results.size();i++){
                if(results.get(i).getId() != putId) {
                    calc.calcGap(formatWrapper.getFormatedStringDateWithTime(results.get(i).getDateStartDate())
                            , formatWrapper.getFormatedStringDateWithTime(results.get(i).getDateEndDate()));
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
                            getActivity(), dbData.getTitle(), dbData.getId()
                            , formatWrapper.getFormatedStringDateWithTime(dbData.getDateStartDate())
                    );
                }

//                        preferences.edit().putInt("nowRegistered", calc.getAllGapInHour()).apply();
                preferences.edit().putString("nowRegistered", String.valueOf(dayCalc.getAllGapInHour())).apply();
                registerInformer.informToActivity();
                getActivity().finish();
            }else{
                realm.cancelTransaction();
                Toast.makeText(parent, R.string.limit_over_toasttext, Toast.LENGTH_SHORT).show();
                dismiss();
            }

        }
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


}
