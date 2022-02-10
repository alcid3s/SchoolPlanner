package gui.popups;

import data.Group;
import data.Schedule;
import gui.Util;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateGroupPopup extends Stage {

    public CreateGroupPopup() {
        setTitle("Create Group");
        Label name = new Label(" Name: ");
        Label size = new Label("Group Size: ");
        TextField nameField = new TextField();
        TextField sizeField = new TextField();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(new HBox(name,nameField), new HBox(size,sizeField));
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vBox);

        Button create = Util.getDefaultButton("Create group", 50,100);
        Button cancel = Util.getDefaultButton("Cancel", 50,100);
        borderPane.setBottom(new HBox(create,cancel));
        create.setOnAction( e -> {
            if(nameField.getText().length() > 0) {
                try {
                    int intSize = Integer.parseInt(sizeField.getText());
                    Schedule.getInstance().addGroup(new Group(nameField.getText(), intSize));
                    new EditGroupsPopup().show();
                    close();
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Could not convert integer.");
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Name is too short.");
            }
        });

        cancel.setOnAction( e -> {
            new EditGroupsPopup().show();
            close();
        });
    }
}
