package d3vel0pper.com.timelimiter.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import d3vel0pper.com.timelimiter.common.listener.RegisteredListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends FragmentActivity implements RegisteredListener {

    RegisterInformer registerInformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());
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

        String fromRealm = "";
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<DBData> query = realm.where(DBData.class);
        RealmResults<DBData> resultAll = query.findAll();
        DBData dbData = resultAll.get(0);
        fromRealm = dbData.getStartDate();

        TextView testText = (TextView)findViewById(R.id.testText);
        testText.setText(fromRealm);

    }

    @Override
    public void onRestart(){
        super.onRestart();
        //Add here the code which reload the DB
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

}
