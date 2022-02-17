package gui.popups;

import data.Group;
import data.Schedule;
import gui.Util;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreateGroupPopup extends Stage{

    /**
     * Popup for CreateGroup
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
                    Schedule.getInstance().addGroup(new Group(nameField.getText(), intSize));
                    new EditGroupsPopup().show();
                    close();
                } catch(NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Could not convert integer.");
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Name is too short.");
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
