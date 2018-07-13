package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class emptyCell extends ListCell<String> {

    Button addButton = new Button("Add");
    Label nameLabel = new Label("Name: ");
    Label dateLabel = new Label("date: ");
    TextField nameInput = new TextField();
    TextField dateInput = new TextField();

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel,nameInput);

        HBox dateBox = new HBox();
        dateBox.getChildren().addAll(dateLabel,dateInput);

        VBox infoBox = new VBox();
        infoBox.getChildren().addAll(nameBox, dateBox);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        StackPane buttonStack = new StackPane();
        buttonStack.getChildren().addAll(addButton);
        buttonStack.setAlignment(Pos.CENTER_RIGHT);


        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
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
