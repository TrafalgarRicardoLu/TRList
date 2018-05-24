package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class Event extends ListCell {

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {

            HBox hbox = new HBox();
            hbox.setPadding(new Insets(15, 12, 15, 10));
            hbox.setSpacing(10);

            Button saveButton = new Button("Save");
            Label eventName = new Label("Name");
            Label date = new Label("Date");

            StackPane stack = new StackPane();
            stack.getChildren().addAll(saveButton);
            stack.setAlignment(Pos.CENTER_RIGHT);

            hbox.getChildren().addAll(eventName,date,stack);
            HBox.setHgrow(stack, Priority.ALWAYS);

            setGraphic(hbox);
        }
    }
}
