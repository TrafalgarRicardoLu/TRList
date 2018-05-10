package CalendarTest;

import controller.CalendarController;
import entity.Event;
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
import java.util.UUID;


public class CalendarTest {


    @Test
    public void createNewCalendar() throws IOException, ParserException {
        CalendarController calendarController = new CalendarController();
        calendarController.createCalendar();
        System.out.println(UUID.randomUUID());
    }

    @Test
    public void deleteCalendar(){
        CalendarController calendarController = new CalendarController();
        calendarController.deleteCalendar();
    }

    @Test
    public void addInfoToCalendar() throws IOException, ParserException {
        CalendarController calendarController = new CalendarController();
        calendarController.updateCalendar(new Event());
    }
}
