package model;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.IndexedComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import org.dom4j.DocumentException;
import utils.conf.ConfigHelper;
import controller.xmlController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author trafalgar
 */
public class Maps {

    private static volatile Maps maps = null;

    private static HashMap<String, List<VEvent>> labelMap = new HashMap<>();

    private static HashMap<String, List<VEvent>> priorityMap = new HashMap<>();

    private static HashMap<String, List<VEvent>> projectMap = new HashMap<>();

    /**
     * get Uids of event for each filter names from xmlController
     * then get event by Uid from calendarController
     * put filter name and related event into map
     *
     * Singleton Pattern
     *
     * @throws DocumentException
     * @throws IOException
     * @throws ParserException
     */
    private Maps() throws DocumentException, IOException, ParserException {
        List<String> labelFilter = xmlController.getFilterNamesByMenuName("label");
        List<String> projectFilter = xmlController.getFilterNamesByMenuName("project");
        List<String> priorityFilter = xmlController.getFilterNamesByMenuName("priority");

        File calendarFile = new File(ConfigHelper.getCalendarPath());
        if(!calendarFile.exists() || calendarFile.length()==0){
            labelMap= null;
            priorityMap=null;
            projectMap=null;
            return;
        }

        FileInputStream fin = new FileInputStream(calendarFile);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        IndexedComponentList indexedEvents = new IndexedComponentList(
                calendar.getComponents(Component.VEVENT), Property.UID);

        for (String filterName : labelFilter) {
            List<VEvent> events = new LinkedList<>();
            List<String> labelUids = xmlController.getUidsByFilterName("label", filterName);
            for (String uid : labelUids) {
                Component event = indexedEvents.getComponent(uid);
                events.add((VEvent) event);
            }
            labelMap.put(filterName, events);
        }

        for (String filterName : projectFilter) {
            List<VEvent> events = new LinkedList<>();
            List<String> projectUids = xmlController.getUidsByFilterName("project", filterName);

            for (String uid : projectUids) {
                Component event = indexedEvents.getComponent(uid);
                events.add((VEvent) event);
            }
            projectMap.put(filterName, events);
        }

        for (String filterName : priorityFilter) {
            List<VEvent> events = new LinkedList<>();
            List<String> priorityUids = xmlController.getUidsByFilterName("priority", filterName);

            for (String uid : priorityUids) {
                Component event = indexedEvents.getComponent(uid);
                events.add((VEvent) event);
            }
            priorityMap.put(filterName, events);
        }

    }

    public static Maps getMaps() throws DocumentException, IOException, ParserException {
        if (maps == null) {
            synchronized (Maps.class) {
                if (maps == null) {
                    maps = new Maps();
                    return maps;
                }
            }
        }
        return maps;
    }


    public  HashMap<String, List<VEvent>> getLabelMap() {
        return labelMap;
    }


    public  HashMap<String, List<VEvent>> getPriorityMap() {
        return priorityMap;
    }


    public  HashMap<String, List<VEvent>> getProjectMap() {
        return projectMap;
    }

}
