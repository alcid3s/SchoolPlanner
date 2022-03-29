package gui.popups;

import data.Lesson;
import data.Schedule;
import data.rooms.Room;
import gui.Util;
import gui.Validation;
import gui.tabs.ScheduleTab;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlternativeRoomPopup extends Stage {

    private static String newOption = "";
    private static Lesson otherLesson = null;
    private static Room newRoom = null;

    public AlternativeRoomPopup(Lesson lesson, boolean add) {
        setTitle("Select different");
        Text txt = new Text(newOption);

        BorderPane pane = new BorderPane();
        pane.setTop(txt);
        Button yes = Util.getDefaultButton("Yes", 30, 200);
        Button no = Util.getDefaultButton("No", 30, 200);
        HBox box = new HBox(yes, no);
        pane.setBottom(box);

        yes.setOnAction(e -> {
            newOption = "";
            if (newRoom != null && otherLesson != null) {
                otherLesson.setRoom(newRoom);
            } else if (newRoom != null) {
                lesson.setRoom(newRoom);
            } else {
                new Alert(Alert.AlertType.ERROR, "Could not change room").show();
            }

            if (add) {
                Schedule.getInstance().addLesson(lesson);
            }

            newRoom = null;
            otherLesson = null;

            ScheduleTab.refreshCanvas();
            new EditLessonsPopup().show();
            close();
        });

        no.setOnAction(e -> {
            newOption = "";
            new EditLessonsPopup().show();
            close();
        });

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public static boolean checkForOptions(Lesson thisLesson) {
        for (Room room : Schedule.getInstance().getRoomList()) {
            if (Validation.isClassRoom(room) && Validation.sizeIsValid(room, thisLesson.getGroup()) && Validation.scheduleIsAvailable(thisLesson.getStartDate(), thisLesson.getEndDate(), thisLesson.getTeacher(), room, thisLesson.getGroup())) {
                newOption = "\n\tThis room is not available. However,\n\troom " + room + " is still available for this group.\t\n\tDo you want to use this room instead?\n";
                newRoom = room;
                return true;
            }
        }
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            for (Room room : Schedule.getInstance().getRoomList()) {
                if (thisLesson.getRoom().getName().equals(lesson.getRoom().getName()) && Validation.isClassRoom(room) && Validation.sizeIsValid(room, lesson.getGroup()) && Validation.scheduleIsAvailable(thisLesson.getStartDate(), thisLesson.getEndDate(), thisLesson.getTeacher(), room, thisLesson.getGroup())) {
                    System.out.println("Test");
                    otherLesson = lesson;
                    newOption = "\n\tThis room is unavailable for this group. However,\n\troom " + room + " is still available for " + lesson.getGroup() + ".\t\n\tDo you want to use this room for this group?\n";
                    newRoom = room;
                    return true;
                }
            }
        }
        return false;
    }
}
