package d3vel0pper.com.timelimiter.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.FormatWrapper;
import d3vel0pper.com.timelimiter.fragment.CustomDialogFragment;
import d3vel0pper.com.timelimiter.fragment.ShowDetailFragment;
import d3vel0pper.com.timelimiter.realm.RealmManager;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by D3vel0pper on 2016/10/25.
 */
public class DailyTaskActivity extends AppCompatActivity {

    private ListView listView;
    private DailyAdapter dailyAdapter;
    protected Context context;
    private int itemPosition;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_work);

        setUpToolBar();

        context = getBaseContext();
        listView = (ListView)findViewById(R.id.itemList);
        setUpListView();
    }

    private void setUpToolBar(){

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("定常作業");
        setSupportActionBar(toolbar);

        ImageButton settingBtn, mvToDailyWork;
        settingBtn = (ImageButton)findViewById(R.id.settingBtn);
        mvToDailyWork = (ImageButton)findViewById(R.id.mvToDailyWork);
        settingBtn.setVisibility(View.GONE);
        mvToDailyWork.setVisibility(View.GONE);
    }

    public void setUpListView(){
        //set RealmResult by using Handler to handle ResultData from UI thread
        //if u don't use handler, it will be crushed By NPE
        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                dailyAdapter = new DailyAdapter(context);
                listView.setAdapter(dailyAdapter);
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
                        sdf.show(getSupportFragmentManager(),"showDetail");
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
                        cdf.show(getSupportFragmentManager(),"list");
                        return true;
                    }
                });
            }
        });
    }

    private class DailyAdapter extends BaseAdapter {

        private Context context;
        private RealmManager realmManager;
        private RealmResults<DBData> realmResults;
        private FormatWrapper fw;
        private LayoutInflater layoutInflater;

        public DailyAdapter(Context context){
            this.context= context;
            fw = new FormatWrapper();
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            realmManager = RealmManager.getInstance();
        }

        @Override
        public int getCount(){
            loadRealm();
            return realmResults.size();
        }

        @Override
        public Object getItem(int position){
//            loadRealm();
            TextView v = (TextView)getView(position,null,null).findViewById(R.id.hiddenData);
            Realm realm = realmManager.getRealm(context);
            RealmQuery<DBData> query = realm.where(DBData.class);
            RealmResults<DBData> res = query.equalTo("id",Integer.parseInt(v.getText().toString())).findAll();
            return res.get(0);
        }

        @Override
        public long getItemId(int position){
            loadRealm();
            return Integer.parseInt(((TextView)getView(position,null,null).findViewById(R.id.hiddenData)).getText().toString());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            convertView = layoutInflater.inflate(R.layout.card_layout,parent,false);

            loadRealm();

            //Hidden data
            TextView hiddenId = (TextView)convertView.findViewById(R.id.hiddenData);
            hiddenId.setText(String.valueOf(realmResults.get(position).getId()));
            hiddenId.setVisibility(View.GONE);
            TextView hiddenComplete = (TextView)convertView.findViewById(R.id.hiddenComplete);
            if(realmResults.get(position).getIsComplete()) {
                hiddenComplete.setText("true");
                convertView.setVisibility(View.GONE);
            } else {
                hiddenComplete.setText("false");
            }

            ((TextView)convertView.findViewById(R.id.titleText)).setText(realmResults.get(position).getTitle());
            ((TextView)convertView.findViewById(R.id.startDateText)).setText(fw.getFormatedStringDate(realmResults.get(position).getDateStartDate()));
            ((TextView)convertView.findViewById(R.id.endDateText)).setText(fw.getFormatedStringDate(realmResults.get(position).getDateEndDate()));
            ((TextView)convertView.findViewById(R.id.placeText)).setText(realmResults.get(position).getPlace());
            if(realmResults.get(position).getDescription().length() > 36){
                String temp = realmResults.get(position).getDescription().substring(0,35) + "...";
                ((TextView)convertView.findViewById(R.id.descriptionText)).setText(temp);
            } else{
                ((TextView) convertView.findViewById(R.id.descriptionText)).setText(realmResults.get(position).getDescription());
            }
            return convertView;
        }

        private void loadRealm(){
            realmManager = RealmManager.getInstance();
            Realm realm = realmManager.getRealm(context);
            RealmQuery<DBData> query = realm.where(DBData.class).equalTo("isRepeatable",true).notEqualTo("isComplete",true);
            this.realmResults = query.findAll().sort("id", Sort.ASCENDING);
        }

    }
}
