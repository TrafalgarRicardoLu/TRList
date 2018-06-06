package model;

import controller.XmlController;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.IndexedComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import org.dom4j.DocumentException;
import utils.conf.ConfigHelper;

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
     * <p>
     * Singleton Pattern
     *
     * @throws DocumentException
     * @throws IOException
     * @throws ParserException
     */
    private Maps() throws DocumentException, IOException, ParserException {
        File calendarFile = new File(ConfigHelper.getCalendarPath());
        if (!calendarFile.exists() || calendarFile.length() == 0) {
            labelMap = null;
            priorityMap = null;
            projectMap = null;
            return;
        }

        initMaps();
    }

    private void initMaps() throws DocumentException, ParserException, IOException {
        initMapByName("label");
        initMapByName("project");
        initMapByName("priority");
    }

    private void initMapByName(String menuName) throws DocumentException, IOException, ParserException {
        List<String> menuFilter = XmlController.getFilterNameListByMenuName(menuName);

        FileInputStream fin = new FileInputStream(ConfigHelper.getCalendarPath());
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        IndexedComponentList indexedEvents = new IndexedComponentList(
                calendar.getComponents(Component.VEVENT), Property.UID);

        for (String filterName : menuFilter) {
            List<VEvent> events = new LinkedList<>();
            List<String> priorityUids = XmlController.getUidListByFilterName(menuName, filterName);

            for (String uid : priorityUids) {
                Component event = indexedEvents.getComponent(uid);
                events.add((VEvent) event);
            }
            getMapByName(menuName).put(filterName, events);
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


    public HashMap<String, List<VEvent>> getMapByName(String menuName) {
        if (menuName.equals("label")) {
            return labelMap;
        } else if (menuName.equals("priority")) {
            return priorityMap;
        } else if (menuName.equals("project")) {
            return projectMap;
        }
        return null;
    }


}
