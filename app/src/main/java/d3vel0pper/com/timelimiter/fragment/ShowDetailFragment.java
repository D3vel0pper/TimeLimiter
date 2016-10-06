package d3vel0pper.com.timelimiter.fragment;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import d3vel0pper.com.timelimiter.R;

/**
 * Created by D3vel0pper on 2016/07/14.
 */
public class ShowDetailFragment extends DialogFragment {
    private Map<String, String> dataMap;

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
        View view = inflater.inflate(R.layout.fragment_register_dialog,container,false);
        TextView textView = (TextView)view.findViewById(R.id.confirmText);
        //Use Map
        textView.setText(
                "：タイトル：\n" + dataMap.get("title") + "\n：開始日時：\n" + dataMap.get("startDate") + "\n：終了日時：\n"
                        + dataMap.get("endDate") + "\n：場所：\n" + dataMap.get("place") + "\n：詳細：\n" + dataMap.get("description")
        );
        textView.setGravity(Gravity.CENTER);
        Button button = (Button)view.findViewById(R.id.confirmBtn);
        button.setText(R.string.close_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public void setDataMap(Map<String, String> map){
        dataMap = map;
    }

}
