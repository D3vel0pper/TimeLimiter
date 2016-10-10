package d3vel0pper.com.timelimiter.common;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by D3vel0pper on 2016/10/09.
 */
public class FormatWrapper {
    public FormatWrapper(){
        sdf = new SimpleDateFormat("",Locale.JAPAN);
    }

    private SimpleDateFormat sdf;
    private final String DATE_WITH_TIME_FORMAT = "yyyy/MM/dd HH:mm";
    private final String DATE_FORMAT = "yyyy/MM/dd";

    public Date getFormatedDate(String targetDateString){
        sdf.applyPattern(DATE_FORMAT);
        Date date = new Date();
        try {
             date = sdf.parse(targetDateString);
        } catch(ParseException e){
            Log.e("PE","Parse has not finished correctly");
        }
        return date;
    }

    public Date getFormatedDateWithTime(String targetDateString){
        sdf.applyPattern(DATE_WITH_TIME_FORMAT);
        Date date = new Date();
        try {
            date = sdf.parse(targetDateString);
        } catch(ParseException e){
            Log.e("PE","Parse has not finished correctly");
        }
        return date;
    }

    public String getFormatedStringDate(Date targetDate){
        sdf.applyPattern(DATE_FORMAT);
        return sdf.format(targetDate);
    }

    public String getFormatedStringDateWithTime(Date targetDate){
        sdf.applyPattern(DATE_WITH_TIME_FORMAT);
        return sdf.format(targetDate);
    }

}
