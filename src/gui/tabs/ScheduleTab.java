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

import java.awt.*;

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
            canvas.setWidth(1920);
            canvas.setHeight(1080);
        }else{
            canvas.setWidth(mainPane.getWidth());
            canvas.setHeight(mainPane.getHeight());
        }

        mainPane.setTop(canvas);

        DrawTime(new FXGraphics2D(canvas.getGraphicsContext2D()));

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

    /*
    Draws rectangles for time indication.
     */
    private void DrawTime(FXGraphics2D graphics) {
        String[] temporaryTeacherList = {"Johan", "Etienne", "Maurice", "Joli", "Bart", "Jan", "Jessica"};
        String[] temporaryTimeList = {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00- 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00"};

        java.awt.Font font = new Font("Verdana", 16, 20);


        //TODO: 10-02-2022 add list of teachers to the left side.
        graphics.draw(new Rectangle(0, 0, 100, 40));
        graphics.setFont(font);

        graphics.drawString("Teachers", 0, 30);

        final int size = 114;
        final int factor = 2;
        for (int i = 0; i < 8; i++) {

            // horizontal and vertical rectangles
            graphics.draw(new Rectangle((i * size * factor) + 100, 0, size * factor, 40));
            graphics.draw(new Rectangle(0, 40, 100, i * (size - 20) * factor));

            // teacher list
            if(i != 0){
                graphics.drawString(temporaryTeacherList[i - 1], 0, i * 170);
            }

            // time list
            graphics.drawString(temporaryTimeList[i], 130 + (i * 230), 30);
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
