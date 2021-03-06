package gui.popups.grouppopup;

import data.Group;
import data.Schedule;
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
import managers.Util;
import managers.Validation;

/**
 * Class CreateGroupPopup
 * Class to create popup for when user wants to create a group
 */
public class CreateGroupPopup extends Stage{

    /**
     * Constructor CreateGroupPopup
     * Popup to create group
     */
    public CreateGroupPopup(){
        setTitle("Create Group");
        Label name = new Label("Name: ");
        Label size = new Label("Group Size: ");
        TextField nameField = new TextField();
        TextField sizeField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.add(name, 0, 0);
        gridPane.add(size, 0, 1);
        gridPane.add(nameField, 1, 0);
        gridPane.add(sizeField, 1, 1);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridPane);

        Button create = Util.getDefaultButton("Create group", 50, 167);
        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        borderPane.setBottom(new HBox(create, cancel));
        create.setOnAction(e -> {
            if(nameField.getText().length() > 0){
                try {
                    int intSize = Integer.parseInt(sizeField.getText());
                    Group group = new Group(nameField.getText(), intSize);
                    if(Validation.groupIsUnique(group.getName()) && Validation.numberIsPositive(intSize)){
                        Schedule.getInstance().addGroup(group);
                        ScheduleTab.refreshCanvas();
                        new EditGroupsPopup().show();
                        close();
                    }else{
                        new Alert(Alert.AlertType.ERROR, Validation.getMessage()).show();
                    }
                } catch(NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Could not convert integer.").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Name is too short.").show();
            }
        });

        cancel.setOnAction(e -> {
            new EditGroupsPopup().show();
            close();
        });

        Scene scene = new Scene(borderPane);
        setScene(scene);
    }
}