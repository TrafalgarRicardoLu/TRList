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

    public static HashMap<String,List> getUidsByFilterName() throws DocumentException {
        SAXReader reader = new SAXReader();
        File file = new File("/home/trafalgar/IdeaProjects/TRList/src/test/java/utilsTest/1.xml");
        Document document = reader.read(file);
        Element root = document.getRootElement();
        Element label = root.element("label");
        Element project = root.element("project");
        Element priority = root.element("priority");
        List<Element> uidElementLabel = label.elements();
        List<Element> uidElementProject = priority.elements();
        List<Element> uidElementPriority = priority.elements();

        List<String> uidLabel = new LinkedList<>();
        List<String> uidProject = new LinkedList<>();
        List<String> uidPriority = new LinkedList<>();

        for (Element uid : uidElementLabel) {
                uidLabel.add(uid.getText());
        }
        for(Element uid:uidElementProject){
                uidProject.add(uid.getText());
        }
        for(Element uid:uidElementPriority){
                uidPriority.add(uid.getText());
        }

        HashMap<String,List> uidMap = new HashMap<>();
        uidMap.put("label",uidLabel);
        uidMap.put("project",uidProject);
        uidMap.put("priority",uidPriority);
        return uidMap;
    }
}
