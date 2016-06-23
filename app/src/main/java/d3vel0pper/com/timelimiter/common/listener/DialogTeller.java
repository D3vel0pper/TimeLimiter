package d3vel0pper.com.timelimiter.common.listener;

/**
 * Created by HotFlush on 2016/06/24.
 */
public class DialogTeller {
    private DialogTeller(){}
    private static DialogTeller instance;
    private ConfirmDialogListener listener;
    public static DialogTeller getInstance(){
        if(instance == null){
            instance = new DialogTeller();
        }
        return instance;
    }

    public void setListener(ConfirmDialogListener listener){
        this.listener = listener;
    }

    public void InformDialog(String TAG){
        listener.onConfirmDialog(TAG);
    }

}
