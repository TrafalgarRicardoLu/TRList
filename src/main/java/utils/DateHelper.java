package utils;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;

import java.text.ParseException;
import java.util.GregorianCalendar;

/**
 * @author trafalgar
 */
public class DateHelper {

    private static MONTH[] months = MONTH.values();

    public static String getDate(String originDate) throws ParseException {
        String year = originDate.substring(0, 4);

        Integer monthIndex = Integer.parseInt(originDate.substring(4, 6)) - 1;
        String monthName = null;
        for (MONTH month : months) {
            if (month.getMonthIndex() == monthIndex) {
                monthName = month.getMonthName();
            }
        }

        int day = Integer.parseInt(originDate.substring(6, 8));
        int hour = Integer.parseInt(originDate.substring(9, 11));
        String minute = originDate.substring(11, 13);

        String date = year + "-" + monthName + "-" + day + " " + hour + ":" + minute;
        return date;
    }

    public static DateTime getCalDate(String originDate) {
        String[] dates = originDate.split("-|:| ");

        for(String i:dates){
            System.out.println(i);
        }

        int monthIndex = 0;
        for (MONTH month : months) {
            if (month.getMonthName().equals(dates[1])) {
                monthIndex = month.getMonthIndex();
                break;
            }
        }

        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("America/Mexico_City");

        java.util.Calendar date = new GregorianCalendar();
        date.setTimeZone(timezone);
        date.set(java.util.Calendar.MONTH, monthIndex);
        date.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));
        date.set(java.util.Calendar.YEAR, Integer.parseInt(dates[0]));
        date.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(dates[3]));
        date.set(java.util.Calendar.MINUTE, Integer.parseInt(dates[4]));
        date.set(java.util.Calendar.SECOND, 0);

        return new DateTime(date.getTime());
    }
}
