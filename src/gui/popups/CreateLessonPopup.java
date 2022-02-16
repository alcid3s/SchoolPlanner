package gui.popups;

import data.Lesson;
import data.Schedule;
import gui.Util;
import gui.Validation;
import gui.tabs.ScheduleTab;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class CreateLessonPopup extends Stage {

    public CreateLessonPopup(ScheduleTab tab){
        //String name, Room room, Teacher teacher, Group group, LocalDateTime startDate, LocalDateTime endDate
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

        Button create = Util.getDefaultButton("Create lesson", 50, 100);
        create.setOnAction(e -> {
            if(nameField.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR, "Fill in a name").show();
            }else if(roomBox.getValue() == null){
                new Alert(Alert.AlertType.ERROR, "Fill in a room").show();
            }else if(teacherBox.getValue() == null){
                new Alert(Alert.AlertType.ERROR, "Fill in a teacher").show();
            }else if(groupBox.getValue() == null){
                new Alert(Alert.AlertType.ERROR, "Fill in a group").show();
            }else if(startHourBox.getValue() == null || startMinuteBox.getValue() == null){
                new Alert(Alert.AlertType.ERROR, "Fill in a start time").show();
            }else if(endHourBox.getValue() == null || endMinuteBox.getValue() == null){
                new Alert(Alert.AlertType.ERROR, "Fill in an end time").show();
            }else{
                Lesson lesson = new Lesson(nameField.getText(), Schedule.getInstance().getRoom(roomBox.getValue().toString()), Schedule.getInstance().getTeacher(teacherBox.getValue().toString()), Schedule.getInstance().getGroup(groupBox.getValue().toString()), Util.makeTime(startHourBox.getValue().toString(), startMinuteBox.getValue().toString()), Util.makeTime(endHourBox.getValue().toString(), endMinuteBox.getValue().toString()));
                if(Validation.lessonIsValid(lesson)){
                    Schedule.getInstance().addLesson(lesson);
                    tab.refreshCanvas();
                    new EditLessonsPopup(tab).show();
                    close();
                }else{
                    new Alert(Alert.AlertType.ERROR, Validation.getMessage()).show();
                }
            }
        });

        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        cancel.setOnAction(e -> {
            new EditLessonsPopup(tab).show();
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
