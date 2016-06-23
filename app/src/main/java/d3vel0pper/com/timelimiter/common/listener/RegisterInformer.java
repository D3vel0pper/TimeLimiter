package d3vel0pper.com.timelimiter.common.listener;

/**
 * Created by D3vel0pper on 2016/06/24.
 */
public class RegisterInformer {
    private static RegisterInformer instance;
    private RegisterInformer(){
    }
    private RegisteredListener listener;
    private String data;

    public static RegisterInformer getInstance(){
        if(instance == null){
            instance = new RegisterInformer();
        }
        return instance;
    }

    public void setData(String data){
        this.data = data;
    }

    public void informToActivity(){
        listener.onRegistered(data);
    }

    public void setListener(RegisteredListener listener){
        this.listener = listener;
    }
}
