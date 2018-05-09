package entity;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.ProdId;

/**
 * @author trafalgar
 */
public class Event {

    private VEvent activity;

    private String priority;

    private String label;

    private String project;

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


    public VEvent getActivity() {
        return activity;
    }

    public void setActivity(VEvent activity) {
        this.activity = activity;
    }
}
