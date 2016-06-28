package d3vel0pper.com.timelimiter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Button;
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
    private String fromRealm = "";
    private TextView testText;
    private RealmResults<DBData> resultAll;
    private ListView listView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getBaseContext();
        final RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        setContentView(R.layout.activity_main);

        registerInformer = RegisterInformer.getInstance();
        registerInformer.setListener(this);

        Button mvToDatePick = (Button)findViewById(R.id.mvToDatePick);
        mvToDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DatePickActivity.class);
                startActivity(intent);
            }
        });

//        ---------------------------Test Code---------------------------------------------------

        loadRealm();

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

    @Override
    public void onRegistered(String data){
        Toast.makeText(this,"The data = " + data,Toast.LENGTH_SHORT).show();
    }

    public void loadRealm(){
//        realm = Realm.getDefaultInstance();
//        RealmQuery<DBData> query = realm.where(DBData.class);
//        resultAll = query.findAll();
        //realmAdapter.setRealmResults();
        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                RealmAdapter realmAdapter = new RealmAdapter(context);
                listView.setAdapter(realmAdapter);
            }
        });
        if(realm != null){
            realm.close();
        }
//        realm = Realm.getDefaultInstance();
//        RealmQuery<DBData> query = realm.where(DBData.class);
//        resultAll = query.findAll();
//        String temp = "";
//        //if no data is exist
//        if (resultAll.size() != 0) {
//            for(int i = 0;i<resultAll.size();i++){
//                DBData dbData = resultAll.get(i);
//                temp += "タイトル：" + dbData.getTitle() + "\n開始：" + dbData.getStartDate()
//                    + "\n終了：" + dbData.getEndDate() + "\n場所：" + dbData.getPlace() + "\n詳細："
//                    + dbData.getDescription() + "\n";
//            }
//            fromRealm += temp;
//        } else{
//        }
//        testText = (TextView)findViewById(R.id.testText);
//        testText.setText(fromRealm);
//        realm.close();

    }

}
