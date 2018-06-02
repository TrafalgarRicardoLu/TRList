package utilsTest;

import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import org.junit.Test;
import utils.MapHelper;

import java.util.List;

public class MapHelperTest {

    @Test
    public void testDeleteUid(){
        Uid uid = new Uid("1");
        MapHelper.deleteEventByUid(uid);

        List<VEvent> eventList = MapHelper.getEventListByFilterName("label","shopping");
        for (VEvent event:eventList){
            System.out.println(event.getUid());
        }
    }
}
