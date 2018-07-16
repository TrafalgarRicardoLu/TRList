package view;

import controller.CalendarController;
import controller.XmlController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.fortuna.ical4j.model.component.VEvent;
import org.dom4j.DocumentException;
import utils.MapHelper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


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
    public void start(Stage primaryStage) {
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

        TextField labelFilterInput = new TextField();
        Button labelFilterButton = new Button("+");
        labelFilterButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String filterName = labelFilterInput.getText();
                MapHelper.insertFilter(currentMenu, filterName);
                XmlController xmlController = new XmlController();
                try {
                    xmlController.insertFilter(currentMenu, filterName);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updeteMenuList();
                labelFilterInput.clear();
            }
        });
        HBox labelNewFilterBox = new HBox();
        labelNewFilterBox.getChildren().addAll(labelFilterInput, labelFilterButton);

        TextField projectFilterInput = new TextField();
        Button projectFilterButton = new Button("+");
        projectFilterButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String filterName = projectFilterInput.getText();
                MapHelper.insertFilter(currentMenu, filterName);
                XmlController xmlController = new XmlController();
                try {
                    xmlController.insertFilter(currentMenu, filterName);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updeteMenuList();
                projectFilterInput.clear();
            }
        });
        HBox projectNewFilterBox = new HBox();
        projectNewFilterBox.getChildren().addAll(projectFilterInput, projectFilterButton);

        TextField priorityFilterInput = new TextField();
        Button priorityFilterButton = new Button("+");
        priorityFilterButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String filterName = priorityFilterInput.getText();
                MapHelper.insertFilter(currentMenu, filterName);
                XmlController xmlController = new XmlController();
                try {
                    xmlController.insertFilter(currentMenu, filterName);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updeteMenuList();
                priorityFilterInput.clear();
            }
        });
        HBox priorityNewFilterBox = new HBox();
        priorityNewFilterBox.getChildren().addAll(priorityFilterInput, priorityFilterButton);

        VBox labelFilterBox = new VBox();
        labelFilterBox.getChildren().addAll(labelList, labelNewFilterBox);
        labelFilterBox.setPadding(new Insets(0));

        VBox projectFilterBox = new VBox();
        projectFilterBox.getChildren().addAll(projectList, projectNewFilterBox);
        projectFilterBox.setPadding(new Insets(0));


        VBox priorityFilterBox = new VBox();
        priorityFilterBox.getChildren().addAll(priorityList, priorityNewFilterBox);
        priorityFilterBox.setPadding(new Insets(0));


        TitledPane labelPane = new TitledPane("Label", labelFilterBox);
        labelPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentMenu = "label";
            }
        });
        TitledPane projectPane = new TitledPane("Project", projectFilterBox);
        projectPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentMenu = "project";
            }
        });
        TitledPane priorityPane = new TitledPane("Priority", priorityFilterBox);
        priorityPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentMenu = "priority";
            }
        });

        Accordion menuList = new Accordion();
        menuList.getPanes().addAll(labelPane, projectPane, priorityPane);
        menuList.setMinSize(200, 400);

        VBox leftView = new VBox();
        HBox calendarButtons = new HBox();
        calendarCreator.setPrefSize(100, 80);
        calendarCreator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CalendarController CalendarController = new CalendarController();
                try {
                    CalendarController.createCalendar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        calendarLoader.setPrefSize(100, 80);
        calendarButtons.getChildren().addAll(calendarCreator, calendarLoader);

        leftView.getChildren().addAll(calendarButtons, menuList);

        emptyListView = new ListView();
        emptyListView.setMaxSize(400, 150);
        List<String> emptyList = new LinkedList();
        emptyList.add("Add");
        ObservableList emptyItem = FXCollections.observableArrayList(emptyList);
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
        Object[] filters = MapHelper.getFilterNameListByMenuName(menuName).toArray();
        ObservableList<Object> items = FXCollections.observableArrayList(filters);

        ListView tempList = null;
        if (menuName.equals("label")) {
            tempList = labelList;
        } else if (menuName.equals("project")) {
            tempList = projectList;
        } else if (menuName.equals("priority")) {
            tempList = priorityList;
        }
        tempList.setItems(items);

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

    public static void updateEventList() {
        List vEventList = MapHelper.getEventListByFilterName(TRList.currentMenu, TRList.currentFilter);
        ObservableList eventList = FXCollections.observableArrayList(vEventList);
        TRList.eventListView.setItems(eventList);
        TRList.eventListView.setCellFactory(new Callback<ListView<VEvent>, ListCell<VEvent>>() {
            @Override
            public ListCell<VEvent> call(ListView<VEvent> param) {
                return new Event();
            }
        });
    }

    private void updeteMenuList() {
        Set filterSet = MapHelper.getFilterNameListByMenuName(currentMenu);
        ObservableList filterList = FXCollections.observableArrayList(filterSet);
        if (currentMenu.equals("label")) {
            labelList.setItems(filterList);
        } else if (currentMenu.equals("project")) {
            projectList.setItems(filterList);
        } else if (currentMenu.equals("priority")) {
            priorityList.setItems(filterList);
        }
    }
}
