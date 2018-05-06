package controller;

import entity.Event;
import entity.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author trafalgar
 */
public class MapController {

    Maps maps;

    private String label = "label";
    private String priority = "priority";
    private String project = "project";

    public HashMap getMapByMapName(String mapName){
        if(mapName.equals(label)){
            return Maps.getLabelMap();
        }else if(mapName.equals(priority)){
            return Maps.getPriorityMap();
        }else if(mapName.equals(project)){
            return Maps.getProjectMap();
        }
        return null;
    }

    public Set getItemsByMuneName(String menuName){
        return getMapByMapName(menuName).entrySet();
    }

    public List<Event> getEventByFilterName(String menuName, String filterName){
        return (List<Event>) getMapByMapName(menuName).get(filterName);
    }
}
