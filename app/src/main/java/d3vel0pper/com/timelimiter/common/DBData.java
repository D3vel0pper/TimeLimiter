package d3vel0pper.com.timelimiter.common;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by D3vel0pper on 2016/06/24.
 */
public class DBData extends RealmObject {
    //yyyy/MM/dd hh:mm
    private String createdAt;
    private String startDate;
    private String endDate;
    private String startDay;
    private String endDay;
    //private String startWeekOfMonth;
    private String title;
    private String place;
    private String description;
    private boolean notifable;
    private boolean isRepeatable;
    private String month;


    private int id;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public void setNotifable(Boolean notifable){
        this.notifable = notifable;
    }
    public boolean getNotifable(){
        return this.notifable;
    }
    /**
     *
     * @param formatedDate: yyyy/MM/dd hh:mm
     */
    public void setCreatedAt(String formatedDate){
        this.createdAt = formatedDate;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }

    public void setStartDate(String formatedDate){
        this.startDate = formatedDate;
    }
    public String getStartDate(){
        return this.startDate;
    }

    public void setEndDate(String formatedDate){
        this.endDate = formatedDate;
    }
    public String getEndDate(){
        return this.endDate;
    }

    public void setStartDay(String startDay){
        this.startDay = startDay;
    }
    public String getStartDay(){
        return this.startDay;
    }

    public void setEndDay(String endDay){
        this.endDay = endDay;
    }
    public String getEndDay(){
        return this.endDay;
    }

//    public void setStartWeekOfMonth(String startWeekOfMonth){
//        this.startWeekOfMonth = startWeekOfMonth;
//    }
//    public String getStartWeekOfMonth(){
//        return this.startWeekOfMonth;
//    }

    public void setMonth(String month){
        this.month = month;
    }
    public String getMonth(){
        return this.month;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setPlace(String place){
        this.place = place;
    }
    public String getPlace() {
        return this.place;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setIsRepeatable(boolean repeatable){
        this.isRepeatable = repeatable;
    }
    public boolean getIsRepeatable(){
        return this.isRepeatable;
    }

}
