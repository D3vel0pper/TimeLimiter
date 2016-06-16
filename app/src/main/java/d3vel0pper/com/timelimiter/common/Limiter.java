package d3vel0pper.com.timelimiter.common;

/**
 * Created by D3vel0pper on 2016/06/16.
 */
public class Limiter {
    private Integer id;
    /**
     * C = Created
     * S = Start
     * E = end
     */
    //Data of schedule created
    private String yearC;
    private String monthC;
    private String dayC;
    private String hourC;
    private String minC;
    //Data Start of scheduled thing
    private String yearS;
    private String monthS;
    private String dayS;
    private String hourS;
    private String minS;
    //Data End of scheduled thing
    private String yearE;
    private String monthE;
    private String dayE;
    private String hourE;
    private String minE;

    private String title;
    private String detail;
    private String place;
    //parameter of notification is enable or not
    private boolean notifible;

    /**
     * Getter and Setter
     */
    //Setter
    public void setId(Integer id){
        this.id = id;
    }
    /**
     * C
     */
    public void setYearC(String year){
        yearC = year;
    }
    public void setMonthC(String month){
        monthC = month;
    }
    public void setDayC(String day){
        dayC = day;
    }
    public void setHourC(String hour){
        hourC = hour;
    }
    public void setMinC(String min){
        minC = min;
    }
    /**
     * S
     */
    public void setYearS(String year){
        yearS = year;
    }
    public void setMonthS(String month){
        monthS = month;
    }
    public void setDayS(String day){
        dayS = day;
    }
    public void setHourS(String hour){
        hourS = hour;
    }
    public void setMinS(String min){
        minS = min;
    }
    /**
     * E
     */
    public void setYearE(String year){
        yearE = year;
    }
    public void setMonthE(String month){
        monthE = month;
    }
    public void setDayE(String day){
        dayE = day;
    }
    public void setHourE(String hour){
        hourE = hour;
    }
    public void setMinE(String min){
        minE = min;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setDetail(String detail){
        this.detail = detail;
    }
    public void setPlace(String place){
        this.place = place;
    }
    public void setNotifible(Boolean isNotifible){
        notifible = isNotifible;
    }

    //Getter
    public Integer getId(){
        return id;
    }
    public String getYearC(){
        return yearC;
    }
    public String getMonthC(){
        return monthC;
    }
    public String getDayC(){
        return dayC;
    }
    public String getHourC(){
        return hourC;
    }
    public String getMinC(){
        return minC;
    }

    public String getYearS(){
        return yearS;
    }
    public String getMonthS(){
        return monthS;
    }
    public String getDayS(){
        return dayS;
    }
    public String getHourS(){
        return hourS;
    }
    public String getMinS(){
        return minS;
    }

    public String getYearE(){
        return yearE;
    }
    public String getMonthE(){
        return monthE;
    }
    public String getDayE(){
        return dayE;
    }
    public String getHourE(){
        return hourE;
    }
    public String getMinE(){
        return minE;
    }

    public String getTitle(){
        return title;
    }
    public String getDetail(){
        return detail;
    }
    public String getPlace(){
        return place;
    }

    public Boolean getNotifible(){
        return notifible;
    }
}
