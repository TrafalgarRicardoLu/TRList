package entity;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.ProdId;

/**
 * @author trafalgar
 */
public class Event {

    private Calendar calendar;

    private String priority;

    private String label;

    private String project;

    private ProdId id = calendar.getProductId();
}
