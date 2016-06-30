package d3vel0pper.com.timelimiter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.common.DBData;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by HotFlush on 2016/06/28.
 */
public class RealmAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private RealmResults<DBData> realmResults;

    public RealmAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        loadRealm();
        return realmResults.size();
    }

    @Override
    public Object getItem(int position){
        loadRealm();
        return realmResults.get(position);
        //return dataArray.get(position);
    }

    @Override
    public long getItemId(int position){
        loadRealm();
        return realmResults.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = layoutInflater.inflate(R.layout.card_layout,parent,false);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<DBData> query = realm.where(DBData.class);
        this.realmResults = query.findAll();

        ((TextView)convertView.findViewById(R.id.titleText)).setText(realmResults.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.startDateText)).setText(realmResults.get(position).getStartDate());
        ((TextView)convertView.findViewById(R.id.endDateText)).setText(realmResults.get(position).getEndDate());
        ((TextView)convertView.findViewById(R.id.placeText)).setText(realmResults.get(position).getPlace());
        ((TextView)convertView.findViewById(R.id.descriptionText)).setText(realmResults.get(position).getDescription());

        return convertView;
    }

    private void loadRealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<DBData> query = realm.where(DBData.class);
        this.realmResults = query.findAll();
    }

}
