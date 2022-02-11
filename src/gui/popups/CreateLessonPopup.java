package gui.popups;

import data.Lesson;
import data.Schedule;
import gui.Util;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class CreateLessonPopup extends Stage {

    public CreateLessonPopup(){
        //String name, Room room, Teacher teacher, Group group, LocalDateTime startDate, LocalDateTime endDate
        Label name = new Label("Name:");
        Label room = new Label("Room:");
        Label teacher = new Label("Teacher:");
        Label group = new Label("Group:");
        Label startTime = new Label("Start Time:");
        Label endTime = new Label("End Time:");

        TextField nameField = new TextField();
        TextField roomField = new TextField();
        TextField teacherField = new TextField();
        TextField groupField = new TextField();
        TextField startHourField = new TextField();
        TextField startMinuteField = new TextField();
        TextField endHourField = new TextField();
        TextField endMinuteField = new TextField();

        VBox box = new VBox();
        box.getChildren().add(new HBox(name, nameField));
        box.getChildren().add(new HBox(room, roomField));
        box.getChildren().add(new HBox(teacher, teacherField));
        box.getChildren().add(new HBox(group, groupField));
        box.getChildren().add(new HBox(startTime, startHourField, new Label(":"), startMinuteField));
        box.getChildren().add(new HBox(endTime, endHourField, new Label(":"), endMinuteField));

        Button create = Util.getDefaultButton("Create lesson", 50, 100);
        create.setOnAction(e -> {
            if(nameField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR, "Fill in a name").show();
            }else if(roomField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR, "Fill in a room").show();
            }else if(teacherField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR, "Fill in a teacher").show();
            }else if(groupField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR, "Fill in a group").show();
            }else if(startHourField.getText().isEmpty() || startMinuteField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR, "Fill in a start time").show();
            }else if(endHourField.getText().isEmpty() || endMinuteField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR, "Fill in an end time").show();
            }else{
                Schedule.getInstance().addLesson(new Lesson(nameField.getText(), Schedule.getInstance().getRoom(roomField.getText()), Schedule.getInstance().getTeacher(teacherField.getText()), Schedule.getInstance().getGroup(groupField.getText()), startHourField.getText() + ":" + startMinuteField.getText(), endHourField.getText() + ":"  + endMinuteField.getText()));
                new EditLessonsPopup().show();
                close();
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
}
