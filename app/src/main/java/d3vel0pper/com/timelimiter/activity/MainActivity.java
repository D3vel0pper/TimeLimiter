package d3vel0pper.com.timelimiter.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.RunnableFuture;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.adapter.RealmAdapter;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.FormatWrapper;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import d3vel0pper.com.timelimiter.common.listener.RegisteredListener;
import d3vel0pper.com.timelimiter.fragment.CustomDialogFragment;
import d3vel0pper.com.timelimiter.fragment.ShowDetailFragment;
import d3vel0pper.com.timelimiter.realm.RealmManager;
import d3vel0pper.com.timelimiter.realm.RealmMigrator;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends FragmentActivity implements RegisteredListener {

    private RegisterInformer registerInformer;
    private Realm realm;
    private Context context;
    private SharedPreferences preferences;
    public ListView listView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle toggle;
    public int itemId;
    public int itemPosition;
    public RealmAdapter realmAdapter;
    private RealmManager realmManager;

    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("ConfigData",MODE_PRIVATE);
        PreferenceManager.setDefaultValues(this,"ConfigData",MODE_PRIVATE,R.xml.default_values,false);
        context = getBaseContext();
        realmManager = RealmManager.getInstance();
        //!!!-----------This part will cause unexpected Error that relate on multi file access--------!!!
        final RealmConfiguration realmConfiguration = realmManager.getConfiguration(this);
        Realm.setDefaultConfiguration(realmConfiguration);
        //---------------------------------------------------------------------------------------------
//        deleteRealm();
        setContentView(R.layout.activity_main);

        setUpTestButton();

        //Set Register Informer
        registerInformer = RegisterInformer.getInstance();
        registerInformer.setListener(this);

        ImageButton mvToDatePick = (ImageButton)findViewById(R.id.mvToDatePick);
        mvToDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DatePickActivity.class);
                startActivity(intent);
            }
        });

        ImageButton settingBtn = (ImageButton)findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);
            }
        });

        setUpListView();
//        ---------------------------Test Code---------------------------------------------------
        Button deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete Realm
                deleteRealm();
            }
        });
        //modify here to decide show or not
        deleteBtn.setVisibility(View.VISIBLE);

//        --------------------------Test Code End-------------------------------------------------
        listView = (ListView)findViewById(R.id.itemList);

    }

    @Override
    public void onRestart(){
        super.onRestart();
        //Add here the code which reload the DB
        setUpListView();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        realmManager.closeRealm();
    }

    //Custom Listener of registration
    @Override
    public void onRegistered(String data){
        Toast.makeText(this,R.string.registered_data_is + data,Toast.LENGTH_SHORT).show();
    }

    public void setUpListView(){
        //set RealmResult by using Handler to handle ResultData from UI thread
        //if u don't use handler, it will be crushed By NPE
        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                realmAdapter = new RealmAdapter(context);
                listView.setAdapter(realmAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DBData dbdata = (DBData)listView.getItemAtPosition(position);
                        //Map of data (Using Map)
                        Map<String, String> dataMap = new HashMap<>();
                        dataMap.put("title",dbdata.getTitle());
//                        dataMap.put("startDate",dbdata.getStartDate());
//                        dataMap.put("endDate",dbdata.getEndDate());
                        FormatWrapper formatWrapper = new FormatWrapper();
                        dataMap.put("startDate",formatWrapper.getFormatedStringDateWithTime(dbdata.getDateStartDate()));
                        dataMap.put("endDate",formatWrapper.getFormatedStringDateWithTime(dbdata.getDateStartDate()));
                        dataMap.put("place",dbdata.getPlace());
                        dataMap.put("description",dbdata.getDescription());
                        ShowDetailFragment sdf = new ShowDetailFragment();
                        sdf.setDataMap(dataMap);
                        sdf.show(getSupportFragmentManager(),"showDetail");
//                        Toast.makeText(context,"position = " + String.valueOf(position) + " Clicked",Toast.LENGTH_SHORT).show();
                    }
                });
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                        View rtnView = realmAdapter.getView(position,null,null);
//                        itemId = Integer.parseInt(((TextView)rtnView.findViewById(R.id.hiddenData)).getText().toString());
                        itemPosition = position;
                        CustomDialogFragment cdf = new CustomDialogFragment();
                        cdf.show(getSupportFragmentManager(),"list");
                        return true;
                    }
                });
            }
        });
    }

    public void deleteRealm(){
        realm = realmManager.getRealm(this);
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realmManager.closeRealm();
        preferences.edit().putString("nowRegistered","0").apply();
        //After delete, reset up listView
        setUpListView();
    }

    private void setUpTestButton(){
        this.testBtn = (Button)findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TabActivity.class);
                startActivity(intent);
            }
        });
    }

}
