package view;

import com.sun.xml.internal.bind.v2.TODO;
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

/**
 * @author trafalgar
 */
public class Main extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ListView eventList = new ListView();

        ListView labelList = new ListView();
        ListView projectList = new ListView();
        ListView priorityList = new ListView();

        /*
        /TODO
        /read map info and put it to List
        */

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
