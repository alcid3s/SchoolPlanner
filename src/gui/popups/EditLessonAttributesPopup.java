package gui.popups;

import data.Group;
import data.Lesson;
import data.Schedule;
import data.persons.Person;
import data.rooms.Classroom;
import data.rooms.Room;
import gui.Util;
import gui.Validation;
import gui.tabs.ScheduleTab;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EditLessonAttributesPopup extends Stage {

    public EditLessonAttributesPopup(Lesson lesson) {
        TextField nameField = new TextField();
        nameField.setText(lesson.getName());
        ArrayList<Room> roomList = new ArrayList<>();
        for (Room r : Schedule.getInstance().getRoomList()) {
            if (r instanceof Classroom)
                roomList.add(r);
        }
        ComboBox roomBox = new ComboBox(FXCollections.observableArrayList(roomList));
        roomBox.getSelectionModel().select(lesson.getRoom());
        ComboBox teacherBox = new ComboBox(FXCollections.observableList(Schedule.getInstance().getTeacherList()));
        teacherBox.getSelectionModel().select(lesson.getTeacher());
        ComboBox groupBox = new ComboBox(FXCollections.observableList(Schedule.getInstance().getGroupList()));
        groupBox.getSelectionModel().select(lesson.getGroup());
        ComboBox startHourBox = new ComboBox(Util.getHoursInList());
        ComboBox startMinuteBox = new ComboBox(Util.getMinutesInList());
        ComboBox endHourBox = new ComboBox(Util.getHoursInList());
        ComboBox endMinuteBox = new ComboBox(Util.getMinutesInList());
        startHourBox.setEditable(true);
        startMinuteBox.setEditable(true);
        endHourBox.setEditable(true);
        endMinuteBox.setEditable(true);

        endMinuteBox.getSelectionModel().select(Util.timeInString(lesson.getEndDate().getMinute()));
        endHourBox.getSelectionModel().select(Util.timeInString(lesson.getEndDate().getHour()));
        startMinuteBox.getSelectionModel().select(Util.timeInString(lesson.getStartDate().getMinute()));
        startHourBox.getSelectionModel().select(Util.timeInString(lesson.getStartDate().getHour()));
        roomBox.setPrefWidth(220);
        teacherBox.setPrefWidth(220);
        groupBox.setPrefWidth(220);


        GridPane gridpane = new GridPane();
        gridpane.add(new Label("Name: "), 0, 0);
        gridpane.add(nameField, 1, 0);
        gridpane.add(new Label("Room: "), 0, 1);
        gridpane.add(roomBox, 1, 1);
        gridpane.add(new Label("Teacher: "), 0, 2);
        gridpane.add(teacherBox, 1, 2);
        gridpane.add(new Label("Group: "), 0, 3);
        gridpane.add(groupBox, 1, 3);
        gridpane.add(new Label("Start Time: "), 0, 4);
        gridpane.add(startHourBox, 1, 4);
        gridpane.add(startMinuteBox, 2, 4);
        gridpane.add(new Label("End Time: "), 0, 5);
        gridpane.add(endHourBox, 1, 5);
        gridpane.add(endMinuteBox, 2, 5);

        Button save = Util.getDefaultButton("Save changes", 50, 100);
        save.setOnAction(e -> {
            boolean editTime = false;
            boolean mayClose = true;
            LocalDateTime startTime = lesson.getStartDate();
            LocalDateTime endTime = lesson.getEndDate();

            if (!(startMinuteBox.getValue() == null)) {
                startTime = Util.makeTime("" + startTime.getHour(), startMinuteBox.getValue().toString());
                editTime = true;
            }
            if (!(startHourBox.getValue() == null)) {
                startTime = Util.makeTime(startHourBox.getValue().toString(), "" + startTime.getMinute());
                editTime = true;
            }
            if (!(endMinuteBox.getValue() == null)) {
                endTime = Util.makeTime("" + endTime.getHour(), endMinuteBox.getValue().toString());
                editTime = true;
            }
            if (!(endHourBox.getValue() == null)) {
                endTime = Util.makeTime(endHourBox.getValue().toString(), "" + endTime.getMinute());
                editTime = true;
            }

            if (Validation.timeIsValid(startTime, endTime) && Validation.scheduleIsAvailable(startTime, endTime, lesson) && editTime) {
                lesson.setStartDate(startTime);
                lesson.setEndDate(endTime);
            } else {
                mayClose = false;
            }

            if (!nameField.getText().isEmpty()) {
                lesson.setName(nameField.getText());
            }
            if (!(roomBox.getValue() == null)) {
                Room room = Schedule.getInstance().getRoom(roomBox.getValue().toString());
                if (Validation.scheduleIsAvailable(lesson.getStartDate(), lesson.getEndDate(), room, lesson.getRoom()) && Validation.isClassRoom(room) && Validation.sizeIsValid(room, lesson.getGroup())) {
                    lesson.setRoom(room);
                } else {
                    mayClose = false;
                }
            }
            if (!(teacherBox.getValue() == null)) {
                Person teacher = Schedule.getInstance().getTeacher(teacherBox.getValue().toString());
                if (Validation.scheduleIsAvailable(lesson.getStartDate(), lesson.getEndDate(), teacher, lesson.getTeacher())) {
                    lesson.setTeacher(teacher);
                } else {
                    mayClose = false;
                }
            }
            if (!(groupBox.getValue() == null)) {
                Group group = Schedule.getInstance().getGroup(groupBox.getValue().toString());
                if (Validation.scheduleIsAvailable(lesson.getStartDate(), lesson.getEndDate(), group, lesson.getGroup()) && Validation.sizeIsValid(lesson.getRoom(), group)) {
                    lesson.setGroup(group);
                } else {
                    mayClose = false;
                }
            }

            if (mayClose) {
                ScheduleTab.refreshCanvas();
                new EditLessonsPopup().show();
                close();
            } else {
                new Alert(Alert.AlertType.ERROR, Validation.getMessage()).show();
            }

        });

        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        cancel.setOnAction(e -> {
            new EditLessonsPopup().show();
            close();
        });

        BorderPane pane = new BorderPane();
        pane.setTop(gridpane);
        pane.setBottom(new HBox(save, cancel));

        Scene scene = new Scene(pane);
        setTitle("Edit Attributes");
        setScene(scene);
    }
}
