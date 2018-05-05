package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author trafalgar
 */
public class MainController {
    @FXML
    public void handlerBtnClick(ActionEvent event) {
        Button btnSource = (Button) event.getSource();
        btnSource.setText("I am clicked!");
    }
}
