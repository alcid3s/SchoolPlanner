package gui.tabs;

import data.Schedule;
import gui.popups.EditTeachersPopup;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import sun.net.www.content.text.Generic;

public class ScheduleTab extends Tab {
    private ObservableList list;
    private Schedule schedule;

    public ScheduleTab() {
        super("Schedule");
        this.schedule = Schedule.getInstance();
        Button editTeachers = getDefaultButton("Edit Teachers", 50,100);
        editTeachers.setOnMouseClicked(e -> {
            new EditTeachersPopup().show();
        });
        Button editGroups = getDefaultButton("Edit Groups", 50,100);


        ListView<Generic> list = new ListView<>();


        HBox hBox = new HBox(editTeachers, editGroups);

        BorderPane mainPane = new BorderPane();
        mainPane.setBottom(hBox);
        setContent(mainPane);
    }

    /*
    Creates default button with a name, height and width.
     */
    public Button getDefaultButton(String name, int height, int width) {
        Button b = new Button(name);
        b.setPrefHeight(height);
        b.setPrefWidth(width);

        return b;
    }



}
