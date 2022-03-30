package gui.popups.teacherpopups;

import data.Schedule;
import data.persons.Person;
import managers.Util;
import managers.Validation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EditTeachersPopup extends Stage {


    public EditTeachersPopup() {
        Schedule schedule = Schedule.getInstance();
        ListView<String> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ArrayList<String> teachersShow = new ArrayList<>();
        schedule.getTeacherList().forEach(t -> teachersShow.add(t.toString()));
        listView.getItems().addAll(teachersShow);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(listView);

        Button createTeacher = Util.getDefaultButton("Create new teacher", 50, 150);
        createTeacher.setOnAction(e -> {
            new CreateTeacherPopup().show();
            close();
        });

        Button removeTeacher = Util.getDefaultButton("Remove teacher", 50, 150);
        removeTeacher.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItems().size() > 0) {
                int selected = listView.getSelectionModel().getSelectedIndex();
                String teacherName = listView.getItems().get(selected);
                Person selectedTeacher = schedule.getTeacher(teacherName);
                if (selectedTeacher != null) {
                    if (Validation.teacherIsFree(selectedTeacher)) {
                        listView.getItems().remove(selected);
                        schedule.removeTeacher(selectedTeacher);
                    } else {
                        new Alert(Alert.AlertType.ERROR, Validation.getMessage()).show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not find teacher").show();
                }
            }
        });

        Button editTeacher = Util.getDefaultButton("Edit teacher", 50, 150);
        editTeacher.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItems().size() > 0) {
                int selected = listView.getSelectionModel().getSelectedIndex();
                String teacherName = listView.getItems().get(selected);
                Person selectedTeacher = schedule.getTeacher(teacherName);
                if (selectedTeacher != null) {
                    new EditTeacherAttributesPopup(selectedTeacher).show();
                    close();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not find teacher").show();
                }
            }
        });

        Button close = Util.getDefaultButton("Close", 50, 100);
        HBox hBox = new HBox(createTeacher, removeTeacher, editTeacher, close);
        close.setOnAction(e -> close());
        borderPane.setBottom(hBox);

        Scene scene = new Scene(borderPane);
        setScene(scene);
    }

}
