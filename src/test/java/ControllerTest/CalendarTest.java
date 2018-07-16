package ControllerTest;

import controller.CalendarController;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.CompatibilityHints;
import org.junit.Test;
import utils.conf.ConfigHelper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.UUID;

public class CalendarTest {


    @Test
    public void testCreateNewCalendar() throws IOException, ParserException {
        CalendarController CalendarController = new CalendarController();
        CalendarController.createCalendar();
        System.out.println(UUID.randomUUID());
    }

    @Test
    public void testDeleteCalendar(){
        CalendarController CalendarController = new CalendarController();
        CalendarController.deleteCalendar();
    }

    @Test
    public void testUpdateCalendar() throws IOException, ParserException, URISyntaxException, ParseException {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION, true);
        CalendarController CalendarController = new CalendarController();
        VEvent vEvent = new VEvent();
        vEvent.getProperties().add(new Organizer("TRL"));
        vEvent.getProperties().add(new Uid("todoist16550857i2606042458udccb5ec2b80c4c15ad80bb5cf550b298@google.com"));
        vEvent.getProperties().add(new DtStart("19700101T000000"));
        vEvent.getProperties().add(new DtEnd("19700101T000001"));
        CalendarController.updateCalendar(vEvent);
    }

    @Test
    public void testReadCalendar() throws IOException, ParserException {
        CalendarController CalendarController = new CalendarController();
        CalendarController.readCalendar();
    }

    @Test
    public void testCalendarExist(){
         File calendarFile = new File(ConfigHelper.getCalendarPath());
        if (!calendarFile.exists() || calendarFile.length() == 0) {
            System.out.println(true);
        }
    }
}
