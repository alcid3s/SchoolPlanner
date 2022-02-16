package gui.tabs;

import data.Lesson;
import data.Schedule;
import gui.Util;
import gui.popups.EditGroupsPopup;
import gui.popups.EditLessonsPopup;
import gui.popups.EditTeachersPopup;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ScheduleTab extends Tab{
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
            canvas.setHeight(935);
        } else {
            canvas.setWidth(mainPane.getWidth());
            canvas.setHeight(mainPane.getHeight());
        }

        mainPane.setTop(canvas);

        this.schedule = Schedule.getInstance();

        refreshCanvas();

        int scale = 1920 / 4;

        Button editTeachers = Util.getDefaultButton("Edit Teachers", 50, scale);
        editTeachers.setOnMouseClicked(e -> new EditTeachersPopup().show());

        Button editGroups = Util.getDefaultButton("Edit Groups", 50,scale);
        editGroups.setOnAction(e -> new EditGroupsPopup().show());

        Button editLessons = Util.getDefaultButton("Edit Lessons", 50, scale);
        editLessons.setOnAction(e -> new EditLessonsPopup(this).show());

        Button refresh = Util.getDefaultButton("Refresh", 50, scale);
        refresh.setOnAction(e -> refreshCanvas());

        HBox hBox = new HBox(editTeachers, editGroups, editLessons, refresh);

        mainPane.setBottom(hBox);
        setContent(mainPane);

        // Testing purposes
//        Lesson lesson = new Lesson("IPJ",
//                new Classroom("LA134", 10),
//                new Teacher("Pieter"),
//                new Group("B1", 6),
//                LocalDateTime.of(2022, 2, 14, 8, 0),
//                LocalDateTime.of(2022, 2, 14, 10, 8));
    }

    private void DrawLesson(Lesson lesson, FXGraphics2D graphics) {
        final int startHour = lesson.getStartDate().getHour();
        final int startMinute = lesson.getStartDate().getMinute();
        final int endHour = lesson.getEndDate().getHour();
        final int endMinute = lesson.getEndDate().getMinute();

        // Paramaters for the
        final int xStart = 100 + (((startHour - 8) * this.size) * factor) + (startMinute * (this.size / 3));
        final int yStart = 40;
        final int xEnd = 100 + (((lesson.getEndDate().getHour() - 8) * this.size) * factor) + (lesson.getEndDate().getMinute() * (this.size / 28)) - xStart;
        final int yEnd = 180;

        graphics.draw(new Rectangle(xStart, yStart, xEnd, yEnd));


        //TODO: 11-02-2022 Create formula so the time is always in the middle
        graphics.drawString(leadingZero(startHour) + ":" + leadingZero(startMinute) + " - "
                + leadingZero(endHour) + ":" + leadingZero(endMinute), xStart + 50, yStart + 30);

        graphics.drawString(lesson.getName(), xStart + 100, yStart + 60);

        //TODO: 11-02-2022 Create fornmula so group is always in the middle
        graphics.drawString(lesson.getGroup().getName(), xStart + 100, yStart + 90);

        //TODO: 11-02-2022 Create formula so room is always in the middle, also create a .getname function in Room.
        graphics.drawString(lesson.getRoom().getName(), xStart + 100, yStart + 120);

        graphics.drawString(lesson.getTeacher().getName(), xStart + 100, yStart + 150);
    }

    private String leadingZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return num + "";
        }
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

    public void refreshCanvas(){
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        DrawFrame(graphics);

        for(Lesson lesson : schedule.getLessonList()){
            DrawLesson(lesson, graphics);
        }
    }
}