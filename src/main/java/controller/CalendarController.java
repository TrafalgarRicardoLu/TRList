package controller;

import entity.Event;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.*;
import utils.UidGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.GregorianCalendar;


/**
 * @author trafalgar
 */
public class CalendarController {

    public void createCalendar() throws IOException, ParserException {
        String calendarPath = "/home/trafalgar/IdeaProjects/TRList/src/test/java/CalendarTest/Test.ics";
        File calendarFile = new File(calendarPath);
        if (!calendarFile.exists()) {
            calendarFile.createNewFile();
        }
    }

    public void deleteCalendar() {

    }

    public void updateCalendar(Event event) throws IOException, ParserException {
        String calendarPath = "/home/trafalgar/IdeaProjects/TRList/src/test/java/CalendarTest/Test.ics";
        File calendarFile = new File(calendarPath);

        // Create Calendar
        FileInputStream fin = new FileInputStream(calendarPath);
        CalendarBuilder builder = new CalendarBuilder();
        net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);

        if (calendarFile.exists() && calendarFile.length() == 0) {
            calendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
            calendar.getProperties().add(CalScale.GREGORIAN);
            calendar.getProperties().add(Version.VERSION_2_0);
        }

        calendar.getComponents().add(event.getActivity());

        FileOutputStream fileOutputStream = new FileOutputStream(calendarFile);
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, fileOutputStream);
    }

    public void readCalendar() {

    }
}
