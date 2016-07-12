package d3vel0pper.com.timelimiter.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import d3vel0pper.com.timelimiter.R;

/**
 * Created by HotFlush on 2016/07/12.
 */
public class EditFragment extends Fragment implements View.OnClickListener{
    /**
     * [0] -> id  [1] -> createdAt  [2] -> title  [3] -> startDate  [4] -> endDate
     * [5] -> place  [6] -> description
     */
    private List<String> dataList;
    private TextView startDateText,endDateText;
    private EditText titleText,placeText,descriptionText;
    private Button startDateBtn,startTimeBtn,endDateBtn,endTimeBtn,endBtn;

    private static EditFragment instance;

    public static EditFragment getInstance(){
        if(instance == null){
            instance = new EditFragment();
        }
        return instance;
    }

    public void setDataList(List<String> list){
        this.dataList = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_date_pick,container,false);
        startDateText = (TextView)view.findViewById(R.id.startText);
        startDateText.setText(dataList.get(3));
        endDateText = (TextView)view.findViewById(R.id.endText);
        endDateText.setText(dataList.get(4));
        titleText = (EditText)view.findViewById(R.id.titleText);
        titleText.setText(dataList.get(2));
        placeText = (EditText)view.findViewById(R.id.placeText);
        placeText.setText(dataList.get(5));
        descriptionText = (EditText)view.findViewById(R.id.descriptionText);
        descriptionText.setText(dataList.get(6));

        startDateBtn = (Button)view.findViewById(R.id.startDateBtn);
        startDateBtn.setOnClickListener(this);
        startTimeBtn = (Button)view.findViewById(R.id.startTimeBtn);
        startTimeBtn.setOnClickListener(this);
        endDateBtn = (Button)view.findViewById(R.id.endDateBtn);
        endDateBtn.setOnClickListener(this);
        endTimeBtn = (Button)view.findViewById(R.id.endTimeBtn);
        endTimeBtn.setOnClickListener(this);
        endBtn = (Button)view.findViewById(R.id.endBtn);
        endBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.startDateBtn:
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.show(getActivity().getSupportFragmentManager(),"startDatePicker");
                break;
            case R.id.startTimeBtn:
                TimePickerFragment tpf = new TimePickerFragment();
                tpf.show(getActivity().getSupportFragmentManager(),"startTimePicker");
                break;
            case R.id.endDateBtn:
                DatePickerFragment pickerEndDate = new DatePickerFragment();
                pickerEndDate.show(getActivity().getSupportFragmentManager(),"endDatePicker");
                break;
            case R.id.endTimeBtn:
                TimePickerFragment pickerEndTime = new TimePickerFragment();
                pickerEndTime.show(getActivity().getSupportFragmentManager(),"endTimePicker");
                break;
            case R.id.endBtn:
                Toast.makeText(getActivity(), "ended correctly", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

}
