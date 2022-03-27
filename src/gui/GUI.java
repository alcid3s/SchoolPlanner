package gui;

import data.Schedule;
import gui.tabs.ScheduleTab;
import gui.tabs.SimulationTab;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.Serializable;

public class GUI extends Application{

    public static void main(String[] args) {
       launch(GUI.class);
    }


    @Override
    public void start(Stage primaryStage){
        TabPane tabs = new TabPane();
        SimulationTab simulationTab = new SimulationTab();
        tabs.getTabs().add(new ScheduleTab(primaryStage));
        tabs.getTabs().add(simulationTab);
        tabs.setPrefHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-50);
        tabs.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        tabs.autosize();
        Scene scene = new Scene(tabs);
        primaryStage.setTitle("SchoolPlanner");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        primaryStage.show();

        Schedule.getInstance().load(new File("resources/autosave.json"));

        primaryStage.setOnCloseRequest(e -> {
            Schedule.getInstance().save(new File("resources/autosave.json"));
            Platform.exit();
        });
    }
}
