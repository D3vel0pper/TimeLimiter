package d3vel0pper.com.timelimiter.common;

/**
 * Created by D3vel0pper on 2016/06/16.
 * This class work as calculator of date or limited time
 * This class has to have the methods that calculating limit.
 */
public class Calculator {
    //These are gap given
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    public Calculator(){
        year = 0;
        month = 0;
        day = 0;
        hour = 0;
        min = 0;
    }

    /**
     * set the gap of Start-End date
     * @param formatedStart: yyyy/mm/dd
     * @param formatedEnd: yyyy/mm/dd
     */
    public void calcDateGap(String formatedStart,String formatedEnd){

    }

    private int calcYearGap() {

        return 0;
    }
    private int calcMonthGap(){

        return 0;
    }
    private int calcDayGap() {

        return 0;
    }
    /**
     * set the gap of Start-End time
     * @param formatedStart: hh:mm
     * @param formatedEnd: hh:mm
     */
    public void calcTimeGap(String formatedStart,String formatedEnd){
        String start[] = formatedStart.split(":",0);
        String end[] = formatedEnd.split(":",0);
        this.hour = calcHourGap(start[0],end[0]);
        this.min = calcMinGap(start[1],end[1]);
    }

    //This could not be minus
    private int calcHourGap(String startHour,String endHour){
        int result = Integer.parseInt(endHour) - Integer.parseInt(startHour);
        return result;
    }
    private int calcMinGap(String startMin,String endMin){
        int result = Integer.parseInt(endMin) - Integer.parseInt(startMin);
        if(result < 0){
            this.hour--;
            result += 60;
        }
        return result;
    }



}
