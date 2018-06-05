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

    private static final String label = "label";
    private static final String priority = "priority";
    private static final String project = "project";

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
     * @param mapName
     * @return HashMap
     */
    public static HashMap getMapByMapName(String mapName) {
        if (mapName.equals(label)) {
            return maps.getLabelMap();
        } else if (mapName.equals(priority)) {
            return maps.getPriorityMap();
        } else if (mapName.equals(project)) {
            return maps.getProjectMap();
        }
        return null;
    }

    /**
     * get all filter names of specific menu
     *
     * @param menuName
     * @return Set<String>
     */
    public static Set<String> getFilterNamesByMenuName(String menuName) {
        if(getMapByMapName(menuName)==null){
            return new ListOrderedSet<String>();
        }
        return getMapByMapName(menuName).keySet();
    }

    /**
     * get all events of specific filter
     *
     * @param menuName
     * @param filterName
     * @return List<VEvent>
     */
    public static List<VEvent> getEventListByFilterName(String menuName, String filterName) {
        return (List<VEvent>) getMapByMapName(menuName).get(filterName);
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
        Map labelMap = getMapByMapName("label");
        Map projectMap = getMapByMapName("project");
        Map priotityMap = getMapByMapName("priority");


        Set<String> labelKeys = labelMap.keySet();
        String tempKey = null;
        List<VEvent> tempList = null;
        for (String key : labelKeys) {
            List<VEvent> eventList = (List<VEvent>) labelMap.get(key);
            for (VEvent event : eventList) {
                if (event.getUid().getValue().equals(uid.getValue())) {
                    eventList.remove(event);
                    tempKey = key;
                    tempList = eventList;
                }
            }
        }
        labelMap.remove(tempKey);
        labelMap.put(tempKey,tempList);
    }
}
