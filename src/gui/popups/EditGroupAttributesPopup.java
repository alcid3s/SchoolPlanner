package gui.popups;

import data.Group;
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

public class EditGroupAttributesPopup extends Stage {
    private Group group;

    public EditGroupAttributesPopup(Group group) {
        this.group = group;
        setTitle("Edit Group");
        Label name = new Label(" Name: ");
        Label size = new Label("Group Size: ");
        TextField nameField = new TextField();
        TextField sizeField = new TextField();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(new VBox(name,size), new VBox(nameField,sizeField));
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);

        Button edit = Util.getDefaultButton("Save", 50, 100);
        Button cancel = Util.getDefaultButton("Cancel", 50, 100);
        borderPane.setBottom(new HBox(edit,cancel));
        cancel.setOnAction(e -> {
            new EditGroupsPopup().show();
            close();
        });
        edit.setOnAction(e -> {
            if(nameField.getText().length() > 0) {
                try {
                    group.setName(nameField.getText());
                    group.setSize(Integer.parseInt(sizeField.getText()));
                    new EditGroupsPopup().show();
                    close();
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Enter a valid number.");
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Name is too short.");
            }
        });

        Scene scene = new Scene(borderPane);
        setScene(scene);

    }
}
