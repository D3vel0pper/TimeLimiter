package d3vel0pper.com.timelimiter.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.adapter.RealmAdapter;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.FormatWrapper;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import d3vel0pper.com.timelimiter.common.listener.RegisteredListener;
import d3vel0pper.com.timelimiter.fragment.CustomDialogFragment;
import d3vel0pper.com.timelimiter.fragment.MainFragment;
import d3vel0pper.com.timelimiter.fragment.ShowDetailFragment;
import d3vel0pper.com.timelimiter.fragment.TestFragment;
import d3vel0pper.com.timelimiter.realm.RealmManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends FragmentActivity implements RegisteredListener, ViewPager.OnPageChangeListener {

    private RegisterInformer registerInformer;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle toggle;
    public int itemId;
    public int itemPosition;
    public RealmManager realmManager;

    public ListView listView;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public FragmentPagerAdapter pagerAdapter;

    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmManager = RealmManager.getInstance();
        setContentView(R.layout.activity_main);

        setUpTestButton();

        //Set Register Informer
        registerInformer = RegisterInformer.getInstance();
        registerInformer.setListener(this);

        ImageButton mvToDatePick = (ImageButton)findViewById(R.id.mvToDailyWork);
        mvToDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DailyTaskActivity.class);
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

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getItemPosition(Object object){
                return POSITION_NONE;
            }

            @Override
            public Fragment getItem(int position) {

                switch(position){
                    case 0:
                        MainFragment fragmentToday;
                        fragmentToday = new MainFragment();
                        fragmentToday.setTag("today");
                        return fragmentToday;
                    case 1:
                        MainFragment fragmentTomorrow;
                        fragmentTomorrow = new MainFragment();
                        fragmentTomorrow.setTag("tomorrow");
                        return fragmentTomorrow;
                    case 2:
                        MainFragment fragment;
                        fragment = new MainFragment();
                        fragment.setTag("list");
                        return fragment;
                }
                return new MainFragment();
            }

            @Override
            public CharSequence getPageTitle(int position){
                switch (position){
                    case 0:
                        return "today";
                    case 1:
                        return "tomorrow";
                    case 2:
                        return "list";
                    default:
                        return "default";
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        //setup automatically
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getBaseContext(),"fab clicked !",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),DatePickActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRestart(){
        super.onRestart();
        //reload ViewPager
        reloadViewPager();
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
    public void onRegistered(String data,boolean isRepeatable){
        if(isRepeatable){
            Toast.makeText(this,R.string.registered_data_is + data + "\n" + "isRepeatable = true",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,R.string.registered_data_is + data + "\n" + "isRepeatable = false",Toast.LENGTH_SHORT).show();

        }
    }

    public void deleteRealm(){
        SharedPreferences preferences = getSharedPreferences("ConfigData",MODE_PRIVATE);
        PreferenceManager.setDefaultValues(this,"ConfigData",MODE_PRIVATE,R.xml.default_values,false);
        Realm realm = realmManager.getRealm(this);
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realmManager.closeRealm();
        preferences.edit().putString("nowRegistered","0").apply();
        //reload ViewPager
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
    }

    private void setUpTestButton(){
        this.testBtn = (Button)findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){

    }

    @Override
    public void onPageScrollStateChanged(int position){

    }

    @Override
    public void onPageSelected(int position){
//        Toast.makeText(this,"page " + Integer.toString(position) + "selected",Toast.LENGTH_SHORT).show();
    }

    public void reloadViewPager(){
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
    }

}
