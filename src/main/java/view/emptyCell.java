package view;

import controller.CalendarController;
import controller.XmlController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import org.dom4j.DocumentException;
import utils.DateHelper;
import utils.MapHelper;
import utils.UidGenerator;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class emptyCell extends ListCell<String> {

    Button addButton = new Button("+");
    Label nameLabel = new Label("Name: ");
    Label dateLabel = new Label("Date: ");
    TextField nameInput = new TextField();
    TextField dateInput = new TextField();

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, nameInput);

        HBox dateBox = new HBox();
        dateBox.getChildren().addAll(dateLabel, dateInput);

        VBox infoBox = new VBox();
        infoBox.getChildren().addAll(nameBox, dateBox);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        StackPane buttonStack = new StackPane();
        buttonStack.getChildren().addAll(addButton);
        buttonStack.setAlignment(Pos.CENTER_RIGHT);


        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String name = nameInput.getText();
                String dateString = dateInput.getText();
                DateTime endDate = DateHelper.getCalDate(dateString);
                DateTime startDate = new DateTime(new Date().getTime());
                VEvent vEvent = new VEvent(startDate, endDate, name);
                UidGenerator uidGenerator = new UidGenerator();
                Uid uid = uidGenerator.generateUid();
                vEvent.getProperties().add(uid);
                CalendarController CalendarController = new CalendarController();
                try {
                    CalendarController.updateCalendar(vEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserException e) {
                    e.printStackTrace();
                }

                XmlController XmlController = new XmlController();
                try {
                    XmlController.insertTodoUid(TRList.currentMenu, TRList.currentFilter, vEvent);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MapHelper.insertEvent(vEvent,TRList.currentMenu,TRList.currentFilter);
                List vEventList = MapHelper.getEventListByFilterName(TRList.currentMenu, TRList.currentFilter);
                ObservableList eventList = FXCollections.observableArrayList(vEventList);
                TRList.eventListView.setItems(eventList);
                TRList.eventListView.setCellFactory(new Callback<ListView<VEvent>, ListCell<VEvent>>() {
                    @Override
                    public ListCell<VEvent> call(ListView<VEvent> param) {
                        return new Event();
                    }
                });

                nameInput.clear();
                dateInput.clear();
            }
        });

        HBox mainBox = new HBox();
        mainBox.setPadding(new Insets(15, 12, 15, 10));
        mainBox.setSpacing(10);
        mainBox.getChildren().addAll(infoBox, buttonStack);
        HBox.setHgrow(buttonStack, Priority.ALWAYS);


        setGraphic(mainBox);
    }
}
