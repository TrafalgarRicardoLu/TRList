package controller;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.IndexedComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.CompatibilityHints;
import utils.conf.ConfigHelper;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * @author trafalgar
 */
public class CalendarController {

    private static final String calendarPath = ConfigHelper.getCalendarPath();
    private static File calendarFile;

    static {
        calendarFile = new File(calendarPath);
    }

    /**
     * create calendar file if not exist
     * @throws IOException
     */
    public void createCalendar() throws IOException {
        if (!calendarFile.exists()) {
            calendarFile.createNewFile();
        }
    }

    /**
     * delete calendar file
     */
    public void deleteCalendar() {
        if (calendarFile.exists()) {
            calendarFile.delete();
        }
    }

    public void updateCalendar(VEvent vEvent) throws IOException, ParserException {

        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION, true);

        FileInputStream fin = new FileInputStream(calendarPath);
        CalendarBuilder builder = new CalendarBuilder();
        net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);

        if (calendarFile.exists() && calendarFile.length() == 0) {
            calendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
            calendar.getProperties().add(CalScale.GREGORIAN);
            calendar.getProperties().add(Version.VERSION_2_0);
        }

        IndexedComponentList indexedEvents = new IndexedComponentList(
                calendar.getComponents(Component.VEVENT), Property.UID);

        Component existing = indexedEvents.getComponent(vEvent.getUid().getValue());
        if (existing == null) {
            calendar.getComponents().add(vEvent);
        } else {
            calendar.getComponents().remove(existing);
            calendar.getComponents().add(vEvent);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(calendarFile);
        CalendarOutputter outputter = new CalendarOutputter();
        try {
            outputter.output(calendar, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            fileOutputStream.close();
        }
    }

    /**
     * get all event from calendar
     * @return List<VEvent>
     * @throws IOException
     * @throws ParserException
     */
    public List<VEvent> readCalendar() throws IOException, ParserException {
        FileInputStream fin = new FileInputStream(calendarPath);
        CalendarBuilder builder = new CalendarBuilder();
        net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);

        List<VEvent> vEvents = new LinkedList<>();
        for (Iterator i = calendar.getComponents(Component.VEVENT).iterator(); i.hasNext(); ) {
            VEvent event = (VEvent) i.next();
            vEvents.add(event);
        }

        return vEvents;
    }


}
