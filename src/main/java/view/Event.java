package view;

import controller.CalendarController;
import controller.XmlController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;
import org.dom4j.DocumentException;
import utils.DateHelper;
import utils.MapHelper;

import java.io.IOException;

/**
 * @author trafalgar
 */
public class Event extends ListCell<VEvent> {

    Button finishedButton = new Button("Finished");
    Label nameLabel = new Label("Name: ");
    Label dateLabel = new Label("Date: ");
    Label eventName;
    Label eventDate;


    @Override
    protected void updateItem(VEvent item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {

            String summary = item.getSummary().toString().substring(8);
            String endDate = item.getEndDate().toString().substring(6);
            endDate = DateHelper.getDate(endDate);

            finishedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        XmlController XmlController = new XmlController();
                        XmlController.markUidAsFinished(item.getUid());
                        MapHelper.deleteEventByUid(item.getUid());
                        TRList.updateEventList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            eventName = new Label(summary);
            eventDate = new Label(endDate);

            //ui and click event of name
            HBox nameBox = new HBox();
            TextField nameInput = new TextField(eventName.getText());
            nameBox.getChildren().addAll(nameLabel, eventName);

            eventName.setOnMousePressed(new EventHandler<MouseEvent>() {
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

                        item.getProperties().remove(item.getSummary());
                        item.getProperties().add(new Summary(nameInput.getText()));
                        CalendarController CalendarController = new CalendarController();
                        try {
                            CalendarController.updateCalendar(item);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParserException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            //ui and click event of date
            HBox dateBox = new HBox();
            TextField dateInput = new TextField(eventDate.getText());
            dateBox.getChildren().addAll(dateLabel, eventDate);

            eventDate.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    dateBox.getChildren().remove(eventDate);
                    dateBox.getChildren().add(dateInput);
                }
            });

            dateBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        dateBox.getChildren().remove(dateInput);
                        eventDate.setText(dateInput.getText());
                        dateBox.getChildren().addAll(eventDate);

                        item.getProperties().remove(item.getEndDate());
                        item.getProperties().add(new Summary(dateInput.getText()));

                    }
                }
            });

            VBox infoBox = new VBox();
            infoBox.getChildren().addAll(nameBox, dateBox);
            infoBox.setAlignment(Pos.CENTER_LEFT);

            StackPane buttonStack = new StackPane();
            buttonStack.getChildren().addAll(finishedButton);
            buttonStack.setAlignment(Pos.CENTER_RIGHT);

            HBox mainBox = new HBox();
            HBox.setHgrow(buttonStack, Priority.ALWAYS);
            mainBox.setPadding(new Insets(15, 12, 15, 10));
            mainBox.setSpacing(10);
            mainBox.getChildren().addAll(infoBox, buttonStack);

            setGraphic(mainBox);
        }
    }
}
