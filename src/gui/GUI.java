package gui;

import data.tilted.TiledMap;
import gui.tabs.ScheduleTab;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        TiledMap tiltedMap = new TiledMap("School_Map.json");
    }


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

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
        });
    }
}
