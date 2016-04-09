package d3vel0pper.com.timelimiter.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by taka-dhu on 2016/04/09.
 */
public class MyCalendar {
    public MyCalendar(){
    }
    //private members
    /**
     * day_code
     *  Sun = 0, Mon = 1, Tue = 2, Wed = 3, Thr = 4, Fri = 5, Sat = 6
     * year = year(0-INT_MAX)
     * month = month(1-12)
     * day = day(1-31)
     * hour = hour(0-23)
     * min = min(0-59)
     * sec = sec(0-60)
     */
    private Integer day_code = 0;
    private Integer year = 0;
    private Integer month = 1;
    private Integer day = 1;
    private Integer hour = 0;
    private Integer min = 0;
    private Integer sec = 0;

    //      methods

    /**
     ** @return is not leap year -> false
     *         is leap year -> true
     */
    private boolean isLeapyear(){
        if(this.year % 4 == 0){
            return true;
        }
        return false;
    }

    //public members
    public void setDateFromFormat(String formatedDate){
        /**
         * Format is "yyyy/MM/dd HH:mm:ss"
         */
        formatedDate.substring(0,4);
        
        if(){

        }

    }

    public void setDay_code(int day_code){
        if(day_code < 0 || day_code > 6){
            this.day_code = 0;
        } else{
            this.day_code = day_code;
        }
    }

    static public String getTest(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }
}
