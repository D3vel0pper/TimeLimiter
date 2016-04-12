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
    //temporally String
    private String sTemp = "";
    private int iTemp = 0;
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

    /**
     *
     * @return true if lastDay is 31
     *         false other
     */
    private boolean isMonthsLastDay(){
        if(this.month.equals(2)){
            return false;
        }
        return true;
    }

    //public members

    /**
     *
     * @param formatedDate
     * @return true -> Data set correctly
     */
    public boolean setDateFromFormat(String formatedDate){
        /**
         * Format is "yyyy/MM/dd HH:mm:ss"
         */
        sTemp = formatedDate.substring(0,3);
        try{
            iTemp = Integer.parseInt(sTemp);
        } catch(NumberFormatException e){
            System.out.println("DataFormatError @ year");
            return false;
        }
        if(iTemp > 9999 || iTemp < 0){
            System.out.println("DataRangeError @ year");
            return false;
        } else {
            this.year = iTemp;
        }

        sTemp = formatedDate.substring(5,6);
        try{
            iTemp = Integer.parseInt(sTemp);
        }catch(NumberFormatException e){
            System.out.println("DataFormatError @ month");
            return false;
        }
        if(iTemp > 12 || iTemp < 0){
            System.out.println("DataFormatError @ month");
            return false;
        } else {
            this.month = iTemp;
        }

        sTemp = formatedDate.substring(8,9);
        try{
            iTemp = Integer.parseInt(sTemp);
        }catch(NumberFormatException e){
            System.out.println("DataFormatError @ day");
            return false;
        }
        if(iTemp < 1){
            System.out.println("DataRangeError @ day");
            return false;
        }
        if(this.month.equals(2)){
            if (isLeapyear() &&  iTemp > 29) {
                System.out.println("DataRangeError @ day");
                return false;
            } else if(iTemp > 28){
                System.out.println("DataRangeError @ day");
                return false;
            }
        } else if(this.month.equals(4) || this.month.equals(6) || this.month.equals(9) || this.month.equals(11)){
            if(iTemp > 30){
                System.out.println("DataRangeError @ day");
                return false;
            }
        } else {
            this.day = iTemp;
        }


        return true;
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
