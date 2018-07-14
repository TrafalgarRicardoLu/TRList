package view;

import controller.CalendarController;
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
import java.util.LinkedList;
import java.util.List;


/**
 * @author trafalgar
 */
public class TRList extends Application {

    static ListView eventListView;
    static ListView emptyListView;
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
    public void start(Stage primaryStage){
        eventListView = new ListView();
        emptyListView = new ListView();
        calendarCreator = new Button("Create");
        calendarLoader = new Button("Load");

        labelList = new ListView();
        projectList = new ListView();
        priorityList = new ListView();

        setListViewItems("label");
        setListViewItems("project");
        setListViewItems("priority");

        TitledPane labelPane = new TitledPane("Label", labelList);
        TitledPane projectPane = new TitledPane("Project", projectList);
        TitledPane priorityPane = new TitledPane("Priority", priorityList);

        Accordion menuList = new Accordion();
        menuList.getPanes().addAll(labelPane, projectPane, priorityPane);
        menuList.setMinSize(200, 400);

        VBox leftView = new VBox();
        HBox calendarButtons = new HBox();
        calendarCreator.setPrefSize(100, 80);
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
        calendarLoader.setPrefSize(100, 80);
        calendarButtons.getChildren().addAll(calendarCreator, calendarLoader);

        leftView.getChildren().addAll(calendarButtons, menuList);

        emptyListView =new ListView();
        emptyListView.setMaxSize(400,150);
        List<String> emptyList = new LinkedList();
        emptyList.add("Add");
        for(String i: emptyList){
            System.out.println(i);
        }
        ObservableList emptyItem = FXCollections.observableArrayList(emptyList);
        System.out.println(emptyItem.size());
        emptyListView.setItems(emptyItem);
        emptyListView.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new emptyCell();
            }
        });

        VBox eventBox = new VBox();
        eventBox.setMinSize(400, 400);
        eventBox.getChildren().addAll(eventListView, emptyListView);

        HBox root = new HBox();
        root.getChildren().addAll(leftView, eventBox);

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(TRList.class.getResource("/bootstrap3.css").toExternalForm());

        primaryStage.setTitle("TRList");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private void setListViewItems(String menuName) {
        //set filters
        Object[] Filters = MapHelper.getFilterNameListByMenuName(menuName).toArray();
        ObservableList<Object> Items = FXCollections.observableArrayList(Filters);

        ListView tempList = null;
        if (menuName.equals("label")) {
            tempList = labelList;
        } else if (menuName.equals("project")) {
            tempList = projectList;
        } else if (menuName.equals("priority")) {
            tempList = priorityList;
        }
        tempList.setItems(Items);

        //set events
        tempList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        currentMenu = menuName;
                        currentFilter = newValue;
                        List vEventList = MapHelper.getEventListByFilterName(menuName, newValue);
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
    }
}
