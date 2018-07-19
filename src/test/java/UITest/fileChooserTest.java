package UITest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;


public class fileChooserTest extends Application {

    Button button=new Button("TestFile");

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                System.out.println(file.getName());
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] temp = new byte[2048];
                    while (fileInputStream.read(temp)!=-1){
                        System.out.println(new String(temp));
                    }
                    fileInputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().add(button);

        primaryStage.setScene(new Scene(hBox));
        primaryStage.show();

    }
}
