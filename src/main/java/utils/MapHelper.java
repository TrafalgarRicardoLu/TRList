package utils;

import model.Maps;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VEvent;
import org.dom4j.DocumentException;

import java.io.IOException;
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

    public static HashMap getMapByMapName(String mapName){
        if(mapName.equals(label)){
            return maps.getLabelMap();
        }else if(mapName.equals(priority)){
            return maps.getPriorityMap();
        }else if(mapName.equals(project)){
            return maps.getProjectMap();
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
