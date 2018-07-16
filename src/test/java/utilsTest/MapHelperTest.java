package utilsTest;

import model.Maps;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import org.dom4j.DocumentException;
import org.junit.Test;
import utils.MapHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MapHelperTest {

    @Test
    public void testDeleteUid() {
        Uid uid = new Uid("1");
        MapHelper.deleteEventByUid(uid);

        List<VEvent> eventList = MapHelper.getEventListByFilterName("label", "shopping");
        for (VEvent event : eventList) {
            System.out.println(event.getUid());
        }
    }

    @Test
    public void testGetFiltersByMenuName() {
        Set<String> filters = MapHelper.getFilterNameListByMenuName("label");
        if (filters.size() == 0) {
            System.out.println("null");
        } else {
            for (Object i : filters) {
                System.out.println(i.toString());
            }
        }
    }

    @Test
    public void testGetMap() throws DocumentException, ParserException, IOException {
        Maps maps = Maps.getMaps();
        HashMap hashMap = maps.getMapByName("label");
        if(hashMap==null){
            System.out.println(1);
        }
    }
}
