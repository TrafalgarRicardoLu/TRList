package view;

import controller.CalendarController;
import controller.UIController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.fortuna.ical4j.model.component.VEvent;
import utils.MapHelper;

import java.io.IOException;
import java.util.List;

/**
 * @author trafalgar
 */
public class TRList extends Application {

    static ListView eventListView;
    ListView labelList;
    ListView projectList;
    ListView priorityList;
    Button calendarCreator;
    Button calendarLoader;
    static String currentMenu = null;
    static String currentFilter = null;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        eventListView = new ListView();

        calendarCreator = new Button("Create");
        calendarLoader= new Button("Load");

        labelList = new ListView();
        projectList = new ListView();
        priorityList = new ListView();

        Object[] labelFilters = MapHelper.getFilterNamesByMenuName("label").toArray();
        Object[] projectFilters = MapHelper.getFilterNamesByMenuName("project").toArray();
        Object[] priorityFilters = MapHelper.getFilterNamesByMenuName("priority").toArray();

        ObservableList<Object> labelItems = FXCollections.observableArrayList(labelFilters);
        ObservableList<Object> projectItems = FXCollections.observableArrayList(projectFilters);
        ObservableList<Object> priorityItems = FXCollections.observableArrayList(priorityFilters);

        labelList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        currentMenu = "label";
                        currentFilter = newValue;
                        List vEventList = MapHelper.getEventListByFilterName("label", newValue);
                        ObservableList eventList = FXCollections.observableArrayList(vEventList);
                        eventListView.setItems(eventList);
                        eventListView.setCellFactory(new Callback<ListView<VEvent>, ListCell<VEvent>>() {
                            @Override
                            public ListCell<VEvent> call(ListView<VEvent> param) {
                                return new Event();
                            }
                        });
                    }
                });


        projectList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        currentMenu = "project";
                        currentFilter = newValue;
                        List vEventList = MapHelper.getEventListByFilterName("project", newValue);
                        ObservableList eventList = FXCollections.observableArrayList(vEventList);
                        eventListView.setItems(eventList);
                        eventListView.setCellFactory(new Callback<ListView<VEvent>, ListCell<VEvent>>() {
                            @Override
                            public ListCell<VEvent> call(ListView<VEvent> param) {
                                return new Event();
                            }
                        });
                    }
                });

        priorityList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        currentMenu = "priority";
                        currentFilter = newValue;
                        List vEventList = MapHelper.getEventListByFilterName("priority", newValue);
                        ObservableList eventList = FXCollections.observableArrayList(vEventList);
                        eventListView.setItems(eventList);
                        eventListView.setCellFactory(new Callback<ListView<VEvent>, ListCell<VEvent>>() {
                            @Override
                            public ListCell<VEvent> call(ListView<VEvent> param) {
                                return new Event();
                            }
                        });
                    }
                });

        labelList.setItems(labelItems);
        projectList.setItems(projectItems);
        priorityList.setItems(priorityItems);

        TitledPane label = new TitledPane("Label", labelList);
        TitledPane project = new TitledPane("Project", projectList);
        TitledPane priority = new TitledPane("Priority", priorityList);

        Accordion menuList = new Accordion();
        menuList.getPanes().addAll(label, project, priority);
        menuList.setMinSize(200,400);

        VBox leftView = new VBox();
        HBox calendarButtons = new HBox();
        calendarCreator.setPrefSize(100,80);
        calendarCreator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CalendarController calendarController = new CalendarController();
                try {
                    calendarController.createCalendar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        calendarLoader.setPrefSize(100,80);
        calendarButtons.getChildren().addAll(calendarCreator,calendarLoader);

        leftView.getChildren().addAll(calendarButtons,menuList);

        HBox root = new HBox();
        eventListView.setMinSize(400,400);
        root.getChildren().addAll(leftView, eventListView);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(TRList.class.getResource("/bootstrap3.css").toExternalForm());

        primaryStage.setTitle("TRList");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
