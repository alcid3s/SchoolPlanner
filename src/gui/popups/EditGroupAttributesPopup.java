package gui.popups;

import data.Group;
import data.rooms.Classroom;
import data.rooms.Room;
import gui.Util;
import gui.Validation;
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

public class EditGroupAttributesPopup extends Stage{

    /**
     * Popup to edit a groups, attributes
     *
     * @param group to be edited
     */
    public EditGroupAttributesPopup(Group group){
        setTitle("Edit Group");
        Label name = new Label(" Name: ");
        Label size = new Label("Group Size: ");
        TextField nameField = new TextField();
        TextField sizeField = new TextField();
        sizeField.setText(group.getSize() + "");
        nameField.setText(group.getName());

        GridPane gridPane = new GridPane();
        gridPane.add(name, 0, 0);
        gridPane.add(size, 0, 1);
        gridPane.add(nameField, 1, 0);
        gridPane.add(sizeField, 1, 1);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridPane);

        Button edit = Util.getDefaultButton("Save", 50, 100);
        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        borderPane.setBottom(new HBox(edit, cancel));
        cancel.setOnAction(e -> {
            new EditGroupsPopup().show();
            close();
        });

        edit.setOnAction(e -> {
            boolean mayClose = true;
            try{
                if(!nameField.getText().isEmpty()){
                    group.setName(nameField.getText());

                }if(!sizeField.getText().isEmpty()){
                    int newSize = Integer.parseInt(sizeField.getText());
                    if(Validation.sizeIsValid(group,newSize)){
                        group.setSize(newSize);
                    }else{
                        mayClose = false;
                        new Alert(Alert.AlertType.ERROR, Validation.getMessage()).show();
                    }
                }

                if(mayClose){
                    ScheduleTab.refreshCanvas();
                    new EditGroupsPopup().show();
                    close();
                }
            } catch(NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Enter a valid number.").show();
            }
        });

        Scene scene = new Scene(borderPane);
        setScene(scene);

    }
}
