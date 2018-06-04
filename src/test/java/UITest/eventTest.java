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
import net.fortuna.ical4j.data.CalendarParser;
import net.fortuna.ical4j.data.CalendarParserImpl;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import view.Event;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;


public class eventTest extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 起始时间是：2008 年 4 月 1 日 上午 9 点
        java.util.Calendar startDate = new GregorianCalendar();
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        startDate.set(java.util.Calendar.YEAR, 2008);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        // 结束时间是：2008 年 4 月 1 日 下午 1 点
        java.util.Calendar endDate = new GregorianCalendar();
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        endDate.set(java.util.Calendar.YEAR, 2008);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 13);
        endDate.set(java.util.Calendar.MINUTE, 0);
        endDate.set(java.util.Calendar.SECOND, 0);

        // 创建事件
        String eventName = "Progress Meeting";
        DateTime start = new DateTime(startDate.getTime());
        DateTime end = new DateTime(endDate.getTime());

        VEvent vEvent = new VEvent(start, end, eventName);
        Event event = new Event();


        List<VEvent> vEvents = new LinkedList<>();
        vEvents.add(vEvent);
        ObservableList<VEvent> eventObservableList;
        eventObservableList=FXCollections.observableList(vEvents);

        ListView<VEvent> eventList = new ListView<>();
        eventList.setMinWidth(400);
        eventList.setItems(eventObservableList);
        eventList.setCellFactory(new Callback<ListView<VEvent>, ListCell<VEvent>>() {
            @Override
            public ListCell<VEvent> call(ListView<VEvent> param) {
                return new Event();
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