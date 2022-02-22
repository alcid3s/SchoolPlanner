package gui;

import gui.tabs.ScheduleTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.awt.*;

public class GUI extends Application{
    @Override
    public void start(Stage primaryStage){
        TabPane tabs = new TabPane();
        tabs.getTabs().add(new ScheduleTab(primaryStage));
        tabs.setPrefHeight(1017);
        tabs.setPrefWidth(1920);
        tabs.autosize();
        Scene scene = new Scene(tabs);
        primaryStage.setTitle("SchoolPlanner");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
