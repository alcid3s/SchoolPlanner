package gui.popups;

import data.persons.Person;
import data.persons.Teacher;
import gui.Util;
import gui.tabs.ScheduleTab;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EditTeacherAttributesPopup extends Stage{

    /**
     * Popup to edit a teachers attributes.
     *
     * @param teacher attributes to be edited
     */
    public EditTeacherAttributesPopup(Teacher teacher){
        Button edit = Util.getDefaultButton("Save", 50, 100);
        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        cancel.setOnAction(e -> {
            new EditTeachersPopup().show();
            close();
        });
        Label name = new Label("Name: ");
        name.setText(teacher.getName());
        TextField nameField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.add(name, 0, 0);
        gridPane.add(nameField, 1, 0);

        edit.setOnAction(e -> {
            if(nameField.getText().length() < 3){
                new Alert(Alert.AlertType.ERROR, "Name is too short.").show();
            }else{
                teacher.setName(nameField.getText());
                ScheduleTab.refreshCanvas();
                new EditTeachersPopup().show();
                close();
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridPane);
        borderPane.setBottom(new HBox(edit, cancel));

        Scene scene = new Scene(borderPane);
        setTitle("Edit teacher attributes");
        setScene(scene);
    }


}
