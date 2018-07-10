package view;

import controller.CalendarController;
import controller.XmlController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.Summary;
import org.dom4j.DocumentException;
import utils.DateHelper;
import utils.MapHelper;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author trafalgar
 */
public class Event extends ListCell<VEvent> {

    Button saveButton = new Button("Finished");
    Label nameLabel = new Label("Name: ");
    Label dateLabel = new Label("Date: ");
    Label eventName;
    Label eventDate;

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

            saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        XmlController.markUidAsFinished(item.getUid());
                        MapHelper.deleteEventByUid(item.getUid());
                        List vEventList = MapHelper.getEventListByFilterName(TRList.currentMenu, TRList.currentFilter);
                        ObservableList eventList = FXCollections.observableArrayList(vEventList);
                        TRList.eventListView.setItems(eventList);
                        TRList.eventListView.setCellFactory(new Callback<ListView<VEvent>, ListCell<VEvent>>() {
                            @Override
                            public ListCell<VEvent> call(ListView<VEvent> param) {
                                return new Event();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
            });


            eventName = new Label(summary);
            eventDate = new Label(endDate);


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
                        CalendarController calendarController = new CalendarController();
                        try {
                            calendarController.updateCalendar(item);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParserException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


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
                        item.getProperties().add(new DtEnd(DateHelper.getCalDate(dateInput.getText())));

                    }
                }
            });

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
