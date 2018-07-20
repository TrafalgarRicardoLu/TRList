package utils;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;

import java.util.GregorianCalendar;

/**
 * @author trafalgar
 */
public class DateHelper {

    private static MONTH[] months = MONTH.values();

    /**
     * read date in cal form and return it in "YYYY-MM-DD HH:SS"
     * @param originDate
     * @return
     */
    public static String getDate(String originDate){
        String year = originDate.substring(0, 4);

        Integer monthIndex = Integer.parseInt(originDate.substring(4, 6));
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

    /**
     * read date in "YYYY-MM-DD HH:SS" and return it in cal form
     * @param originDate
     * @return
     */
    public static DateTime getCalDate(String originDate) {
        String[] dates = originDate.split("-|:| ");

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
        date.set(java.util.Calendar.MONTH, monthIndex-1);
        date.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2])-1);
        date.set(java.util.Calendar.YEAR, Integer.parseInt(dates[0]));
        date.set(java.util.Calendar.HOUR,Integer.parseInt(dates[3])-1);
        date.set(java.util.Calendar.MINUTE, Integer.parseInt(dates[4]));
        date.set(java.util.Calendar.SECOND, 0);

        return new DateTime(date.getTime());
    }
}
