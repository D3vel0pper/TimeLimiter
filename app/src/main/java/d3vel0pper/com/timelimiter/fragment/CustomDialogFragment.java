package d3vel0pper.com.timelimiter.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import d3vel0pper.com.timelimiter.R;
import d3vel0pper.com.timelimiter.activity.DatePickActivity;
import d3vel0pper.com.timelimiter.common.DBData;
import d3vel0pper.com.timelimiter.common.listener.RegisterInformer;
import io.realm.Realm;

/**
 * Created by D3vel0pper on 2016/06/21.
 */
public class CustomDialogFragment extends DialogFragment {
    public CustomDialogFragment(){}
    static private String dataString;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //set Attributes
        Dialog dialog = getDialog();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float dialogWidth = 300 * metrics.scaledDensity;
        layoutParams.width = (int)dialogWidth;
        dialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DatePickActivity parent = (DatePickActivity)getActivity();
        View view;
        switch(getTag()){
            case "register":
                view = registerCase(inflater,container,savedInstanceState,parent);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_register_dialog,container,false);
                break;
        }
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstantState){
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        return dialog;
    }

    public View registerCase(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState, DatePickActivity parent){
        View view = inflater.inflate(R.layout.fragment_register_dialog,container,false);
        TextView confirmText = (TextView)view.findViewById(R.id.confirmText);
        if(dataString == null){
            dataString = "";
        }
        dataString = parent.getAllData();
        String confirmString = "Do U want to register the date below?\n" + dataString;
        confirmText.setText(confirmString);
        Button confirmBtn = (Button)view.findViewById(R.id.confirmBtn);
        confirmBtn.setText("Yes !");
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                RegisterInformer registerInformer = RegisterInformer.getInstance();
                registerInformer.setData(dataString);
                registerInformer.informToActivity();
                if(dataString != null){
                    /**
                     * data[]
                     *      [0] -> title
                     *      [1] -> startGuide -> this is not for need X
                     *      [2] -> startDate (yyyy/MM/dd hh:mm)
                     *      [3] -> endGuide -> this is not for need X
                     *      [4] -> endDate (yyyy/MM/dd hh:mm)
                     *      [5] -> place
                     *      [6] -> description
                     */
                    String[] data = dataString.split("\n");

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    DBData dbData = realm.createObject(DBData.class);
                    dbData.setTitle(data[0]);
                    //dbData.setCreatedAt();
                    dbData.setStartDate(data[2]);
                    dbData.setEndDate(data[4]);
                    dbData.setPlace(data[5]);
                    dbData.setDescription(data[6]);
                    realm.commitTransaction();

                }
            }
        });
        return view;
    }

}
