package d3vel0pper.com.timelimiter.fragment;

import android.app.Dialog;
import android.os.Bundle;

import d3vel0pper.com.timelimiter.activity.DatePickActivity;

/**
 * Created by HotFlush on 2016/06/23.
 */
public class CBDatePickerFragment extends DatePickerFragment implements DatePickerFragment.MyCallBack {
    public CBDatePickerFragment(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setMyCallBack(this,"!!!!TAGged!!!!");
        return super.onCreateDialog(savedInstanceState);
    }

}
