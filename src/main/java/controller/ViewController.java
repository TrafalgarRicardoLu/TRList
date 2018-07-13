package controller;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Uid;
import utils.MapHelper;
import utils.UidGenerator;
import view.Event;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author trafalgar
 */
public class ViewController {

    public void handleSaveClike(){

        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("America/Mexico_City");
        VTimeZone tz = timezone.getVTimeZone();

        // 起始时间是：2008 年 4 月 1 日 上午 9 点
        java.util.Calendar startDate = new GregorianCalendar();
        startDate.setTimeZone(timezone);
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        startDate.set(java.util.Calendar.YEAR, 2008);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        // 结束时间是：2008 年 4 月 1 日 下午 1 点
        java.util.Calendar endDate = new GregorianCalendar();
        endDate.setTimeZone(timezone);
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        endDate.set(java.util.Calendar.YEAR, 2008);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 13);
        endDate.set(java.util.Calendar.MINUTE, 0);
        endDate.set(java.util.Calendar.SECOND, 0);

        // 创建事件
        String eventName = "Progress Meeting";
        DateTime start = new DateTime(startDate.getTime());
        DateTime end = new DateTime(endDate.getTime());

        VEvent meeting = new VEvent(start, end, eventName);

        // 添加时区信息
        meeting.getProperties().add(tz.getTimeZoneId());

        // Get UID
        UidGenerator ug = new UidGenerator();
        Uid uid = ug.generateUid();
        meeting.getProperties().add(uid);

        System.out.println(meeting.getSummary());

    }

    public List<Event> handleClinkFilter(String menuName,String filterName){
        List<VEvent> eventList = MapHelper.getEventListByFilterName(menuName,filterName);
        List<Event> eventViewList = new LinkedList<>();

        Iterator iterator = eventList.iterator();
        while(iterator.hasNext()){
            VEvent current = (VEvent) iterator.next();
            Event event = new Event();
            eventViewList.add(event);
        }

        return eventViewList;
    }
}