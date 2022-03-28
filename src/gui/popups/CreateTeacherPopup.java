package gui.popups;

import data.Schedule;
import data.persons.Teacher;
import gui.Util;
import gui.Validation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreateTeacherPopup extends Stage {

    /**
     * Popup for create Teacher
     */
    public CreateTeacherPopup() {
        Button create = Util.getDefaultButton("Create Teacher", 50, 134);
        Button cancel = Util.getDefaultButton("Cancel", 50, 100);

        cancel.setOnAction(e -> {
            new EditTeachersPopup().show();
            close();
        });

        Label name = new Label("Name: ");
        TextField nameField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.add(name, 0, 0);
        gridPane.add(nameField, 1, 0);

        create.setOnAction(e -> {
            if (nameField.getText().length() < 3) {
                new Alert(Alert.AlertType.ERROR, "Name is too short.").show();
            } else if (Validation.nameIsValid(nameField.getText()) && Validation.teacherIsUnique(nameField.getText())) {
                Schedule.getInstance().addTeacher(new Teacher(nameField.getText()));
                if (nameField.getText().equalsIgnoreCase("Rick")) {
                    new EasterEggPopup("src/gui/popups/song.mp4").show();
                } else {
                    new EditTeachersPopup().show();
                }
                close();
            } else {
                new Alert(Alert.AlertType.ERROR, Validation.getMessage()).show();
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridPane);
        borderPane.setBottom(new HBox(create, cancel));

        Scene scene = new Scene(borderPane);
        setTitle("Create teacher");
        setScene(scene);

    }
}
