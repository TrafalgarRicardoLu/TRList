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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import org.dom4j.DocumentException;
import utils.DateHelper;
import utils.MapHelper;
import utils.UidGenerator;
import utils.conf.ConfigHelper;

import java.io.*;
import java.util.Date;
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
    Button xmlLoader;
    static String currentMenu = null;
    static String currentFilter = null;

    Button addButton = new Button("+");
    Label nameLabel = new Label("Name: ");
    Label dateLabel = new Label("Date: ");
    TextField nameInput = new TextField();
    TextField dateInput = new TextField();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        eventListView = new ListView();
        emptyListView = new ListView();
        calendarCreator = new Button("Create");
        calendarLoader = new Button("Load Cal");
        xmlLoader = new Button("Load XML");

        labelList = new ListView();
        projectList = new ListView();
        priorityList = new ListView();

        //init menuList
        setListViewItems("label");
        setListViewItems("project");
        setListViewItems("priority");

        //box for adding new filter
        TextField newFilterInput = new TextField();
        Button newFilterButton = new Button("+");
        newFilterButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String filterName = newFilterInput.getText();
                MapHelper.insertFilter(currentMenu, filterName);
                XmlController xmlController = new XmlController();
                try {
                    xmlController.insertFilter(currentMenu, filterName);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateMenuList();
                newFilterInput.clear();
            }
        });
        HBox newFilterBox = new HBox();
        newFilterBox.getChildren().addAll(newFilterInput, newFilterButton);


        VBox labelFilterBox = new VBox();
        labelFilterBox.getChildren().add(labelList);
        labelFilterBox.setPadding(new Insets(0));

        VBox projectFilterBox = new VBox();
        projectFilterBox.getChildren().add(projectList);
        projectFilterBox.setPadding(new Insets(0));


        VBox priorityFilterBox = new VBox();
        priorityFilterBox.getChildren().add(priorityList);
        priorityFilterBox.setPadding(new Insets(0));

        //put menuList into titlePane
        TitledPane labelPane = new TitledPane("Label", labelFilterBox);
        labelPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentMenu = "label";
                checkContain(labelFilterBox,projectFilterBox,priorityFilterBox,newFilterBox);
                labelFilterBox.getChildren().add(newFilterBox);
            }
        });
        TitledPane projectPane = new TitledPane("Project", projectFilterBox);
        projectPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentMenu = "project";
                checkContain(labelFilterBox,projectFilterBox,priorityFilterBox,newFilterBox);
                projectFilterBox.getChildren().add(newFilterBox);
            }
        });
        TitledPane priorityPane = new TitledPane("Priority", priorityFilterBox);
        priorityPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentMenu = "priority";
                checkContain(labelFilterBox,projectFilterBox,priorityFilterBox,newFilterBox);
                priorityFilterBox.getChildren().add(newFilterBox);
            }
        });

        Accordion menuList = new Accordion();
        menuList.getPanes().addAll(labelPane, projectPane, priorityPane);
        menuList.setMinSize(200, 400);

        //add buttons for loading and creating
        VBox buttons = new VBox();
        HBox loadButtons = new HBox();
        calendarCreator.setPrefSize(200, 100);
        calendarLoader.setPrefSize(100, 100);
        xmlLoader.setPrefSize(100, 100);
        calendarCreator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CalendarController CalendarController = new CalendarController();
                XmlController xmlController = new XmlController();
                try {
                    xmlController.createXML();
                    CalendarController.createCalendar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        calendarLoader.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    loadFile("cal");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        xmlLoader.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    loadFile("xml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        loadButtons.getChildren().addAll(calendarLoader, xmlLoader);

        //accomplish the view of left part
        buttons.getChildren().addAll(calendarCreator, loadButtons);
        VBox leftView = new VBox();
        leftView.getChildren().addAll(buttons, menuList);

        //add box for adding new event
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
                    XmlController.insertTodoUid(currentMenu, currentFilter, vEvent);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MapHelper.insertEvent(vEvent, currentMenu, currentFilter);
                updateEventList();
                nameInput.clear();
                dateInput.clear();
            }
        });

        HBox newEventBox = new HBox();
        newEventBox.setPadding(new Insets(15, 12, 15, 10));
        newEventBox.setSpacing(10);
        newEventBox.getChildren().addAll(infoBox, buttonStack);
        HBox.setHgrow(buttonStack, Priority.ALWAYS);

        //accomplish the view of right part
        VBox eventBox = new VBox();
        eventBox.setMinSize(400, 400);
        eventBox.getChildren().addAll(eventListView, newEventBox);

        //put two part together
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
        List vEventList = MapHelper.getEventListByFilterName(currentMenu, currentFilter);
        ObservableList eventList = FXCollections.observableArrayList(vEventList);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(new Callback<ListView<VEvent>, ListCell<VEvent>>() {
            @Override
            public ListCell<VEvent> call(ListView<VEvent> param) {
                return new Event();
            }
        });
    }

    private void updateMenuList() {
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

    private void loadFile(String type) throws IOException {
        String filePath = null;
        if (type.equals("cal")) {
            filePath = ConfigHelper.getCalendarPath();
        } else if (type.equals("xml")) {
            filePath = ConfigHelper.getXMLPath();
        }
        FileChooser calendarChooser = new FileChooser();
        File file = calendarChooser.showOpenDialog(new Stage());

        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        byte temp[] = new byte[fileInputStream.available()];
        fileInputStream.read(temp);

        //clear origin file
        fileOutputStream.write(new String("").getBytes());
        fileOutputStream.flush();

        //write new data
        fileOutputStream.write(temp);
        fileOutputStream.flush();

        fileInputStream.close();
        fileOutputStream.close();
    }

    private void checkContain(VBox vBox1, VBox vBox2, VBox vBox3, HBox newFilterBox) {
        if (vBox1.getChildren().contains(newFilterBox)) {
            vBox1.getChildren().remove(newFilterBox);
        }
        if (vBox2.getChildren().contains(newFilterBox)) {
            vBox2.getChildren().remove(newFilterBox);
        }
        if (vBox3.getChildren().contains(newFilterBox)) {
            vBox3.getChildren().remove(newFilterBox);
        }

    }
}
