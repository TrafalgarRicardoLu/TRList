package utils;

import model.Maps;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.*;

/**
 * @author trafalgar
 */
public class MapHelper {

    private static Maps maps;

    static {
        try {
            maps = Maps.getMaps();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * get map
     *
     * @param menuName
     * @return HashMap
     */
    public static HashMap getMapByName(String menuName) {
        return maps.getMapByName(menuName);
    }

    /**
     * get all filter names of specific menu
     *
     * @param menuName
     * @return Set<String>
     */
    public static Set<String> getFilterNameListByMenuName(String menuName) {
        if (getMapByName(menuName) == null) {
            return new ListOrderedSet<String>();
        }
        return getMapByName(menuName).keySet();
    }

    /**
     * get all events of specific filter
     *
     * @param menuName
     * @param filterName
     * @return List<VEvent>
     */
    public static List<VEvent> getEventListByFilterName(String menuName, String filterName) {
        return (List<VEvent>) getMapByName(menuName).get(filterName);
    }


    /**
     * get specific event by name
     *
     * @param menuName
     * @param filterName
     * @param eventName
     * @return VEvent
     */
    public static VEvent getEventByName(String menuName, String filterName, String eventName) {
        List<VEvent> eventList = getEventListByFilterName(menuName, filterName);

        Iterator iterator = eventList.iterator();
        while (iterator.hasNext()) {
            VEvent event = (VEvent) iterator.next();
            String name = event.getName();
            if (name.equals(eventName)) {
                return event;
            }
        }
        return null;
    }

    public static void deleteEventByUid(Uid uid) {
        deleteEventAtMenu(uid, "label");
        deleteEventAtMenu(uid, "project");
        deleteEventAtMenu(uid, "priority");
    }

    private static void deleteEventAtMenu(Uid uid, String menuName) {
        Map menuMap = getMapByName(menuName);

        Set<String> keys = menuMap.keySet();
        String tempKey = null;
        List<VEvent> tempList = null;
        for (String key : keys) {
            List<VEvent> eventList = (List<VEvent>) menuMap.get(key);
            for (VEvent event : eventList) {
                if (event.getUid().getValue().equals(uid.getValue())) {
                    eventList.remove(event);
                    tempKey = key;
                    tempList = eventList;
                }
            }
        }
        if (tempKey != null) {
            menuMap.remove(tempKey);
            menuMap.put(tempKey, tempList);
        }
    }

    public static void insertEvent(VEvent event,String menuName, String filterName){
        HashMap menuMap = getMapByName(menuName);
        List eventList = getEventListByFilterName(menuName,filterName);

        eventList.add(event);
        menuMap.put(filterName,eventList);

    }

    public static void insertFilter(String menuName,String filterName){
        HashMap menuMap = getMapByName(menuName);
        menuMap.put(filterName,new LinkedList<VEvent>());
    }
}
