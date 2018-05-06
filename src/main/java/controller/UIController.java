package controller;

import entity.Maps;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author trafalgar
 */
public class UIController {

    Maps maps;

    @FXML
    public void handlerBtnClick(ActionEvent event) {
        Button btnSource = (Button) event.getSource();
        btnSource.setText("I am clicked!");
    }
}
