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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.RunnableFuture;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.adapter.RealmAdapter;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import d3vel0pper.com.timelimiter.common.listener.RegisteredListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends FragmentActivity implements RegisteredListener {

    private RegisterInformer registerInformer;
    private Realm realm;
    private ListView listView;
    private Context context;
    private SharedPreferences preferences;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("ConfigData",MODE_PRIVATE);
        PreferenceManager.setDefaultValues(this,"ConfigData",MODE_PRIVATE,R.xml.default_values,false);
        context = getBaseContext();
        final RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        setContentView(R.layout.activity_main);

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

        loadRealm();
//        ---------------------------Test Code---------------------------------------------------
        Button testBtn = (Button)findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("maxHourPerDay",10);
                editor.apply();
            }
        });

        Button deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(realm != null){
                    realm.close();
                }
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
                realm.close();
                //After delete, reload Realm
                loadRealm();
            }
        });

//        --------------------------Test Code End-------------------------------------------------
        listView = (ListView)findViewById(R.id.itemList);

    }

    @Override
    public void onRestart(){
        super.onRestart();
        //Add here the code which reload the DB
        loadRealm();
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    //Custom Listener of registration
    @Override
    public void onRegistered(String data){
        Toast.makeText(this,"The data = " + data,Toast.LENGTH_SHORT).show();
    }

    public void loadRealm(){
        //set RealmResult by using Handler to handle ResultData from UI thread
        //if u don't use handler, it will be crushed By NPE
        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                RealmAdapter realmAdapter = new RealmAdapter(context);
                listView.setAdapter(realmAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context,"position = " + String.valueOf(position) + " Clicked",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        if(realm != null){
            realm.close();
        }
    }

    /**
     *
     */
    private void setPreferences(){

    }
}
