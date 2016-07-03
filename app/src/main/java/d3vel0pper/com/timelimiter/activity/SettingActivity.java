package d3vel0pper.com.timelimiter.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        final SettingAdapter adapter = new SettingAdapter(this);
        listView.setAdapter(adapter);

        Button resetButton = (Button)findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("ConfigData",MODE_PRIVATE);
                preferences.edit().clear().apply();
                //invalidate when data changed
                adapter.notifyDataSetChanged();
            }
        });
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
