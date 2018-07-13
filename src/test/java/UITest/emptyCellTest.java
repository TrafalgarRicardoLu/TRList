package UITest;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import view.emptyCell;


public class emptyCellTest extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<String> strList = FXCollections.observableArrayList("红色","黄色","绿色");

        ListView<String> eventList = new ListView<>();
        eventList.setMinWidth(400);
        eventList.setItems(strList);
        eventList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new emptyCell();
            }
        });

        HBox root = new HBox();
        root.getChildren().addAll(eventList);

        Scene scene = new Scene(root, 600, 400);


        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}

