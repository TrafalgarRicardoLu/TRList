package view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.MapHelper;

/**
 * @author trafalgar
 */
public class TRList extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ListView eventList = new ListView();

        ListView labelList = new ListView();
        ListView projectList = new ListView();
        ListView priorityList = new ListView();

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
                        eventList.setItems((ObservableList) MapHelper.getEventByFilterName("label", newValue));
                    }
                });

        projectList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        eventList.setItems((ObservableList) MapHelper.getEventByFilterName("project", newValue));
                    }
                });

        priorityList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        eventList.setItems((ObservableList) MapHelper.getEventByFilterName("priority", newValue));
                    }
                });

        labelList.setItems(labelItems);
        projectList.setItems(projectItems);
        priorityList.setItems(priorityItems);

        TitledPane label = new TitledPane("Label", labelList);
        TitledPane project = new TitledPane("Project", projectList);
        TitledPane priority = new TitledPane("Priority", priorityList);

        Accordion leftView = new Accordion();
        leftView.getPanes().addAll(label, project, priority);

        HBox root = new HBox();
        root.getChildren().addAll(leftView, eventList);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
