package gui.popups;

import data.Lesson;
import data.Schedule;
import gui.Util;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class EditLessonAttributesPopup extends Stage{

    public EditLessonAttributesPopup(Lesson lesson){
        TextField nameField = new TextField();
        ComboBox roomBox = new ComboBox(FXCollections.observableList(Schedule.getInstance().getRoomList()));
        ComboBox teacherBox = new ComboBox(FXCollections.observableList(Schedule.getInstance().getTeacherList()));
        ComboBox groupBox = new ComboBox(FXCollections.observableList(Schedule.getInstance().getGroupList()));
        ComboBox startHourBox = new ComboBox(Util.getHoursInList());
        ComboBox startMinuteBox = new ComboBox(Util.getMinutesInList());
        ComboBox endHourBox = new ComboBox(Util.getHoursInList());
        ComboBox endMinuteBox = new ComboBox(Util.getMinutesInList());

        startHourBox.setEditable(true);
        startMinuteBox.setEditable(true);
        endHourBox.setEditable(true);
        endMinuteBox.setEditable(true);

        VBox box = new VBox();
        box.getChildren().add(new HBox(new Label("Name:"), nameField));
        box.getChildren().add(new HBox(new Label("Room:"), roomBox));
        box.getChildren().add(new HBox(new Label("Teacher:"), teacherBox));
        box.getChildren().add(new HBox(new Label("Group:"), groupBox));
        box.getChildren().add(new HBox(new Label("Start Time:"), startHourBox,new Label(":"), startMinuteBox));
        box.getChildren().add(new HBox(new Label("End Time:"), endHourBox, new Label(":"), endMinuteBox));

        Button save = Util.getDefaultButton("Save changes", 50, 100);
        save.setOnAction(e -> {
            if(!nameField.getText().isEmpty()){
                lesson.setName(nameField.getText());
            }if(!(roomBox.getValue() == null)){
                lesson.setRoom(Schedule.getInstance().getRoom(roomBox.getValue().toString()));
            }if(!(teacherBox.getValue() == null)){
                lesson.setTeacher(Schedule.getInstance().getTeacher(teacherBox.getValue().toString()));
            }if(!(groupBox.getValue() == null)){
                lesson.setGroup(Schedule.getInstance().getGroup(groupBox.getValue().toString()));
            }if(!(startHourBox.getValue() == null)){
                lesson.setStartDate(Util.makeTime(startHourBox.getValue().toString(), "" + lesson.getStartDate().getMinute()));
            }if(!(endHourBox.getValue() == null)){
                lesson.setEndDate(Util.makeTime(endHourBox.getValue().toString(), "" + lesson.getEndDate().getMinute()));
            }if(!(startMinuteBox.getValue() == null)){
                lesson.setStartDate(Util.makeTime("" + lesson.getStartDate().getHour(), startMinuteBox.getValue().toString()));
            }if(!(endMinuteBox.getValue() == null)){
                lesson.setEndDate(Util.makeTime("" + lesson.getEndDate().getHour(), endMinuteBox.getValue().toString()));
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
