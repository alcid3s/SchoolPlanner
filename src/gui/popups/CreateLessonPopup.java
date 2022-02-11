package gui.popups;

import data.Group;
import data.Lesson;
import data.Schedule;
import data.persons.Teacher;
import data.rooms.Classroom;
import data.rooms.Room;
import gui.Util;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CreateLessonPopup extends Stage {

    public CreateLessonPopup(){
        //String name, Room room, Teacher teacher, Group group, LocalDateTime startDate, LocalDateTime endDate
        Schedule schedule = Schedule.getInstance();
        Label name = new Label("Name:");
        Label room = new Label("Room:");
        Label teacher = new Label("Teacher:");
        Label group = new Label("Group:");
        Label startTime = new Label("Start Time:");
        Label endTime = new Label("End Time:");

        TextField nameField = new TextField();
        ComboBox<String> roomField = new ComboBox<>();
        ArrayList<String> roomListView = new ArrayList<>();
        for(Room r : schedule.getRoomList()) {
            if(r instanceof Classroom) {
                roomListView.add(r.toString());
            }
        }
        roomField.setItems(FXCollections.observableArrayList(roomListView));
        ComboBox<String> teacherField = new ComboBox<>();
        ArrayList<String> teacherListView = new ArrayList<>();
        for(Teacher t : schedule.getTeacherList()) {
            teacherListView.add(t.toString());
        }
        teacherField.setItems(FXCollections.observableArrayList(teacherListView));
        ComboBox<String> groupField = new ComboBox<>();
        ArrayList<String> groupView = new ArrayList<>();
        for(Group g : schedule.getGroupList()) {
            groupView.add(g.toString());
        }
        groupField.setItems(FXCollections.observableArrayList(groupView));


        TextField startTimeField = new TextField();
        startTimeField.setText("yyyy-MM-dd HH:mm");
        TextField endTimeField = new TextField();
        endTimeField.setText("yyyy-MM-dd HH:mm");

        VBox box = new VBox();
        box.getChildren().add(new HBox(name, nameField));
        box.getChildren().add(new HBox(room, roomField));
        box.getChildren().add(new HBox(teacher, teacherField));
        box.getChildren().add(new HBox(group, groupField));
        box.getChildren().add(new HBox(startTime, startTimeField));
        box.getChildren().add(new HBox(endTime, endTimeField));

        Button create = Util.getDefaultButton("Create lesson", 50, 100);
        create.setOnAction(e -> {
            if(nameField.getText().length() > 0) {
                Teacher selectedTeacher = getSelectedTeacher(teacherField);
                Group selectedGroup = getSelectedGroup(groupField);
                Room selectedRoom = getSelectedRoom(roomField);
                if(selectedTeacher != null && selectedGroup != null && selectedRoom != null) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime selectedStartTime = LocalDateTime.parse(startTimeField.getText(), formatter); 
                        LocalDateTime selectedEndTime = LocalDateTime.parse(endTimeField.getText(), formatter);
                        if(selectedStartTime.isAfter(selectedEndTime)) {
                            new Alert(Alert.AlertType.ERROR, "Start time is after end time.").show();
                            return;
                        }
                        if(selectedGroup.getSize() > selectedRoom.getCapacity()) {
                            new Alert(Alert.AlertType.ERROR, "Too large group for selected room").show();
                            return;
                        }
                        schedule.addLesson(new Lesson(nameField.getText(), selectedRoom, selectedTeacher, selectedGroup, selectedStartTime, selectedEndTime));
                        new EditLessonsPopup().show();
                        close();
                    } catch (DateTimeParseException ex) {
                        new Alert(Alert.AlertType.ERROR, "Entered times are not valid.").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not create lesson, invalid values").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Lesson name is too short.").show();
            }
        });

        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        cancel.setOnAction(e -> {
            new EditLessonsPopup().show();
            close();
        });

        BorderPane pane = new BorderPane();
        pane.setTop(box);
        pane.setBottom(new HBox(create, cancel));

        Scene scene = new Scene(pane);
        setTitle("Create lesson");
        setScene(scene);
    }

    public Teacher getSelectedTeacher(ComboBox comboBox) {
        String teacher = (String) comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
        if(teacher != null) {
            return Schedule.getInstance().getTeacher(teacher);
        }
        return null;
    }

    public Group getSelectedGroup(ComboBox comboBox) {
        String group = (String) comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
        if(group != null) {
            return Schedule.getInstance().getGroup(group.split(" \\(")[0]);
        }

        return null;
    }

    public Room getSelectedRoom(ComboBox comboBox) {
        String room = (String) comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
        if(room != null) {
            return Schedule.getInstance().getRoom(room.split(" \\(")[0]);
        }
        return null;
    }
}
