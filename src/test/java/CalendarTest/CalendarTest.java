package CalendarTest;

import controller.CalendarController;
import model.Event;
import net.fortuna.ical4j.data.ParserException;
import org.junit.Test;

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
