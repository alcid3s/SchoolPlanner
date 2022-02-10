package gui.tabs;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ScheduleTab extends Tab {
    private ObservableList list;

    public ScheduleTab() {
        super("Schedule");
        Button editTeachers = getDefaultButton("Edit Teachers", 100,100);
        Button editGroups = getDefaultButton("Edit Groups", 100,100);

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
