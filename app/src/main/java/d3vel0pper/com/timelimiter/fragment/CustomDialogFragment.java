package d3vel0pper.com.timelimiter.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.DatePickActivity;
import d3vel0pper.com.timelimiter.activity.SettingActivity;
import d3vel0pper.com.timelimiter.common.Calculator;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.Notificationer;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import io.realm.Realm;

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
        } else if(getTag().equals("setting0") || getTag().equals("setting1") || getTag().equals("setting2")){
           view = settingCase(inflater,container,savedInstanceState,(SettingActivity) parent);
        } else {
            view = inflater.inflate(R.layout.fragment_register_dialog,container,false);
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
                RegisterInformer registerInformer = RegisterInformer.getInstance();
                registerInformer.setData(dataString);
                registerInformer.informToActivity();
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

                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    DBData dbData = realm.createObject(DBData.class);
                    dbData.setTitle(data[0]);
                    //dbData.setCreatedAt();
                    dbData.setStartDate(data[2]);
                    dbData.setEndDate(data[4]);
                    dbData.setPlace(data[5]);
                    dbData.setDescription(data[6]);
                    //get Current Date for CreatedAt
                    DatePickActivity parent = (DatePickActivity)getActivity();
                    String[] timeNow = parent.getTimeNow();
                    dbData.setCreatedAt(timeNow[0] + " " + timeNow[1]);
                    realm.commitTransaction();
                    //register Notification
                    Notificationer.setLocalNotification(
                            getActivity(),dbData.getTitle(),dbData.getId(),dbData.getStartDate()
                    );
                    //Calculating and Registering sum of scheduled plans
                    Calculator calc = new Calculator();
                    calc.getTimeCountGap(dbData.getStartDate(),dbData.getEndDate());
                    getActivity().finish();
                }
            }
        });
        return view;
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
                        preferences.edit().
                                putInt("maxHourPerDay",Integer.parseInt(editText.getText().toString())).
                                apply();
                        parent.adapter.notifyDataSetChanged();
                        break;
                    case "setting1":
                        preferences.edit().
                                putInt("maxHourPerWeek",Integer.parseInt(editText.getText().toString())).
                                apply();
                        parent.adapter.notifyDataSetChanged();
                        break;
                    case "setting2":
                        preferences.edit().
                                putInt("maxHourPerMonth",Integer.parseInt(editText.getText().toString())).
                                apply();
                        parent.adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                Toast.makeText(parent, "changed settings", Toast.LENGTH_SHORT).show();
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

}
