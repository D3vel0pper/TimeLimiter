package d3vel0pper.com.timelimiter.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.EditActivity;
import d3vel0pper.com.timelimiter.common.Calculator;
import d3vel0pper.com.timelimiter.common.ConstantValues;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.FormatWrapper;
import d3vel0pper.com.timelimiter.common.MyCalendar;
import d3vel0pper.com.timelimiter.common.Notificationer;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import d3vel0pper.com.timelimiter.realm.RealmManager;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by D3vel0pper on 2016/07/14.
 */
public class EditDialogFragment extends DialogFragment {
    public EditDialogFragment(){}
    private String dataString;
    private Realm realm;
    private EditActivity parent;
    private FormatWrapper formatWrapper;
    private RealmManager realmManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        formatWrapper = new FormatWrapper();
        realmManager = RealmManager.getInstance();
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
        View view = inflater.inflate(R.layout.fragment_register_dialog,container,false);
        TextView confirmText = (TextView)view.findViewById(R.id.confirmText);
        parent = (EditActivity)getActivity();
        if(dataString == null){
            dataString = "";
        }
        dataString = parent.getAllData();
        String confirmString = ConstantValues.CONFIRMATION_TEXT_JP + "\n" + dataString;
        confirmText.setText(confirmString);
        confirmText.setGravity(Gravity.CENTER);
        Button confirmBtn = (Button)view.findViewById(R.id.confirmBtn);
        confirmBtn.setText(R.string.ok_button);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterInformer registerInformer = RegisterInformer.getInstance();
                registerInformer.setData(dataString);
                if(dataString != null) {
                    inOnClick(registerInformer);
                }
            }
        });
        return view;
    }

    public void inOnClick(RegisterInformer registerInformer){
        SharedPreferences preferences
                = getActivity().getSharedPreferences("ConfigData", Context.MODE_PRIVATE);
        realm = realmManager.getRealm(getActivity());
        RealmResults<DBData> results;
        RealmResults<DBData> updateTarget;
        RealmQuery<DBData> query = realm.where(DBData.class);

        //Use Map
        Map<String, String> dataMap = parent.getDataMap();
        //Search target data exist
        updateTarget = query.equalTo("id",Integer.parseInt(dataMap.get("id"))).findAll();
        if(updateTarget.isEmpty()){
            Toast.makeText(parent, R.string.file_not_exists, Toast.LENGTH_SHORT).show();
            dismiss();
        }
        int putId = Integer.parseInt(dataMap.get("id"));

        //Calculating and Registering sum of scheduled plans
        Calculator dayCalc = new Calculator();
        Calculator calc = new Calculator();
        dayCalc.calcGap(dataMap.get("startDate"),dataMap.get("endDate"));

        int dayTotal = 0;
        results = query.equalTo("startDay",dataMap.get("startDate").split(" ")[0]).notEqualTo("id",putId).findAll();
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
            realm.beginTransaction();
            //update data
            Map<String, String> timeNowMap = parent.getTimeMap();
            updateTarget.get(0).setDateCreatedAt(formatWrapper
                    .getFormatedDateWithTime(timeNowMap.get("date") + " " + timeNowMap.get("time"))
            );
            updateTarget.get(0).setTitle(dataMap.get("title"));
            updateTarget.get(0).setDateStartDate(formatWrapper.getFormatedDateWithTime(dataMap.get("startDate")));
            updateTarget.get(0).setDateStartDay(formatWrapper.getFormatedDate(dataMap.get("startDate").split(" ")[0]));
            updateTarget.get(0).setDateEndDate(formatWrapper.getFormatedDateWithTime(dataMap.get("endDate")));
            updateTarget.get(0).setDateEndDay(formatWrapper.getFormatedDate(dataMap.get("endDate").split(" ")[0]));
            updateTarget.get(0).setMonth(dataMap.get("startDate").split(" ")[0].split("/")[1]);
            updateTarget.get(0).setPlace(dataMap.get("place"));
            updateTarget.get(0).setDescription(dataMap.get("description"));
            realm.commitTransaction();
            //register Notification
//            if (preferences.getBoolean("notification", true)) {
//                Notificationer.setLocalNotification(
//                        getActivity(), dbData.getTitle(), dbData.getId(), dbData.getStartDate()
//                );
//            }
            //Uses lis.get() because of not using dbData
            if (Boolean.valueOf(preferences.getString("notification", "true"))) {
                Notificationer.cancelLocalNotification(getActivity(),Integer.parseInt(dataMap.get("id")));
                Notificationer.setLocalNotification(
                        getActivity(), dataMap.get("title"), Integer.parseInt(dataMap.get("id")), dataMap.get("startDate")
                );
            }

//                        preferences.edit().putInt("nowRegistered", calc.getAllGapInHour()).apply();
            preferences.edit().putString("nowRegistered", String.valueOf(dayCalc.getAllGapInHour())).apply();
            registerInformer.informToActivity();
            getActivity().finish();
        }else{
            try {
                realm.cancelTransaction();
            } catch(IllegalStateException e){
                Log.e("IllegalStateException","transAction have not been started");
            }
            Toast.makeText(parent, R.string.limit_over_toasttext, Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    private boolean isRegistable(SharedPreferences preferences,Calculator calcedCalc,int dayTotal,int weekTotal,int monthTotal){
//        try{
//            if(((preferences.getInt("nowRegistered",0) + calcedCalc.getAllGapInHour()) < preferences.getInt("maxHourPerDay",Integer.MAX_VALUE))
//                && ((preferences.getInt("nowRegistered",0) + calcedCalc.getAllGapInHour()) < preferences.getInt("maxHourPerWeek",Integer.MAX_VALUE))
//                && ((preferences.getInt("nowRegistered",0) + calcedCalc.getAllGapInHour()) < preferences.getInt("maxHourPerMonth",Integer.MAX_VALUE))){
//                return true;
//            }
//
//        } catch(ClassCastException e){
//            if(((Integer.parseInt(preferences.getString("nowRegistered","0"))
//                    + calcedCalc.getAllGapInHour()) < preferences.getInt("maxHourPerDay",Integer.MAX_VALUE))
//                    && ((Integer.parseInt(preferences.getString("nowRegistered","0"))
//                    + calcedCalc.getAllGapInHour()) < preferences.getInt("maxHourPerWeek",Integer.MAX_VALUE))
//                    && ((Integer.parseInt(preferences.getString("nowRegistered","0")
//                    + calcedCalc.getAllGapInHour()) < preferences.getInt("maxHourPerMonth",Integer.MAX_VALUE)))){
//                return true;
//            }
//        }
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
