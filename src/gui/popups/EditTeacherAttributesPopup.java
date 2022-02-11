package gui.popups;

import data.Lesson;
import data.Schedule;
import data.persons.Teacher;
import data.rooms.Canteen;
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

public class EditTeacherAttributesPopup extends Stage {
    private Teacher teacher;
    public EditTeacherAttributesPopup(Teacher teacher) {
        this.teacher = teacher;
        Button edit = Util.getDefaultButton("Save", 50, 100);
        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        cancel.setOnAction(e -> {
            new EditTeachersPopup().show();
            close();
        });
        Label name = new Label("Name:");
        TextField nameField = new TextField();

        VBox vbox = new VBox();
        vbox.getChildren().add(new HBox(name,nameField));


        edit.setOnAction(e -> {
            if(nameField.getText().length() < 3) {
                new Alert(Alert.AlertType.ERROR, "Name is too short.").show();
            } else {
                teacher.setName(nameField.getText());
                new EditTeachersPopup().show();
                close();
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vbox);
        borderPane.setBottom(new HBox(edit,cancel));

        Scene scene = new Scene(borderPane);
        setTitle("Edit teacher attributes");
        setScene(scene);
    }


}
