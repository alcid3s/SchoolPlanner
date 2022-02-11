package gui;

import gui.tabs.ScheduleTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class GUI extends Application{
    @Override
    public void start(Stage primaryStage) {
        TabPane tabs = new TabPane();
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
