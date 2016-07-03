package d3vel0pper.com.timelimiter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.adapter.SettingAdapter;

/**
 * Created by D3vel0pper on 2016/07/04.
 */
public class SettingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ListView listView = (ListView)findViewById(R.id.settingList);
        SettingAdapter adapter = new SettingAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

}