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
import io.realm.Sort;

/**
 * Created by D3vel0pper on 2016/06/28.
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
        //if sort() is not called, order will be not in correct position after delete Object
        this.realmResults = query.findAll().sort("id", Sort.ASCENDING);

        TextView hiddenId = (TextView)convertView.findViewById(R.id.hiddenData);
//        hiddenId.setText(String.valueOf(realmResults.get(position).getId()) + " " + realmResults.get(position).getStartDay() + " " + realmResults.get(position).getMonth());
        hiddenId.setText(String.valueOf(realmResults.get(position).getId()));
        hiddenId.setVisibility(View.GONE);
        ((TextView)convertView.findViewById(R.id.titleText)).setText(realmResults.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.startDateText)).setText(realmResults.get(position).getStartDate());
        ((TextView)convertView.findViewById(R.id.endDateText)).setText(realmResults.get(position).getEndDate());
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
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<DBData> query = realm.where(DBData.class);
        this.realmResults = query.findAll();
    }

}
