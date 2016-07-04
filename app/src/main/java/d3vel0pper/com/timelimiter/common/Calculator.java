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
    private MyCalendar myCalendar;

    public Calculator(){
        year = 0;
        month = 0;
        day = 0;
        hour = 0;
        min = 0;
        myCalendar = new MyCalendar();
    }

    /**
     * Return formated calculated Gap after calcGap is called
     * @return String of formated Gap yyyy/MM/dd/ hh:mm
     */
    public String getFormatedGap(){
        return String.valueOf(year) + "/" + String.valueOf(month)
                + "/" + String.valueOf(day) + " "
                + String.valueOf(hour) + ":" + String.valueOf(min);
    }

    /**
     * Return formated calculate Gap that u given
     * @param formatedDateStart: yyyy/MM/dd/ hh:mm
     * @param formatedDateEnd yyyy/MM/dd/ hh:mm
     * @return
     */
    public String getFormatedGap(String formatedDateStart,String formatedDateEnd){
        calcGap(formatedDateStart,formatedDateEnd);
        return String.valueOf(year) + "/" + String.valueOf(month)
                + "/" + String.valueOf(day) + " "
                + String.valueOf(hour) + ":" + String.valueOf(min);
    }

    /**
     * Return DayCount Gap after calcGap or calcTimeGap is called
     * @return Integer count of days
     */
    public Integer getDayCountGap(){
        return this.day;
    }

    /**
     * Return TimeCountGap that u given
     * @param formatedDateStart
     * @param formatedDateEnd
     */
    public String getTimeCountGap(String formatedDateStart,String formatedDateEnd){
        calcGap(formatedDateStart,formatedDateEnd);
        String[] rtn = {"",""};
        int temp;
        temp = this.hour + (this.day * 24);
        if(temp < 10){
            rtn[0] = "0" + String.valueOf(temp);
        } else {
            rtn[0] = String.valueOf(temp);
        }
        if(this.min < 10){
            rtn[1] = "0" + String.valueOf(this.min);
        } else {
            rtn[1] = String.valueOf(this.min);
        }
        return rtn[0] + ":" + rtn[1];
    }
    /**
     * Return TimeCountGap after calcGap is called
     */
    public String getTimeCountGap(){
        String[] rtn = {"",""};
        int temp;
        temp = this.hour + (this.day * 24);
        if(temp < 10){
            rtn[0] = "0" + String.valueOf(temp);
        } else {
            rtn[0] = String.valueOf(temp);
        }
        if(this.min < 10){
            rtn[1] = "0" + String.valueOf(this.min);
        } else {
            rtn[1] = String.valueOf(this.min);
        }
        return rtn[0] + ":" + rtn[1];
    }

    /**
     * Calculating Gap of 2 date.
     * @param formatedDateStart
     * @param formatedDateEnd
     */
    public void calcGap(String formatedDateStart,String formatedDateEnd){
        String[] tempStart = formatedDateStart.split(" ");
        String[] tempEnd = formatedDateEnd.split(" ");
        calcTimeGap(tempStart[1],tempEnd[1]);
        calcDateGap(tempStart[0],tempEnd[0]);
    }

    /**
     * set the gap of Start-End date
     * @param formatedStart: yyyy/mm/dd
     * @param formatedEnd: yyyy/mm/dd
     */
    public void calcDateGap(String formatedStart,String formatedEnd){
        int gap = 0;
        String[] start = formatedStart.split("/",0);
        String[] end = formatedEnd.split("/",0);
        //Set the start date to calendar
        myCalendar.setDateWithoutTime(formatedStart);
        int endMonth = Integer.parseInt(end[1]);
        int endDay = Integer.parseInt(end[2]);
        //loop while myCalendar.month is same as endMonth
        while((myCalendar.getMonth() != endMonth) && (myCalendar.getMonth() < endMonth)){
            int temp = myCalendar.getLastDay() - myCalendar.getDay();
            myCalendar.addDays(temp);
            gap += temp;
            if(myCalendar.isLastDay()){
                myCalendar.incrementDay();
                gap++;
            }
        }
        if(myCalendar.getDay() != endDay){
            int temp = endDay - myCalendar.getDay();
            myCalendar.addDays(temp);
            gap += temp;
        }
        //set Gap day
        this.day = gap;
    }

    private int calcYearGap(String startYear,String endYear) {
        return Integer.parseInt(endYear) - Integer.parseInt(startYear);
    }
    private int calcMonthGap(String startMonth,String endMonth){
        int result = Integer.parseInt(endMonth) - Integer.parseInt(startMonth);
        if(result <= 0){
            this.year--;
            result += 12;
        }
        return result;
    }

    /**
     * set the gap of Start-End time
     * @param formatedStart: hh:mm
     * @param formatedEnd: hh:mm
     */
    public void calcTimeGap(String formatedStart,String formatedEnd){
        String start[] = formatedStart.split(":",0);
        String end[] = formatedEnd.split(":",0);
        this.min = calcMinGap(start[1],end[1]);
        this.hour = calcHourGap(start[0],end[0]);
    }

    //This could not be minus
    private int calcHourGap(String startHour,String endHour){
        int result = Integer.parseInt(endHour) - Integer.parseInt(startHour);
        if(result < 0){
            this.day--;
            result += 24;
        }
        return result + this.hour;
    }
    private int calcMinGap(String startMin,String endMin){
        int result = Integer.parseInt(endMin) - Integer.parseInt(startMin);
        if(result < 0){
            this.hour--;
            result += 60;
        }
        return result + this.min;
    }

    /**
     * plz Call after calcGap()
     * Return Gap Counted By hour.
     * @return: calculated Gap counted by hour (min will be omitted)
     */
    public int getAllGapInHour(){
        int hour = 0;
        for(int i = 0;this.day > i;i++){
            hour += 24;
        }
        return hour + this.hour;
    }

}
