package CalendarTest;

import controller.CalendarController;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class CalendarTest {


    @Test
    public void createNewCalendar() throws IOException, ParserException {
//        String in = "/home/trafalgar/IdeaProjects/TRList/src/test/java/CalendarTest/test.ics";
//
//        FileInputStream fin = new FileInputStream(in);
//        CalendarBuilder builder = new CalendarBuilder();
//        Calendar calendar = builder.build(fin);
//
////
////
//        String out = "/home/trafalgar/IdeaProjects/TRList/src/test/java/CalendarTest/testOut.ics";
//        FileOutputStream fout = new FileOutputStream(out);
//        CalendarOutputter outputter = new CalendarOutputter();
//        outputter.output(calendar, fout);
        CalendarController calendarController = new CalendarController();
        calendarController.createCalendar();
    }

    @Test
    public void addInfoToCalendar(){
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        System.out.println(calendar);
    }
}
