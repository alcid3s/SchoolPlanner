package gui.tabs;

import data.Schedule;
import gui.popups.EditGroupsPopup;
import gui.popups.EditTeachersPopup;
import javafx.collections.ObservableList;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.RandomAccess;

public class ScheduleTab extends Tab {
    private ObservableList list;
    private Schedule schedule;
    private BorderPane mainPane;
    private Canvas canvas;

    public ScheduleTab() {
        super("Schedule");
        setClosable(false);

        canvas = new Canvas();
        mainPane = new BorderPane();

        mainPane.setTop(canvas);

        Group timeBlockGroup = new Group();
        Group timeTextGroup = new Group();
        final int size = 50;
        for (int i = 0; i < 12; i++) {
            timeBlockGroup.getChildren().add(new Rectangle(i * size, 0, size, size));
            timeTextGroup.getChildren().add(new Text(i * size, 0, "TIME HERE"));
        }

        this.schedule = Schedule.getInstance();
        Button editTeachers = getDefaultButton("Edit Teachers", 50,100);
        editTeachers.setOnMouseClicked(e -> {
            new EditTeachersPopup().show();
        });
        Button editGroups = getDefaultButton("Edit Groups", 50,100);
        editGroups.setOnAction(e -> {
            new EditGroupsPopup().show();
        });


        HBox hBox = new HBox(editTeachers, editGroups);

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
