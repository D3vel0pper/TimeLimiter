package d3vel0pper.com.timelimiter.common;

import io.realm.RealmObject;

/**
 * Created by D3vel0pper on 2016/06/29.
 */
public class ConfigData /*extends RealmObject*/ {

    public ConfigData(){
        maxHourPerDay = Integer.MAX_VALUE;
        maxHourPerWeek = Integer.MAX_VALUE;
        maxHourPerMonth = Integer.MAX_VALUE;
    }
    //Limit Hour per a Day
    private int maxHourPerDay;
    //Limit Hour Per Week
    private int maxHourPerWeek;
    //Limit Hour Per Month
    private int maxHourPerMonth;
    /**
     * mode for showing percentage to user
     *      0 (default) -> Per Day
     *      1 -> Per Week
     *      2 -> Per Month
     */
    private int showingMode;


    public void setMaxHourPerDay(int maxHourPerDay){
        this.maxHourPerDay = maxHourPerDay;
    }
    public int getMaxHourPerDay(){
        return this.maxHourPerDay;
    }
    public void setMaxHourPerWeek(int maxHourPerWeek){
        this.maxHourPerWeek = maxHourPerWeek;
    }
    public int getMaxHourPerWeek(){
        return this.maxHourPerWeek;
    }
    public void setMaxHourPerMonth(int maxHourPerMonth){
        this.maxHourPerMonth = maxHourPerMonth;
    }
    public int getMaxHourPerMonth(){
        return this.maxHourPerMonth;
    }

    /**
     * Set Showing mode of percentage per what
     * @param mode: 0 -> day(default) 1 -> week 2 -> month
     */
    public void setShowingMode(int mode){
        this.showingMode = mode;
    }
    public int getShowingMode(){
        return this.showingMode;
    }

}
