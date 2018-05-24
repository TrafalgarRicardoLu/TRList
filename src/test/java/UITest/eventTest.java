package UITest;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.Event;


public class eventTest extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        ListView<String > eventList = new ListView<String>();

        ObservableList<String> data = FXCollections.observableArrayList(
                "chocolate", "salmon", "gold", "coral", "darkorchid",
                "blueviolet", "brown");

        eventList.setItems(data);
        eventList.setCellFactory((ListView<String> i) -> new Event());

        HBox root = new HBox();
        root.getChildren().addAll(eventList);

        Scene scene = new Scene(root, 600, 400);


        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}