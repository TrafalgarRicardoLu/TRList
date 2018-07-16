package controller;

import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import utils.conf.ConfigHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author trafalgar
 */
public class XmlController {

    private static Document document;
    private static final String XMLPath = ConfigHelper.getXMLPath();

    static {
        SAXReader reader = new SAXReader();
        File file = new File(XMLPath);
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * get to do filters from XML by menuName
     *
     * @param menuName
     * @return List<Element>
     * @throws DocumentException
     */
    public List<Element> getTodoFilterListByMenuName(String menuName) throws DocumentException {

        Element root = document.getRootElement();
        Element menu = root.element("todo").element(menuName);
        return menu.elements();
    }


    /**
     * get to to filter names from XML by menuName
     *
     * @param menuName
     * @return List<String>
     * @throws DocumentException
     */
    public List<String> getFilterNameListByMenuName(String menuName) throws DocumentException {
        List<Element> filters = getTodoFilterListByMenuName(menuName);
        List<String> filterNames = new LinkedList<>();

        for (Element filter : filters) {
            filterNames.add((String) filter.attribute("name").getData());
        }

        return filterNames;
    }

    /**
     * get uids from XML, it must point out menu name and filter name in case there is same filter name
     * in different menu
     *
     * @param menuName
     * @param filterName
     * @return List<String>
     * @throws DocumentException
     */
    public List<String> getUidListByFilterName(String menuName, String filterName) throws DocumentException {
        List<Element> filters = getTodoFilterListByMenuName(menuName);
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

    /**
     * create XML file if not exist
     *
     * @throws IOException
     */

    public void createXML() throws IOException {
        File dividedFile = new File(XMLPath);
        if (!dividedFile.exists()) {
            dividedFile.createNewFile();
        }
    }

    /**
     * add new uid to XML, it must point out menu name and filter name.
     *
     * @param menuName
     * @param filterName
     * @param vEvent
     * @throws DocumentException
     * @throws IOException
     */
    public void insertTodoUid(String menuName, String filterName, VEvent vEvent) throws DocumentException, IOException {
        String uid = vEvent.getUid().getValue();
        List<Element> filters = getTodoFilterListByMenuName(menuName);
        Element filter = null;

        for (Element i : filters) {
            if (i.attribute("name").getData().equals(filterName)) {
                filter = i;
            }
        }

        List<Element> uids = filter.elements();
        for (Element i : uids) {
            if (i.getText().equals(uid)) {
                return;
            }
        }

        filter.addElement("uid").setText(uid);

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileWriter(XMLPath), format);
        writer.write(document);
        writer.close();
    }

    /**
     * delete new uid from XML, it must point out menu name and filter name.
     *
     * @param menuName
     * @param uid
     * @throws DocumentException
     * @throws IOException
     */
    public void deleteTodoUid(String menuName, Uid uid) throws DocumentException, IOException {
        List<Element> filters = getTodoFilterListByMenuName(menuName);

        for (Element filter : filters) {
            List<Element> uids = filter.elements();
            for (Element i : uids) {
                if (i.getText().equals(uid.getValue())) {
                    filter.remove(i);
                }
            }
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileWriter(XMLPath), format);
        writer.write(document);
        writer.close();
    }

    /**
     * get to do filters from XML by menuName
     *
     * @return List<Element>
     * @throws DocumentException
     */
    public List<Element> getFinishedUidList() throws DocumentException {

        Element root = document.getRootElement();
        List<Element> finishedUids = root.element("finished").elements();
        return finishedUids;
    }

    public void insertFinishedUid(Uid uid) throws IOException {
        Element root = document.getRootElement();
        Element finished = root.element("finished");
        List<Element> finishedUids = finished.elements();

        for (Element i : finishedUids) {
            if (i.getText().equals(uid.getValue())) {
                return;
            }
        }

        finished.addElement("uid").setText(uid.getValue());

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileWriter(XMLPath), format);
        writer.write(document);
        writer.close();
    }

    public void markUidAsFinished(Uid uid) throws IOException, DocumentException {
        deleteTodoUid("label", uid);
        deleteTodoUid("project", uid);
        deleteTodoUid("priority", uid);

        insertFinishedUid(uid);
    }

    public void insertFilter(String menuName, String filterName) throws DocumentException, IOException {
        List<Element> menus = document.getRootElement().element("todo").elements();
        Element menu = null;

        for (Element i : menus) {
            if(i.getName().equals(menuName)){
                menu=i;
                break;
            }
        }

        menu.addElement("filter").addAttribute("name",filterName);

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileWriter(XMLPath), format);
        writer.write(document);
        writer.close();


    }
}
