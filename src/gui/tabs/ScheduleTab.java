package gui.tabs;

import data.Lesson;
import data.Schedule;
import data.rooms.Classroom;
import data.rooms.Room;
import managers.Util;
import gui.popups.grouppopup.EditGroupsPopup;
import gui.popups.lessonpopups.EditLessonsPopup;
import gui.popups.teacherpopups.EditTeachersPopup;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTab extends Tab {
    private static ScheduleTab tab;
    private final int size = 114;
    private final int factor = 2;
    private final ScrollPane pane;
    private final ResizableCanvas canvas;
    private static DrawState state;

    public ScheduleTab(Stage stage) {
        super("Schedule");
        setClosable(false);
        tab = this;
        state = DrawState.GROUP;

        BorderPane mainPane = new BorderPane();
        pane = new ScrollPane();
        pane.setPannable(true);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        canvas = new ResizableCanvas(g -> {
            drawFrame(g);
            Schedule.getInstance().getLessonList().forEach(l -> {
                drawLesson(l, g);
            });
        }, mainPane);
        pane.setContent(canvas);
        mainPane.setCenter(pane);


        refreshCanvas();

        int scale = (int) canvas.getWidth();

        Button editTeachers = Util.getDefaultButton("Edit Teachers", 50, scale);
        editTeachers.setOnMouseClicked(e -> new EditTeachersPopup().show());

        Button editGroups = Util.getDefaultButton("Edit Groups", 50, scale);
        editGroups.setOnAction(e -> new EditGroupsPopup().show());

        Button editLessons = Util.getDefaultButton("Edit Lessons", 50, scale);
        editLessons.setOnAction(e -> new EditLessonsPopup().show());

        Button refresh = Util.getDefaultButton("Refresh", 50, scale);
        refresh.setOnAction(e -> refreshCanvas());

        Button nextMode = Util.getDefaultButton("Change View", 50, scale);
        nextMode.setOnAction(e -> {
            if(state == DrawState.GROUP){
                state = DrawState.TEACHER;
            }else if(state == DrawState.TEACHER){
                state = DrawState.ROOM;
            }else if(state == DrawState.ROOM){
                state = DrawState.GROUP;
            }
            refreshCanvas();
        });

        Button save = Util.getDefaultButton("Save Rooster", 50, scale);
        save.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Location to save");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Rooster", "*.rooster"), new FileChooser.ExtensionFilter("JSON", "*.json"));
            File saveLocation = fileChooser.showSaveDialog(stage);
            if (saveLocation != null) {
                if (Schedule.getInstance().save(saveLocation)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "File Saved.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not save file.").show();
                }
            } else
                new Alert(Alert.AlertType.ERROR, "Could not find file.").show();
        });

        Button load = Util.getDefaultButton("Load Rooster", 50, scale);
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File to load");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Rooster", "*.rooster"), new FileChooser.ExtensionFilter("JSON", "*.json"));
            File loadLocation = fileChooser.showOpenDialog(stage);
            if (loadLocation != null) {
                if (Schedule.getInstance().load(loadLocation)) {

                    new Alert(Alert.AlertType.CONFIRMATION, "File Loaded.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not load file.").show();
                }
            } else
                new Alert(Alert.AlertType.ERROR, "Could not find file.").show();
        });

        HBox hBox = new HBox(editTeachers, editGroups, editLessons, refresh, nextMode,save, load);

        mainPane.setBottom(hBox);
        setContent(mainPane);
    }

    private void drawLesson(Lesson lesson, FXGraphics2D graphics) {


        int location = 0;
        if(state == DrawState.GROUP){
            for (int i = 0; i < Schedule.getInstance().getGroupList().size(); i++) {
                if (Schedule.getInstance().getGroupList().get(i).getName().equals(lesson.getGroup().getName())) {
                    location = i;
                    break;
                }
            }
        }else if(state == DrawState.TEACHER){
            for (int i = 0; i < Schedule.getInstance().getTeacherList().size(); i++) {
                if (Schedule.getInstance().getTeacherList().get(i).getName().equals(lesson.getTeacher().getName())) {
                    location = i;
                    break;
                }
            }
        }else if(state == DrawState.ROOM){
            List<Room> classrooms = new ArrayList<>();
            Schedule.getInstance().getRoomList().forEach(r -> {
                if(r instanceof Classroom){
                    classrooms.add(r);
                }
            });
            for (int i = 0; i < classrooms.size(); i++) {
                if (classrooms.get(i).getName().equals(lesson.getRoom().getName())) {
                    location = i;
                    break;
                }
            }
        }

        final int startHour = lesson.getStartDate().getHour();
        final int startMinute = lesson.getStartDate().getMinute();
        final int endHour = lesson.getEndDate().getHour();
        final int endMinute = lesson.getEndDate().getMinute();

        final int height = 148;
        // Parameters for the class block.
        final int xStart = 100 + (((startHour - 8) * this.size) * factor) + (startMinute * (this.size / 28));
        final int yStart = 40 + (height * (location)) + 40 * location;
        final int xWidth = 100 + (((endHour - 8) * this.size) * factor) + (endMinute * (this.size / 28)) - xStart;
        final int yWidth = 188;

        Rectangle rectangle = new Rectangle(xStart, yStart, xWidth, yWidth);
        graphics.draw(rectangle);

        int textLocation = (rectangle.width / 2) + xStart;

        //graphics.drawRect(textLocation, yStart+10, 150,25);
        if (lesson.notNull()) {
            String time = leadingZero(startHour) + ":" + leadingZero(startMinute) + " - " + leadingZero(endHour) + ":" + leadingZero(endMinute);
            graphics.drawString(time, textLocation - graphics.getFontMetrics().stringWidth(time) / 2, yStart + 30);
            graphics.drawString(lesson.getName(), textLocation - graphics.getFontMetrics().stringWidth(lesson.getName()) / 2, yStart + 60);
            graphics.drawString(lesson.getGroup().getName(), textLocation - graphics.getFontMetrics().stringWidth(lesson.getGroup().getName()) / 2, yStart + 90);
            graphics.drawString(lesson.getRoom().getName(), textLocation - graphics.getFontMetrics().stringWidth(lesson.getRoom().getName()) / 2, yStart + 120);
            graphics.drawString(lesson.getTeacher().getName(), textLocation - graphics.getFontMetrics().stringWidth(lesson.getTeacher().getName()) / 2, yStart + 150);
        }
    }

    private String leadingZero(int num) {
        return num < 10 ? "0" + num : num + "";
    }

    /*
    Draws rectangles for time indication.
     */
    private void drawFrame(FXGraphics2D graphics){


        String[] temporaryTimeList = {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00- 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00"};

        Font font = new Font("Verdana", 16, 17);

        graphics.draw(new Rectangle(0, 0, 100, 40));
        graphics.setFont(font);

        int a = Schedule.getInstance().getGroupList().size();

        for(int i = 0; i < temporaryTimeList.length; i++ ){
            graphics.draw(new Rectangle((i * this.size * this.factor) + 100, 0, this.size * this.factor, 40));
            graphics.drawString(temporaryTimeList[i], 130 + (i * 230), 30);
        }

        if(state == DrawState.GROUP){
            graphics.drawString("Groups", 0, 30);
            a = Schedule.getInstance().getGroupList().size();
            for(int i = 0; i < a; i++) {
                Rectangle2D r = new Rectangle(0, 40 + i*188, 100, 188);
                graphics.draw(r);
                graphics.drawString(Schedule.getInstance().getGroupList().get(i).getName(), 0, (int)r.getY() + 188/ 2);
            }
        }else if(state == DrawState.TEACHER){
            graphics.drawString("Teachers", 0, 30);
            a = Schedule.getInstance().getGroupList().size();
            for(int i = 0; i < a; i++) {
                Rectangle2D r = new Rectangle(0, 40 + i*188, 100, 188);
                graphics.draw(r);
                graphics.drawString(Schedule.getInstance().getTeacherList().get(i).getName(), 0, (int)r.getY() + 188/ 2);
            }
        }else if(state == DrawState.ROOM){
            graphics.drawString("Rooms", 0, 30);
            List<Room> classrooms = new ArrayList<>();
            for(Room r : Schedule.getInstance().getRoomList()){
                if(r instanceof Classroom){
                    classrooms.add(r);
                }
            }
            a = classrooms.size();
            for(int i = 0; i < a; i++) {
                Rectangle2D r = new Rectangle(0, 40 + i*188, 100, 188);
                graphics.draw(r);
                graphics.drawString(classrooms.get(i).getName(), 0, (int)r.getY() + 188/ 2);
            }
        }

        if(a > 4){
            pane.setMaxHeight(40 + 188*a);
            this.canvas.setHeight(40 + 188*a);
            pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
    }

    private Canvas getCanvas() {
        return canvas;
    }

    public static void refreshCanvas() {
        FXGraphics2D graphics = new FXGraphics2D(tab.getCanvas().getGraphicsContext2D());
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) tab.getCanvas().getWidth(), (int) tab.getCanvas().getHeight());
        tab.drawFrame(graphics);

        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            tab.drawLesson(lesson, graphics);
        }
    }

    public static DrawState getState(){
        return state;
    }
}