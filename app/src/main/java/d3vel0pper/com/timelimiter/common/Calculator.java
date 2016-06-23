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
     * set the gap of Start-End date
     * @param formatedStart: yyyy/mm/dd
     * @param formatedEnd: yyyy/mm/dd
     */
    public void calcDateGap(String formatedStart,String formatedEnd){
        int gap = -1;
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
        this.day = gap;
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



}
