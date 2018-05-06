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

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public ProdId getId() {
        return id;
    }
}
