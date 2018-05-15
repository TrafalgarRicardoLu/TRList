package controller;

import model.Event;
import model.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author trafalgar
 */
public class MapController {

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

    public static Set<String> getFiltersByMenuName(String menuName){
        return getMapByMapName(menuName).keySet();
    }

    public static List<Event> getEventByFilterName(String menuName, String filterName){
        return (List<Event>) getMapByMapName(menuName).get(filterName);
    }
}
