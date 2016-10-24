package d3vel0pper.com.timelimiter.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.fragment.TestFragment;

/**
 * Created by D3vel0pper on 2016/10/24.
 */
public class TabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.pager);

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                TestFragment testFragment = TestFragment.getInstance();
                testFragment.setPageNum(position);
                return testFragment;
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

        //If use tabLayout only, u can put title by these methods
//        tabLayout.addTab(tabLayout.newTab().setTag("today"));
//        tabLayout.addTab(tabLayout.newTab().setTag("tomorrow"));
//        tabLayout.addTab(tabLayout.newTab().setTag("all"));

        viewPager.addOnPageChangeListener(this);

        //setup automatically
        tabLayout.setupWithViewPager(viewPager);

        //setup manually by these methods
        //tabLayout.setTabsFromPagerAdapter(adapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    @Override
    public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){

    }

    @Override
    public void onPageScrollStateChanged(int position){

    }

    @Override
    public void onPageSelected(int position){
        Toast.makeText(this,"page " + Integer.toString(position) + "selected",Toast.LENGTH_SHORT).show();
    }

}
