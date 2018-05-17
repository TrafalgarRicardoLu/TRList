package controller;

import model.Event;
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


    public void createCalendar() throws IOException {
        if (!calendarFile.exists()) {
            calendarFile.createNewFile();
        }
    }

    public void deleteCalendar() {
        if (calendarFile.exists()) {
            calendarFile.delete();
        }
    }

    public void updateCalendar(Event event) throws IOException, ParserException {

        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION, true);

        FileInputStream fin = new FileInputStream(calendarPath);
        CalendarBuilder builder = new CalendarBuilder();
        net.fortuna.ical4j.model.Calendar calendar =builder.build(fin);

        if (calendarFile.exists() && calendarFile.length() == 0) {
            calendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
            calendar.getProperties().add(CalScale.GREGORIAN);
            calendar.getProperties().add(Version.VERSION_2_0);
        }

        IndexedComponentList indexedEvents = new IndexedComponentList(
                calendar.getComponents(Component.VEVENT), Property.UID);

        VEvent activity = event.getActivity();
        Component existing = indexedEvents.getComponent(event.getActivity().getUid().getValue());
        if (existing == null) {
            System.out.println("not exist");
            calendar.getComponents().add(activity);
        } else {
            System.out.println("exist");
            calendar.getComponents().remove(existing);
            calendar.getComponents().add(activity);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(calendarFile);
        CalendarOutputter outputter = new CalendarOutputter();
        try {
            outputter.output(calendar, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }finally {
            fileOutputStream.close();
        }
    }

    public List<VEvent> readCalendar() throws IOException, ParserException {

        FileInputStream fin = new FileInputStream(calendarPath);
        CalendarBuilder builder = new CalendarBuilder();
        net.fortuna.ical4j.model.Calendar myCalendar = builder.build(fin);

        List<VEvent> vEvents = new LinkedList<>();
        for (Iterator i = myCalendar.getComponents(Component.VEVENT).iterator(); i.hasNext(); ) {
            VEvent event = (VEvent) i.next();
            vEvents.add(event);
        }

        return vEvents;
    }


}
