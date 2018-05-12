package view;

import com.sun.xml.internal.bind.XmlAccessorFactory;
import com.sun.xml.internal.bind.v2.TODO;
import controller.MapController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.XMLGenerator;

import java.lang.reflect.Array;
import java.util.Set;

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

        Object[] labelFilters = XMLGenerator.getFilterNamesByMenuName("label").toArray();
        Object[] projectFilters = XMLGenerator.getFilterNamesByMenuName("project").toArray();
        Object[] priorityFilters = XMLGenerator.getFilterNamesByMenuName("priority").toArray();

        ObservableList<Object> labelItems =FXCollections.observableArrayList (labelFilters);
        ObservableList<Object> projectItems =FXCollections.observableArrayList (projectFilters);
        ObservableList<Object> priorityItems =FXCollections.observableArrayList (priorityFilters);

        labelList.setItems(labelItems);
        projectList.setItems(projectItems);
        priorityList.setItems(priorityItems);

        TitledPane label = new TitledPane("Label",labelList);
        TitledPane project = new TitledPane("Project",projectList);
        TitledPane priority = new TitledPane("Priority",priorityList);

        Accordion leftView = new Accordion();
        leftView.getPanes().addAll(label,project,priority);

        HBox root = new HBox();
        root.getChildren().addAll(leftView, eventList);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
