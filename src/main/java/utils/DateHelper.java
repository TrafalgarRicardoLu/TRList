package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateHelper {

    static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static String getDate(String originDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

        String year = originDate.substring(0,4);
        String month = months[Integer.parseInt(originDate.substring(4,6))-1];
        int day = Integer.parseInt(originDate.substring(6,8));
        int hour = Integer.parseInt(originDate.substring(9,11));
        String minute = originDate.substring(11,13);

        String date = year + "-" + month + "-" + day + "  " + hour + ":" + minute;
        return date;
    }
}
