package gui;

import data.Schedule;
import gui.tabs.ScheduleTab;
import gui.tabs.SimulationTab;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import managers.Names;

import java.awt.*;
import java.io.File;

public class GUI extends Application {
    private static Scene scene;
    private static TabPane tabs;

    public static void main(String[] args) {
        launch(GUI.class);
    }


    @Override
    public void start(Stage primaryStage) {
        tabs = new TabPane();
        scene = new Scene(tabs);
        SimulationTab simulationTab = new SimulationTab();
        tabs.getTabs().add(new ScheduleTab(primaryStage));
        tabs.getTabs().add(simulationTab);
        tabs.setPrefHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50);
        tabs.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        tabs.autosize();
        Names.init();

        primaryStage.setTitle("SchoolPlanner");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        primaryStage.show();

        try{Schedule.getInstance().load(new File("resources/autosave.json"));}catch(Exception e){System.out.println(e.getMessage());}

        primaryStage.setOnCloseRequest(e -> {
            try{Schedule.getInstance().save(new File("resources/autosave.json"));}catch(Exception f){System.out.println(f.getMessage());}
            Platform.exit();
        });
    }

    public static TabPane getTabPane() {
        return tabs;
    }

    public static Scene getScene() {
        return scene;
    }
}