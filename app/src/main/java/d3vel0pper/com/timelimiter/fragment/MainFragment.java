package d3vel0pper.com.timelimiter.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.MainActivity;
import d3vel0pper.com.timelimiter.adapter.RealmAdapter;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.FormatWrapper;
import d3vel0pper.com.timelimiter.realm.RealmManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by D3vel0pper on 2016/10/25.
 */
public class MainFragment extends Fragment {

    private MainActivity parent;
    private Context context;
    public ListView listView;
    public int itemPosition;
    public RealmAdapter realmAdapter;
    private RealmManager realmManager;

    private String TAG;

    public MainFragment(){
        TAG = "default";
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        parent = (MainActivity)getActivity();

        context = getActivity().getBaseContext();
        realmManager = parent.realmManager;

        final RealmConfiguration realmConfiguration = realmManager.getConfiguration(parent);
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_main,container,false);
        listView = (ListView)v.findViewById(R.id.itemList);
        setUpListView();
        parent.listView = listView;
        return v;
    }

    public void setUpListView(){
        //set RealmResult by using Handler to handle ResultData from UI thread
        //if u don't use handler, it will be crushed By NPE
        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                realmAdapter = new RealmAdapter(context,TAG);
                listView.setAdapter(realmAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DBData dbdata = (DBData)listView.getItemAtPosition(position);
                        //Map of data (Using Map)
                        Map<String, String> dataMap = new HashMap<>();
                        dataMap.put("title",dbdata.getTitle());
//                        dataMap.put("startDate",dbdata.getStartDate());
//                        dataMap.put("endDate",dbdata.getEndDate());
                        FormatWrapper formatWrapper = new FormatWrapper();
                        dataMap.put("startDate",formatWrapper.getFormatedStringDateWithTime(dbdata.getDateStartDate()));
                        dataMap.put("endDate",formatWrapper.getFormatedStringDateWithTime(dbdata.getDateStartDate()));
                        dataMap.put("place",dbdata.getPlace());
                        dataMap.put("description",dbdata.getDescription());
                        ShowDetailFragment sdf = new ShowDetailFragment();
                        sdf.setDataMap(dataMap);
                        sdf.show(getActivity().getSupportFragmentManager(),"showDetail");
//                        Toast.makeText(context,"position = " + String.valueOf(position) + " Clicked",Toast.LENGTH_SHORT).show();
                    }
                });
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                        View rtnView = realmAdapter.getView(position,null,null);
//                        itemId = Integer.parseInt(((TextView)rtnView.findViewById(R.id.hiddenData)).getText().toString());
                        itemPosition = position;
                        CustomDialogFragment cdf = new CustomDialogFragment();
                        cdf.show(getActivity().getSupportFragmentManager(),"list");
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

    public void setTag(String tag){
        TAG = tag;
    }

}
