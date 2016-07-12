package d3vel0pper.com.timelimiter.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.fragment.EditFragment;

public class EditActivity extends DatePickActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportFragmentManager().beginTransaction().add(R.id.container,EditFragment.getInstance(),"EditFragment").commit();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
