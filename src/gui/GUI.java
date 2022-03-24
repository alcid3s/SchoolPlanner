package gui;

import data.Names;
import gui.tabs.ScheduleTab;
import gui.tabs.SimulationTab;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.awt.*;

public class GUI extends Application {

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

        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }
}
