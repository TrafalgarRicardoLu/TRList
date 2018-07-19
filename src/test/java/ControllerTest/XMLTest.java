package ControllerTest;

import controller.XmlController;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import utils.UidGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLTest {

    XmlController XmlController = new XmlController();

    @Test
    public void testDom() throws DocumentException {
        SAXReader reader = new SAXReader();
        File file = new File("/home/trafalgar/IdeaProjects/TRList/src/test/java/TRList.xml");
        Document document = reader.read(file);
        Element root = document.getRootElement();
        Element label = root.element("label");
        Element project = root.element("project");
        Element priority = root.element("priority");
        List<Element> filterLabel = label.elements();
        for (Element filter : filterLabel) {
            List<Element> uidList = filter.elements();
            System.out.println(filter.attribute("name").getData());
            for (Element uid : uidList) {
                System.out.println(uid.getText());
            }
        }
    }

    @Test
    public void testGetUids() throws DocumentException {
        XmlController xmlGenerator = new XmlController();
        List<String> uids = xmlGenerator.getUidListByFilterName("project", "TRList");
        if (uids == null) {
            System.out.println(1);
        }
        for (String uid : uids) {
            System.out.println(uid);
        }
    }

    @Test
    public void testAddTodoUid() throws DocumentException, IOException {
        VEvent vEvent = new VEvent();
        UidGenerator uidGenerator = new UidGenerator();
        Uid uid = uidGenerator.generateUid();
        vEvent.getProperties().add(uid);
        XmlController.insertTodoUid("label", "shopping", vEvent);
    }

    @Test
    public void testDeleteTodoUid() throws IOException, DocumentException {
        VEvent vEvent = new VEvent();
        UidGenerator uidGenerator = new UidGenerator();
        Uid uid = uidGenerator.generateUid();
        vEvent.getProperties().add(uid);
        XmlController.deleteTodoUid("label", uid);
    }

    @Test
    public void testAddFinishedUid() throws IOException {
        Uid uid = new Uid("122");
        XmlController.insertFinishedUid(uid);
    }

    @Test
    public void testMoveUidToFinished() throws IOException, DocumentException {
        Uid uid = new Uid("2");
        XmlController.markUidAsFinished(uid);
    }

    @Test
    public void testGetFinishedUId() throws DocumentException {
        List<Element> uids = XmlController.getFinishedUidList();
        for (Element i : uids) {
            System.out.println(i.getText());
        }

    }

    @Test
    public void testGetFiltersByMenuName() throws DocumentException {
        List<String> filters = XmlController.getFilterNameListByMenuName("label");
        for(String i: filters){
            System.out.println(i);
        }
    }

    @Test
    public void testInsertFilter() throws IOException, DocumentException {
        XmlController.insertFilter("label","New");
    }

}
