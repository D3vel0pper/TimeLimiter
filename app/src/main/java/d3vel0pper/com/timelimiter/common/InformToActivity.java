package d3vel0pper.com.timelimiter.common;

import d3vel0pper.com.timelimiter.fragment.MyListener;

/**
 * Created by HotFlush on 2016/06/23.
 */
public class InformToActivity {
    private static InformToActivity instance;
    private InformToActivity(){
    }
    private MyListener listener;
    private String data;
    public static InformToActivity getInstance(){
        if(instance == null){
            instance = new InformToActivity();
        }
        return instance;
    }

    public void setData(String data){
        this.data = data;
    }

     public void informToActivity(){
         listener.passTheDate(data);
     }

    public void setListener(MyListener listener){
        this.listener = listener;
    }


}
