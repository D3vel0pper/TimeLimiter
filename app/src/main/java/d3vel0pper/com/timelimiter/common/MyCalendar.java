package d3vel0pper.com.timelimiter.common;

//import android.util.Log;

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
     *  Mon = 0, Tue = 1, Wed = 2, Thr = 3, Fri = 4, Sat = 5, Sun = 6
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
    private String sTemp[];
    private String splited[];
    private int iTemp = 0;
    //day codes
    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;

    //      methods

    /**
     ** @return is not leap year -> false
     *         is leap year -> true
     */
    private boolean isLeapyear(){
        if(this.year % 400 == 0){
            return true;
        } else if(this.year % 100 == 0){
            return false;
        } else if(this.year % 4 == 0){
            return true;
        }
        return false;
    }

    private void setDay_code(){

        if(day_code < 0 || day_code > 6){
            this.day_code = 0;
        } else{
            /**
             * generate day_code
             */
            int yTemp = this.year;
            int mTemp = this.month;

            if(this.month == 1 || this.month == 2){
                yTemp -= 1;
                mTemp += 13;
            } else {
                mTemp += 1;
            }

            int temp = (int) (((yTemp * 365.25) + (mTemp * 30.6) + (yTemp / 400) + this.day) - (yTemp / 100) -429) ;
            this.day_code = temp - ((temp/7) * 7);

        }
    }

    //public members

    /**
     *
     * @param formatedDate is formated in "yyyy/MM/dd HH:mm:ss"
     * @return true -> Data set correctly
     */
    public boolean setDateFromFormat(String formatedDate){

        boolean flag = true;
        /**
         * set year
         */
        if(!(setYear(formatedDate))){
            this.year = 1970;
            flag = false;
        }

        /**
         * set month
         */
        if(!(setMonth(formatedDate))){
            this.month = 1;
            flag = false;
        }

        /**
         * set day
         */
        if(!(setDay(formatedDate))){
            this.day = 1;
            flag = false;
        }
        /**
         * set day_code
         */
        setDay_code();

        return flag;
    }

    /**
     * @param date is formated in "yyyy/MM/dd"
     * @return true -> Data set correctly
     */
    public boolean setDateWithoutTime(String date){

        boolean flag = true;
        /**
         * set year
         */
        if(!(setYear(date))){
            this.year = 1970;
            flag = false;
        }

        /**
         * set month
         */
        if(!(setMonth(date))){
            this.month = 1;
            flag = false;
        }

        /**
         * set day
         */
        if(!(setDay(date))){
            this.day = 1;
            flag = false;
        }
        return flag;
    }

    private String[] temporary;
    /**
     * for testing
     */
    private boolean setYear(String formatedDate){

        sTemp = formatedDate.split("/");

        try{
            iTemp = Integer.parseInt(sTemp[0]);
        } catch(NumberFormatException e){
            System.out.println("DataFormatError @ year");
//            Log.d("FormatError",e.toString());
            return false;
        }
        if(iTemp > 9999 || iTemp < 0){
            System.out.println("DataRangeError @ year");
//            Log.d("DataRangeError","DataRangeError @ year");
            return false;
        } else {
            this.year = iTemp;
        }
        return true;
    }

    public int getYear(){
        return this.year;
    }

    private boolean setMonth(String formatedDate){
        //sTemp = formatedDate.substring(5,6);
        try{
            iTemp = Integer.parseInt(sTemp[1]);
        }catch(NumberFormatException e){
            System.out.println("DataFormatError @ month");
            return false;
        }
        if(iTemp > 12 || iTemp < 1){
            System.out.println("DataFormatError @ month");
            return false;
        } else {
            this.month = iTemp;
        }
        return true;
    }

    public int getMonth(){
        return this.month;
    }

    private boolean setDay(String formatedDate){

        try{
            iTemp = Integer.parseInt(sTemp[2]);
        }catch(NumberFormatException e){
            System.out.println("DataFormatError @ day");
//                Log.d("DataFormatError","DataFormatError @ day")
            return false;
        }
        if(iTemp < 1){
            System.out.println("DataRangeError @ day");
//            Log.d("DataRangeError","DataRangeError @ day");
            return false;
        }
        if(iTemp > getLastDay()){
            System.out.println("DataRangeError @ day");
//            Log.d("DataRangeError","DataRangeError @ day");
            return false;
        } else {
            this.day = iTemp;
        }
        return true;

    }

    public int getDay(){
        return this.day;
    }

    public int getDay_Code(){
        return this.day_code;
    }

    /**
     *
     * @return Last day of that month
     * if no match in the month, this will return 0
     */
    public int getLastDay(){
        switch(this.month){
            case 1:
                return 31;
            case 2:
                if(isLeapyear()){
                    return 29;
                }
                return 28;
            case 3:
                return 31;
            case 4:
                return 29;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        return 0;
    }


    /**
     * @return The Last Day of month
     */
    public int getLastDayOf(int month){
        switch(this.month){
            case 1:
                return 31;
            case 2:
                if(isLeapyear()){
                    return 29;
                }
                return 28;
            case 3:
                return 31;
            case 4:
                return 29;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        return 0;
    }

    /**
     * check the set date is last day or not
     * @return true -> yes it is the last day of month
     */
    public boolean isLastDay(){
        if(this.day == getLastDay()){
            return true;
        } else{
            return false;
        }
    }

    public void incrementYear(){
        this.year++;
    }

    public void incrementMonth(){
        if(this.month == 12){
            incrementYear();
            this.month = 1;
        } else {
            this.month++;
        }
    }
    /**
     * Increment the day with confirm is LastDay.
     */
    public void incrementDay(){
        if(isLastDay()){
            incrementMonth();
            this.day = 1;
        }else {
            this.day++;
        }
    }

    public void decrementYear(){
        this.year--;
    }

    public void decrementMonth(){
        if(this.month == 1){
            decrementYear();
            this.month = 12;
        } else {
            this.month--;
        }
    }

    public void decrementDay(){
        if(this.day == 1){
            decrementMonth();
            this.day = getLastDay();
        } else {
            this.day--;
        }
    }

    public void addDays(int additionalDay){
        while(additionalDay > 0){
            if(isLastDay()){
                incrementDay();
                additionalDay--;
            }
            if((getLastDay() - (additionalDay + this.day)) > 0){
                this.day += additionalDay;
                additionalDay = 0;
            } else {
                //get gap to LastDay
                int gap = getLastDay() - this.day;
                additionalDay -= gap;
                //add to day gap
                this.day += gap;
            }
        }

    }

    public String getFormatedDate(){
        String date;
        if(getYear() < 10){
            date = "000" + String.valueOf(this.year);
        } else if(getYear() < 100){
            date = "00" + String.valueOf(this.year);
        } else if(getYear() < 1000){
            date = "0" + String.valueOf(this.year);
        } else {
            date = String.valueOf(this.year);
        }
        date += "/";
        if(getMonth() < 10){
            date = date + "0" + String.valueOf(this.month);
        } else {
            date += String.valueOf((this.month));
        }
        date += "/";
        if(getDay() < 10){
            date = date + "0" + String.valueOf(this.day);
        } else {
            date += String.valueOf(this.day);
        }
        return date;
    }



//        static public String getTest(){
//            final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            final Date date = new Date(System.currentTimeMillis());
//            return df.format(date);
//        }
}
