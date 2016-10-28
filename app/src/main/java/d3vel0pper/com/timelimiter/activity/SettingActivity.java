package d3vel0pper.com.timelimiter.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.adapter.SettingAdapter;
import d3vel0pper.com.timelimiter.fragment.CustomDialogFragment;

/**
 * Created by D3vel0pper on 2016/07/04.
 */
public class SettingActivity extends FragmentActivity {

    public SettingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        ImageButton resetBtn = (ImageButton)findViewById(R.id.resetImgBtn);
        resetBtn.setVisibility(View.VISIBLE);
        ImageButton mvToDailyWork, settingBtn;
        mvToDailyWork = (ImageButton)findViewById(R.id.mvToDailyWork);
        mvToDailyWork.setVisibility(View.GONE);
        settingBtn = (ImageButton)findViewById(R.id.settingBtn);
        settingBtn.setVisibility(View.GONE);
        toolbar.setTitle("設定");

        TextView textView = (TextView)findViewById(R.id.percentage);
//        textView.setText(String.valueOf(getSharedPreferences("ConfigData",MODE_PRIVATE).getInt("nowRegistered",0)));
        textView.setText(getSharedPreferences("ConfigData",MODE_PRIVATE).getString("nowRegistered","0"));
        ListView listView = (ListView)findViewById(R.id.settingList);
        adapter = new SettingAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomDialogFragment cdf = new CustomDialogFragment();
                switch (position){
                    case 0:
                        cdf.show(getSupportFragmentManager(),"setting0");
                        break;
                    case 1:
                        cdf.show(getSupportFragmentManager(),"setting1");
                        break;
                    case 2:
                        cdf.show(getSupportFragmentManager(),"setting2");
                        break;
                }
            }
        });

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
