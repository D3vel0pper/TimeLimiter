package d3vel0pper.com.timelimiter.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.InformToActivity;
import d3vel0pper.com.timelimiter.fragment.MyListener;


public class MainActivity extends FragmentActivity implements MyListener {

    InformToActivity informToActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        informToActivity = InformToActivity.getInstance();
        informToActivity.setListener(this);

        Button mvToDatePick = (Button)findViewById(R.id.mvToDatePick);
        mvToDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DatePickActivity.class);
                startActivity(intent);
            }
        });

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
    public void passTheDate(String data){
        Toast.makeText(this,"The data = " + data,Toast.LENGTH_SHORT).show();
    }

}
