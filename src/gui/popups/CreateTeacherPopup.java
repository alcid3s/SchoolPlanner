package gui.popups;

import data.Schedule;
import data.persons.Teacher;
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

public class CreateTeacherPopup extends Stage {

    public CreateTeacherPopup() {
        Button create = Util.getDefaultButton("Create Teacher", 50, 100);
        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        cancel.setOnAction(e -> {
            new EditTeachersPopup().show();
            close();
        });
        Label name = new Label("Name:");
        TextField nameField = new TextField();

        VBox vbox = new VBox();
        vbox.getChildren().add(new HBox(name,nameField));


        create.setOnAction(e -> {
            if(nameField.getText().length() < 3) {
                new Alert(Alert.AlertType.ERROR, "Name is too short.").show();
            } else {
                Schedule.getInstance().addTeacher(new Teacher(nameField.getText()));
                new EditTeachersPopup().show();
                close();
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vbox);
        borderPane.setBottom(new HBox(create,cancel));

        Scene scene = new Scene(borderPane);
        setTitle("Create teacher");
        setScene(scene);

    }
}
