package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import net.fortuna.ical4j.model.component.VEvent;
import utils.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
            String endDate = item.getEndDate().toString().substring(6);

            try {
                endDate = DateHelper.getDate(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Button saveButton = new Button("Finished");
            Label nameLabel = new Label("Name: ");
            Label dateLabel = new Label("Date: ");
            Label eventName = new Label(summary);
            Label eventDate = new Label(endDate);


            HBox nameBox = new HBox();
            TextField nameInput = new TextField(eventName.getText());
            nameBox.getChildren().addAll(nameLabel, eventName);
            nameBox.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    nameBox.getChildren().remove(eventName);
                    nameBox.getChildren().addAll(nameInput);
                }
            });
            nameBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        nameBox.getChildren().remove(nameInput);
                        eventName.setText(nameInput.getText());
                        nameBox.getChildren().addAll(eventName);
                    }
                }
            });


            HBox dateBox = new HBox();
            dateBox.getChildren().addAll(dateLabel, eventDate);



            VBox infoBox = new VBox();
            infoBox.getChildren().addAll(nameBox, dateBox);
            infoBox.setAlignment(Pos.CENTER_LEFT);

            StackPane buttonStack = new StackPane();
            buttonStack.getChildren().addAll(saveButton);
            buttonStack.setAlignment(Pos.CENTER_RIGHT);

            hbox.getChildren().addAll(infoBox, buttonStack);
            HBox.setHgrow(buttonStack, Priority.ALWAYS);

            setGraphic(hbox);
        }
    }


}
