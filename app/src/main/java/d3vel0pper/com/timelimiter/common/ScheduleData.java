package d3vel0pper.com.timelimiter.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by D3vel0pper on 2017/03/21.
 * this class managing data that passed to RealmObject
 * Singleton object
 */

public class ScheduleData {

    private ScheduleData(){
        this.modelData = new HashMap<>();
    }

    private ScheduleData instance;
    private Map<String,String> modelData;

    public ScheduleData getInstance(){
        if(this.instance == null){
            this.instance = new ScheduleData();
        }
        return this.instance;
    }

    /**
     * getter, setter
     */

    public String getTitle(){
        return this.modelData.get("title");
    }

    public String getStartDate(){
        return this.modelData.get("startDate");
    }

    public String getEndDate(){
        return this.modelData.get("endDate");
    }

    public String getPlace(){
        return this.modelData.get("place");
    }

    public String getDescription(){
        return this.modelData.get("description");
    }

    public void setTitle(String title){
        this.modelData.put("title",title);
    }

    public void setStartDate(String startDate){
        this.modelData.put("startDate",startDate);
    }

    public void setStartDate(Date startDate){
        //manage by formatwrapper
    }

    public void setEndDate(String endDate){
        this.modelData.put("endDate",endDate);
    }

    public void setEndDate(Date endDate){
        //manage by formatwrapper
    }

    public void setPlace(String place){
        this.modelData.put("place",place);
    }

    public void setDescription(String description){
        this.modelData.put("description",description);
    }

}
