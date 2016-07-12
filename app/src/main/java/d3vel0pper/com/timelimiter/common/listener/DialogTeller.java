package d3vel0pper.com.timelimiter.common.listener;

/**
 * Created by D3vel0pper on 2016/06/24.
 */
public class DialogTeller {
    private DialogTeller(){}
    //static single instance
    private static DialogTeller instance;
    //interface of listener
    private ConfirmDialogListener listener;
    //when u use this listener, get this instance
    public static DialogTeller getInstance(){
        if(instance == null){
            instance = new DialogTeller();
        }
        return instance;
    }

    //after get instance, call this to set up listener
    public void setListener(ConfirmDialogListener listener){
        this.listener = listener;
    }

    //use this when u wanna inform to other
    public void InformDialog(String TAG){
        listener.onConfirmDialog(TAG);
    }

}
