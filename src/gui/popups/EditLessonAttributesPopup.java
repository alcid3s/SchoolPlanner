package gui.popups;

import data.Group;
import data.Lesson;
import data.Schedule;
import data.persons.Person;
import data.persons.Teacher;
import data.rooms.Room;
import gui.Util;
import gui.Validation;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
            boolean mayClose = true;

            if(!(startHourBox.getValue() == null)){
                if(Validation.timeIsValid(Util.makeTime(startHourBox.getValue().toString(), "" + lesson.getStartDate().getMinute()), lesson.getEndDate())){
                    lesson.setStartDate(Util.makeTime(startHourBox.getValue().toString(), "" + lesson.getStartDate().getMinute()));
                }else{
                    mayClose = false;
                }
            }if(!(endHourBox.getValue() == null)){
                if(Validation.timeIsValid(lesson.getStartDate(), Util.makeTime(endHourBox.getValue().toString(), ""+ lesson.getEndDate().getMinute()))){
                    lesson.setEndDate(Util.makeTime(endHourBox.getValue().toString(), "" + lesson.getEndDate().getMinute()));
                }else{
                    mayClose = false;
                }
            }if(!(startMinuteBox.getValue() == null)){
                if(Validation.timeIsValid(Util.makeTime("" + lesson.getStartDate().getHour(), startMinuteBox.getValue().toString()), lesson.getEndDate())){
                    lesson.setStartDate(Util.makeTime("" + lesson.getStartDate().getHour(), startMinuteBox.getValue().toString()));
                }else{
                    mayClose = false;
                }
            }if(!(endMinuteBox.getValue() == null)){
                if(Validation.timeIsValid(lesson.getStartDate(), Util.makeTime("" + lesson.getEndDate().getHour(), endMinuteBox.getValue().toString()))){
                    lesson.setEndDate(Util.makeTime("" + lesson.getEndDate().getHour(), endMinuteBox.getValue().toString()));
                }else{
                    mayClose = false;
                }
            }if(!nameField.getText().isEmpty()){
                lesson.setName(nameField.getText());
            }if(!(roomBox.getValue() == null)){
                Room room = Schedule.getInstance().getRoom(roomBox.getValue().toString());
                if(Validation.scheduleIsAvailable(lesson.getStartDate(), lesson.getEndDate(), room, lesson.getRoom()) && Validation.isClassRoom(room) && Validation.sizeIsValid(room, lesson.getGroup())){
                    lesson.setRoom(room);
                }else{
                    mayClose = false;
                }
            }if(!(teacherBox.getValue() == null)){
                Person teacher = Schedule.getInstance().getTeacher(teacherBox.getValue().toString());
                if(Validation.scheduleIsAvailable(lesson.getStartDate(), lesson.getEndDate(), teacher, lesson.getTeacher())){
                    lesson.setTeacher(teacher);
                }else{
                    mayClose = false;
                }
            }if(!(groupBox.getValue() == null)){
                Group group = Schedule.getInstance().getGroup(groupBox.getValue().toString());
                if(Validation.scheduleIsAvailable(lesson.getStartDate(), lesson.getEndDate(), group, lesson.getGroup()) && Validation.sizeIsValid(lesson.getRoom(), group)){
                    lesson.setGroup(group);
                }else{
                    mayClose = false;
                }
            }

            if(mayClose){
                new EditLessonsPopup().show();
                close();
            }else{
                new Alert(Alert.AlertType.ERROR, Validation.getMessage()).show();
            }

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
