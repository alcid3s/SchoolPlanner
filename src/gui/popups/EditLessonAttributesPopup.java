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

public class EditLessonAttributesPopup extends Stage{

    public EditLessonAttributesPopup(Lesson lesson){
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

        Button save = Util.getDefaultButton("Save changes", 50, 100);
        save.setOnAction(e -> {

            if(!nameField.getText().isEmpty()) {
                lesson.setName(nameField.getText());
            }if(!roomField.getText().isEmpty()) {
                lesson.setRoom(Schedule.getInstance().getRoom(roomField.getText()));
            }if(!teacherField.getText().isEmpty()) {
                lesson.setTeacher(Schedule.getInstance().getTeacher(teacherField.getText()));
            }if(!groupField.getText().isEmpty()) {
                lesson.setGroup(Schedule.getInstance().getGroup(groupField.getText()));
            }if(!startHourField.getText().isEmpty()) {
                lesson.setStartDate(startHourField.getText() + ":" + Util.getMinute(lesson.getStartDate()));
            }if(!endHourField.getText().isEmpty()){
                lesson.setEndDate(endHourField.getText() + ":" + Util.getMinute(lesson.getEndDate()));
            }if(!startMinuteField.getText().isEmpty()){
                lesson.setStartDate(Util.getHour(lesson.getStartDate() + ":" + startMinuteField.getText()));
            }if(!endMinuteField.getText().isEmpty()){
                lesson.setEndDate(Util.getHour(lesson.getEndDate()) + ":" + endMinuteField.getText());
            }
            new EditLessonsPopup().show();
            close();
        });

        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        cancel.setOnAction(e -> {
            new EditLessonsPopup().show();
            close();
        });

        BorderPane pane = new BorderPane();
        pane.setTop(box);
        pane.setBottom(new HBox(save,cancel));

        Scene scene = new Scene(pane);
        setTitle("Edit Attributes");
        setScene(scene);
    }
}
