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
import utils.propertyGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author trafalgar
 */
public class CalendarController {

    private static String calendarPath;
    private static File calendarFile;

    static {
        calendarPath = propertyGenerator.getProperties("filePath");
        calendarFile = new File(calendarPath);
    }


    public void createCalendar() throws IOException{
        if (!calendarFile.exists()) {
            calendarFile.createNewFile();
        }
    }

    public void deleteCalendar() {
        if(calendarFile.exists()){
            calendarFile.delete();
        }
        return;
    }

    public void updateCalendar(Event event) throws IOException, ParserException {
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
