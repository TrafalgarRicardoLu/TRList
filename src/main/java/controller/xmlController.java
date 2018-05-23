package controller;

import net.fortuna.ical4j.model.component.VEvent;
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
public class xmlController {

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
     * get filters from XML by menuName
     *
     * @param menuName
     * @return List<Element>
     * @throws DocumentException
     */
    public static List<Element> getFiltersByMenuName(String menuName) throws DocumentException {

        Element root = document.getRootElement();
        Element menu = root.element(menuName);
        return menu.elements();
    }

    /**
     * get filter names from XML by menuName
     *
     * @param menuName
     * @return List<String>
     * @throws DocumentException
     */
    public static List<String> getFilterNamesByMenuName(String menuName) throws DocumentException {
        List<Element> filters = getFiltersByMenuName(menuName);
        List<String> filterNames = new LinkedList<>();

        for (Element filter : filters) {
            filterNames.add((String) filter.attribute("name").getData());
        }

        return filterNames;
    }

    /**
     *get uids from XML, it must point out menu name and filter name in case there is same filter name
     * in different menu
     *
     * @param menuName
     * @param filterName
     * @return List<String>
     * @throws DocumentException
     */
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

    /**
     * create XML file if not exist
     * @throws IOException
     */

    public static void createXML() throws IOException {
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
    public static void addUid(String menuName, String filterName, VEvent vEvent) throws DocumentException, IOException {
        String uid = vEvent.getUid().getValue();
        List<Element> filters = getFiltersByMenuName(menuName);
        Element filter = null;

        for (Element i : filters) {
            if (i.attribute("name").getData().equals(filterName)) {
                filter = i;
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
     * @param menuName
     * @param filterName
     * @param vEvent
     * @throws DocumentException
     * @throws IOException
     */
    public static void deleteUid(String menuName, String filterName, VEvent vEvent) throws DocumentException, IOException {
        String uid = vEvent.getUid().getValue();
        List<Element> filters = getFiltersByMenuName(menuName);
        Element filter = null;

        for (Element i : filters) {
            if (i.attribute("name").getData().equals(filterName)) {
                filter = i;
            }
        }

        List<Element> uids = filter.elements();
        for (Element i : uids) {
            if (i.getText().equals(uid)) {
                filter.remove(i);
            }
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileWriter(XMLPath), format);
        writer.write(document);
        writer.close();
    }
}
