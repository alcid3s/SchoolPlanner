package gui.tabs;

import data.Group;
import data.Lesson;
import data.Schedule;
import data.persons.Teacher;
import data.rooms.Classroom;
import data.rooms.Room;
import gui.popups.EditGroupsPopup;
import gui.popups.EditLessonsPopup;
import gui.popups.EditTeachersPopup;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.time.LocalDateTime;

public class ScheduleTab extends Tab {
    private ObservableList list;
    private Schedule schedule;
    private BorderPane mainPane;
    private Canvas canvas;

    private final int size = 114;
    private final int factor = 2;

    public ScheduleTab() {
        super("Schedule");
        setClosable(false);


        mainPane = new BorderPane();
        System.out.println(mainPane.getWidth() + " and " + this.mainPane.getHeight());
        canvas = new Canvas();

        if (mainPane.getHeight() == 0 || mainPane.getWidth() == 0) {
            canvas.setWidth(1920);
            canvas.setHeight(910);
        } else {
            canvas.setWidth(mainPane.getWidth());
            canvas.setHeight(mainPane.getHeight());
        }

        mainPane.setTop(canvas);

        DrawFrame(new FXGraphics2D(canvas.getGraphicsContext2D()));

        this.schedule = Schedule.getInstance();
        Button editTeachers = getDefaultButton("Edit Teachers", 50, 100);
        editTeachers.setOnMouseClicked(e -> {
            new EditTeachersPopup().show();
        });
        Button editGroups = getDefaultButton("Edit Groups", 50,100);
        editGroups.setOnAction(e -> {
            new EditGroupsPopup().show();
        });
        Button editLessons = getDefaultButton("Edit Lessons", 50, 100);
        editLessons.setOnAction(e -> {
            new EditLessonsPopup().show();
        });

        HBox hBox = new HBox(editTeachers, editGroups, editLessons);

        mainPane.setBottom(hBox);
        setContent(mainPane);

        // Testing purposes
        Lesson lesson = new Lesson("IPJ",
                new Classroom("LA134", 10),
                new Teacher("Pieter"),
                new Group("B1", 6),
                LocalDateTime.of(2022,  2, 14, 12, 0),
                LocalDateTime.of(2022, 2, 14, 13, 15));

        DrawLesson(lesson, new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    private void DrawLesson(Lesson lesson, FXGraphics2D graphics){

        // Paramaters for the
        int xStart = 100 + (((lesson.getStartDate().getHour() - 8) * this.size) * factor) + (lesson.getStartDate().getMinute() * (this.size / 3));
        int yStart = 40;
        int xEnd = 100 + (((lesson.getEndDate().getHour() - 8) * this.size) * factor) + (lesson.getEndDate().getMinute() * (this.size / 28)) - xStart;
        int yEnd = 200;

        graphics.draw(new Rectangle(xStart, yStart, xEnd, yEnd));


        graphics.drawString(lesson.getStartDate().getHour().slice);
    }

    /*
    Draws rectangles for time indication.
     */
    private void DrawFrame(FXGraphics2D graphics) {
        String[] temporaryTeacherList = {"Johan", "Etienne", "Maurice", "Joli", "Bart", "Jan", "Jessica"};
        String[] temporaryTimeList = {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00- 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00"};

        java.awt.Font font = new Font("Verdana", 16, 20);


        graphics.draw(new Rectangle(0, 0, 100, 40));
        graphics.setFont(font);

        graphics.drawString("Teachers", 0, 30);

        for (int i = 0; i < 8; i++) {

            // horizontal and vertical rectangles
            graphics.draw(new Rectangle((i * this.size * this.factor) + 100, 0, this.size * this.factor, 40));
            graphics.draw(new Rectangle(0, 40, 100, i * (this.size - 20) * this.factor));

            // teacher list
            if (i != 0) {
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
