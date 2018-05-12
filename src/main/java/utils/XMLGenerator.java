package utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author trafalgar
 */
public class XMLGenerator {

    public static List<Element> getFiltersByMenuName(String menuName) throws DocumentException {
        SAXReader reader = new SAXReader();
        File file = new File("/home/trafalgar/IdeaProjects/TRList/src/test/java/utilsTest/1.xml");
        Document document = reader.read(file);
        Element root = document.getRootElement();
        Element menu = root.element(menuName);
        return menu.elements();
    }

    public static List<String> getFilterNamesByMenuName(String menuName) throws DocumentException {
        List<Element> filters = getFiltersByMenuName(menuName);
        List<String> filterNames = new LinkedList<>();

        for(Element filter:filters){
            filterNames.add((String) filter.attribute("name").getData());
        }

        return filterNames;
    }

    public static List<String> getUidsByFilterName(String menuName, String filterName) throws DocumentException {
        List<Element> filters = getFiltersByMenuName(menuName);
        List<String> uids = new LinkedList<>();

        for (Element filter : filters) {
            if (filter.attribute("name").getData().equals(filterName)) {
                List<Element> tempUids = filter.elements();
                for (Element uid : tempUids) {
                    uids.add(uid.getText());
                }
            }
        }

        return uids;
    }
}
