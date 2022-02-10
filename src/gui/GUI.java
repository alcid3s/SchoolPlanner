package gui;

import data.Group;
import data.rooms.Classroom;
import data.rooms.Room;
import gui.tabs.ScheduleTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group("23TIVT1B1", 12);
        Room room = new Classroom("LA209", 12);

        Tab tab = new Tab();
        TabPane tabs = new TabPane(tab);
        tabs.getTabs().add(new ScheduleTab());
        tabs.setPrefHeight(800);
        tabs.setPrefWidth(1000);
        tabs.autosize();

        Scene scene = new Scene(tabs);
        primaryStage.setTitle("SchoolPlanner");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
