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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import d3vel0pper.com.timelimiter.R;

/**
 * Created by HotFlush on 2016/07/14.
 */
public class ShowDetailFragment extends DialogFragment {
    private List<String> showData;

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
        textView.setText(
                "：タイトル：\n" + showData.get(0) + "\n：開始日時：\n" + showData.get(1) + "\n：終了日時：\n"
                + showData.get(2) + "\n：場所：\n" + showData.get(3) + "\n：詳細：\n" + showData.get(4)
        );
        textView.setGravity(Gravity.CENTER);
        Button button = (Button)view.findViewById(R.id.confirmBtn);
        button.setText("閉じる");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public void setShowData(List<String> list){
        showData = list;
    }

}
