package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * @author trafalgar
 */
public class Event extends ListCell<VEvent> {



    @Override
    protected void updateItem(VEvent item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {

            HBox hbox = new HBox();
            hbox.setPadding(new Insets(15, 12, 15, 10));
            hbox.setSpacing(10);

            String summary = item.getSummary().toString().substring(8);
            String endDate = item.getEndDate().toString().substring(8);

            Button saveButton = new Button("Finished");
            Label eventName = new Label(summary);
            Label date = new Label(endDate);

            StackPane stack = new StackPane();
            stack.getChildren().addAll(saveButton);
            stack.setAlignment(Pos.CENTER_RIGHT);

            hbox.getChildren().addAll(eventName,date,stack);
            HBox.setHgrow(stack, Priority.ALWAYS);

            setGraphic(hbox);
        }
    }


}
