package d3vel0pper.com.timelimiter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.FormatWrapper;
import d3vel0pper.com.timelimiter.realm.RealmManager;
import d3vel0pper.com.timelimiter.realm.RealmMigrator;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by D3vel0pper on 2016/06/28.
 */
public class RealmAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private RealmResults<DBData> realmResults;
    private FormatWrapper fw;
    private RealmManager realmManager;

    public RealmAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fw = new FormatWrapper();
        realmManager = RealmManager.getInstance();
    }

    @Override
    public int getCount(){
//        realmManager.closeRealm();
        loadRealm();
        return realmResults.size();
    }

    @Override
    public Object getItem(int position){
//        realmManager.closeRealm();
        loadRealm();
        TextView v = (TextView)getView(position,null,null).findViewById(R.id.hiddenData);
        Realm realm = realmManager.getRealm(context);
        RealmQuery<DBData> query = realm.where(DBData.class);
        RealmResults<DBData> res = query.equalTo("id",Integer.parseInt(v.getText().toString())).findAll();
        return res.get(0);
    }

    @Override
    public long getItemId(int position){
//        realmManager.closeRealm();
        loadRealm();
//        return realmResults.get(position).getId();
        return Integer.parseInt(((TextView)getView(position,null,null).findViewById(R.id.hiddenData)).getText().toString());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = layoutInflater.inflate(R.layout.card_layout,parent,false);

        loadRealm();
//        this.realmResults = query.findAll().sort("startDate", Sort.ASCENDING);

        //Hidden data
        TextView hiddenId = (TextView)convertView.findViewById(R.id.hiddenData);
//        hiddenId.setText(String.valueOf(realmResults.get(position).getId()) + " " + realmResults.get(position).getStartDay() + " " + realmResults.get(position).getMonth());
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
        //migration process for Realm
        realmManager = RealmManager.getInstance();
        Realm realm = realmManager.getRealm(context);
//        Realm realm = Realm.getDefaultInstance();
        RealmQuery<DBData> query = realm.where(DBData.class).notEqualTo("isComplete",true);
        //if sort() is not called, order will be not in correct position after delete Object
        this.realmResults = query.findAll().sort("id", Sort.ASCENDING);
    }

}
