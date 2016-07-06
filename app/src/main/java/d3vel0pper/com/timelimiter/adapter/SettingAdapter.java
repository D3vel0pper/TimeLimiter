package d3vel0pper.com.timelimiter.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import d3vel0pper.com.timelimiter.R;

/**
 * Created by D3vel0pper on 2016/07/04.
 */
public class SettingAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private SharedPreferences preferences;

    public SettingAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.preferences = context.getSharedPreferences("ConfigData",Context.MODE_PRIVATE);
    }

    @Override
    public int getCount(){
        return 6;
    }

    @Override
    public Object getItem(int position){
        switch (position){
            case 0:
                return String.valueOf(preferences.getInt("maxHourPerDay",Integer.MAX_VALUE));
            case 1:
                return String.valueOf(preferences.getInt("maxHourPerWeek",Integer.MAX_VALUE));
            case 2:
                return String.valueOf(preferences.getInt("maxHourPerMonth",Integer.MAX_VALUE));
            case 3:
                return String.valueOf(preferences.getInt("showingMode",0));
            case 4:
                return String.valueOf(preferences.getBoolean("notification",true));
            case 5:
                return String.valueOf(preferences.getBoolean("deleteAuto",false));
        }
        return 0;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = layoutInflater.inflate(R.layout.setting_item_layout,parent,false);
        TextView guide = (TextView)convertView.findViewById(R.id.guide);
        TextView inputedText = (TextView)convertView.findViewById(R.id.inputedText);
        Switch itemSwitch = (Switch)convertView.findViewById(R.id.itemSwitch);
        switch (position){
            case 0:
                guide.setText("h/Day");
//                inputedText.setText(String.valueOf(preferences.getInt("maxHourPerDay",Integer.MAX_VALUE)));
                inputedText.setText(preferences.getString("maxHourPerDay",String.valueOf(Integer.MAX_VALUE)));
                break;
            case 1:
                guide.setText("h/Week");
//                inputedText.setText(String.valueOf(preferences.getInt("maxHourPerWeek",Integer.MAX_VALUE)));
                inputedText.setText(preferences.getString("maxHourPerWeek",String.valueOf(Integer.MAX_VALUE)));
                break;
            case 2:
                guide.setText("h/Month");
//                inputedText.setText(String.valueOf(preferences.getInt("maxHourPerMonth",Integer.MAX_VALUE)));
                inputedText.setText(preferences.getString("maxHourPerMonth",String.valueOf(Integer.MAX_VALUE)));
                break;
            case 3:
                inputedText.setText("showingMode");
                break;
            case 4:
                guide.setText("Toggle Notification");
                inputedText.setVisibility(View.GONE);
                itemSwitch.setVisibility(View.VISIBLE);
//                itemSwitch.setChecked(preferences.getBoolean("notification",true));
                itemSwitch.setChecked(Boolean.valueOf(preferences.getString("notification","true")));
                itemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        preferences.edit().putBoolean("notification",isChecked).apply();
                        preferences.edit().putString("notification",String.valueOf(isChecked)).apply();
                    }
                });
                break;
            case 5:
                guide.setText("Toggle auto Delete");
                inputedText.setVisibility(View.GONE);
                itemSwitch.setVisibility(View.VISIBLE);
//                itemSwitch.setChecked(preferences.getBoolean("deleteAuto",false));
                itemSwitch.setChecked(Boolean.valueOf(preferences.getString("deleteAuto","false")));
                itemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        preferences.edit().putBoolean("deleteAuto",isChecked).apply();
                        preferences.edit().putString("deleteAuto",String.valueOf(isChecked)).apply();
                    }
                });
                break;
        }

        return convertView;
    }


}
