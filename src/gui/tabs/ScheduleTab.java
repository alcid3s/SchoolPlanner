package gui.tabs;

import data.Schedule;
import gui.popups.EditGroupsPopup;
import gui.popups.EditTeachersPopup;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;

import javax.xml.soap.Text;
import java.awt.*;
import java.awt.geom.Area;

public class ScheduleTab extends Tab {
    private ObservableList list;
    private Schedule schedule;
    private BorderPane mainPane;
    private Canvas canvas;

    public ScheduleTab() {
        super("Schedule");

        mainPane = new BorderPane();

        System.out.println(mainPane.getWidth() + " and " + this.mainPane.getHeight());

        canvas = new Canvas();

        if(mainPane.getHeight() == 0 || mainPane.getWidth() == 0){
            canvas.setWidth(1000);
            canvas.setHeight(800);
        }else{
            canvas.setWidth(mainPane.getWidth());
            canvas.setHeight(mainPane.getHeight());
        }

        mainPane.setTop(canvas);

        System.out.println("Drawing...");
        DrawTime(new FXGraphics2D(canvas.getGraphicsContext2D()));
        System.out.println("Drew context");

        this.schedule = Schedule.getInstance();
        Button editTeachers = getDefaultButton("Edit Teachers", 50,100);
        editTeachers.setOnMouseClicked(e -> {
            new EditTeachersPopup().show();
        });
        Button editGroups = getDefaultButton("Edit Groups", 50,100);
        editTeachers.setOnAction(e -> {
            new EditGroupsPopup().show();
        });

        HBox hBox = new HBox(editTeachers, editGroups);

        mainPane.setBottom(hBox);
        setContent(mainPane);
    }

    private void DrawTime(FXGraphics2D graphics) {
        final int size = 100;
        final int factor = 2;
        for (int i = 0; i < 5; i++) {
            graphics.draw(new Rectangle(i * size * factor, 0, size * factor, 40));
        }
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
