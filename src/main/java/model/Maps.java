package model;

import org.dom4j.DocumentException;
import utils.xml.XMLGenerator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author trafalgar
 */
public class Maps {

    private static volatile Maps maps= null;

    private static HashMap<String, List<Event>> labelMap = new HashMap<>();

    private static HashMap<String, List<Event>> priorityMap = new HashMap<>();

    private static HashMap<String, List<Event>> projectMap = new HashMap<>();


    private Maps() throws DocumentException {
        List<String> labelFilter = XMLGenerator.getFilterNamesByMenuName("label");
        List<String> projectFilter = XMLGenerator.getFilterNamesByMenuName("project");
        List<String> priorityFilter = XMLGenerator.getFilterNamesByMenuName("priority");

        for(String filterName: labelFilter){
            List<Event> events = new LinkedList<>();
            List<String> labelUids = XMLGenerator.getUidsByFilterName("label",filterName);

            for(String uid:labelUids){
                //Todo
                //get event by UID
                //events.add();
            }
            labelMap.put(filterName,events);
        }
        for(String filterName: projectFilter){
            List<Event> events = new LinkedList<>();
            List<String> projectUids = XMLGenerator.getUidsByFilterName("project",filterName);

            for(String uid:projectUids){
                //Todo
                //get event by UID
                //events.add();
            }
            labelMap.put(filterName,events);
        }
        for(String filterName: priorityFilter){
            List<Event> events = new LinkedList<>();
            List<String> priorityUids = XMLGenerator.getUidsByFilterName("priority",filterName);

            for(String uid:priorityUids){
                //Todo
                //get event by UID
                //events.add();
            }
            labelMap.put(filterName,events);
        }

    }

    public static Maps getMaps() throws DocumentException {
        if(maps == null){
            synchronized (Maps.class){
                if(maps == null){
                    maps = new Maps();
                }
            }
        }
        return maps;
    }


    public static HashMap<String, List<Event>> getLabelMap() {
        return labelMap;
    }


    public static HashMap<String, List<Event>> getPriorityMap() {
        return priorityMap;
    }


    public static HashMap<String, List<Event>> getProjectMap() {
        return projectMap;
    }

}
