package utils;

import model.Maps;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author trafalgar
 */
public class MapHelper {

    private static final String label = "label";
    private static final String priority = "priority";
    private static final String project = "project";

    public static HashMap getMapByMapName(String mapName){
        if(mapName.equals(label)){
            return Maps.getLabelMap();
        }else if(mapName.equals(priority)){
            return Maps.getPriorityMap();
        }else if(mapName.equals(project)){
            return Maps.getProjectMap();
        }
        return null;
    }

    public static Set<String> getFilterNamesByMenuName(String menuName){
        return getMapByMapName(menuName).keySet();
    }

    public static List<VEvent> getEventByFilterName(String menuName, String filterName){
        return (List<VEvent>) getMapByMapName(menuName).get(filterName);
    }
}
