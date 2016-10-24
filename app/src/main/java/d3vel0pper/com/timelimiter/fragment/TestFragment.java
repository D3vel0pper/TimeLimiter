package d3vel0pper.com.timelimiter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import d3vel0pper.com.timelimiter.R;

/**
 * Created by D3vel0pper on 2016/10/25.
 */
public class TestFragment extends Fragment {

    private static TestFragment instance;

    int page;

    public TestFragment(){
        page = 0;
    }

    public static TestFragment getInstance(){
        if(instance == null){
            return new TestFragment();
        }
        return instance;
    }

    public void setPageNum(int page){
        this.page = page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.test_fragment,container,false);
        TextView tv = (TextView) v.findViewById(R.id.testText);
        tv.setText("Page" + Integer.toString(page));
        return v;
    }
}
