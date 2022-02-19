package gui.tabs;

import data.Group;
import data.Lesson;
import data.Schedule;
import gui.Util;
import gui.popups.EditGroupsPopup;
import gui.popups.EditLessonsPopup;
import gui.popups.EditTeachersPopup;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTab extends Tab{
    private final int size = 114;
    private final int factor = 2;
    private Schedule schedule;
    private BorderPane mainPane;
    private Canvas canvas;
    private static ScheduleTab tab;

    public ScheduleTab(Stage stage){
        super("Schedule");
        setClosable(false);

        tab = this;

        mainPane = new BorderPane();
        canvas = new Canvas();

        if(mainPane.getHeight() == 0 || mainPane.getWidth() == 0){
            canvas.setWidth(1920);
            canvas.setHeight(935);
        }else{
            canvas.setWidth(mainPane.getWidth());
            canvas.setHeight(mainPane.getHeight());
        }

        mainPane.setTop(canvas);

        this.schedule = Schedule.getInstance();

        refreshCanvas();

        int scale = 1920 / 6;

        Button editTeachers = Util.getDefaultButton("Edit Teachers", 50, scale);
        editTeachers.setOnMouseClicked(e -> new EditTeachersPopup().show());

        Button editGroups = Util.getDefaultButton("Edit Groups", 50, scale);
        editGroups.setOnAction(e -> new EditGroupsPopup().show());

        Button editLessons = Util.getDefaultButton("Edit Lessons", 50, scale);
        editLessons.setOnAction(e -> new EditLessonsPopup().show());

        Button refresh = Util.getDefaultButton("Refresh", 50, scale);
        refresh.setOnAction(e -> refreshCanvas());

        Button save = Util.getDefaultButton("Save Rooster", 50,scale);
        save.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Location to save");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Rooster","*.rooster"), new FileChooser.ExtensionFilter("JSON", "*.json"));
            File saveLocation = fileChooser.showSaveDialog(stage);
            if(saveLocation != null) {
                System.out.println(saveLocation.getAbsolutePath());
                Schedule.getInstance().save(saveLocation);
            }
            else
                new Alert(Alert.AlertType.ERROR, "Could not find file.").show();
        });

        Button load = Util.getDefaultButton("Load Rooster", 50, scale);
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File to load");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Rooster","*.rooster"), new FileChooser.ExtensionFilter("JSON", "*.json"));
            File loadLocation = fileChooser.showOpenDialog(stage);
            if(loadLocation != null) {
                System.out.println(loadLocation.getAbsolutePath());
                Schedule.getInstance().load(loadLocation);
            }
            else
                new Alert(Alert.AlertType.ERROR, "Could not find file.").show();
        });

        HBox hBox = new HBox(editTeachers, editGroups, editLessons, refresh,save,load);

        mainPane.setBottom(hBox);
        setContent(mainPane);

    }

    private void DrawLesson(Lesson lesson, FXGraphics2D graphics){
        List<Group> groups = Schedule.getInstance().getGroupList();

        int groupLocation = 0;
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getName().equals(lesson.getGroup().getName())) {
                groupLocation = i;
                break;
            }
        }

        final int startHour = lesson.getStartDate().getHour();
        final int startMinute = lesson.getStartDate().getMinute();
        final int endHour = lesson.getEndDate().getHour();
        final int endMinute = lesson.getEndDate().getMinute();

        final int height = 148;
        // Paramaters for the class block.
        final int xStart = 100 + (((startHour - 8) * this.size) * factor) + (startMinute * (this.size / 28));
        final int yStart = 40 + (height * (groupLocation)) + 40 * groupLocation;
        final int xWidth = 100 + (((endHour - 8) * this.size) * factor) + (endMinute * (this.size / 28)) - xStart;
        final int yWidth = (yStart + height) / ((groupLocation + 1));

        Rectangle rectangle = new Rectangle(xStart, yStart, xWidth, yWidth);
        graphics.draw(rectangle);

        int textLocation = (rectangle.width / 2) + xStart - (xStart / 8);

        graphics.drawString(leadingZero(startHour) + ":" + leadingZero(startMinute) + " - "
                + leadingZero(endHour) + ":" + leadingZero(endMinute), textLocation, yStart + 30);
        graphics.drawString(lesson.getName(), textLocation, yStart + 60);
        graphics.drawString(lesson.getGroup().getName(), textLocation, yStart + 90);
        graphics.drawString(lesson.getRoom().getName(), textLocation, yStart + 120);
        graphics.drawString(lesson.getTeacher().getName(), textLocation, yStart + 150);
    }

    private String leadingZero(int num){
        return num < 10 ? "0" + num : num + "";
    }

    /*
    Draws rectangles for time indication.
     */
    private void DrawFrame(FXGraphics2D graphics){
        List<String> array = new ArrayList<>();
        for(Group group : Schedule.getInstance().getGroupList()){
            array.add(group.getName());
        }

        String[] temporaryTimeList = {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00- 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00"};

        java.awt.Font font = new Font("Verdana", 16, 20);

        graphics.draw(new Rectangle(0, 0, 100, 40));
        graphics.setFont(font);

        graphics.drawString("Groups", 0, 30);

        for(int i = 0; i < 8; i++){

            // horizontal and vertical rectangles
            graphics.draw(new Rectangle((i * this.size * this.factor) + 100, 0, this.size * this.factor, 40));
            graphics.draw(new Rectangle(0, 40, 100, i * (this.size - 20) * this.factor));

            // teacher list
            if(i != 0 && i <= array.size()){
                graphics.drawString(array.get(i - 1), 0, i * 170);
            }

            // time list
            graphics.drawString(temporaryTimeList[i], 130 + (i * 230), 30);
        }
    }

    private Canvas getCanvas(){
        return canvas;
    }

    public static void refreshCanvas() {
        FXGraphics2D graphics = new FXGraphics2D(tab.getCanvas().getGraphicsContext2D());
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)tab.getCanvas().getWidth(), (int)tab.getCanvas().getHeight());
        tab.DrawFrame(graphics);

        for (Lesson lesson : tab.schedule.getLessonList()) {
            tab.DrawLesson(lesson, graphics);
        }
    }
}