package gui.popups;

import data.Group;
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

public class EditGroupAttributesPopup extends Stage{

    /**
     * Popup to edit a groups, attributes
     *
     * @param group to be edited
     */
    public EditGroupAttributesPopup(Group group){
        setTitle("Edit Group");
        Label name = new Label(" Name: ");
        name.setText(group.getName());
        Label size = new Label("Group Size: ");
        size.setText(group.getSize() + "");
        TextField nameField = new TextField();
        TextField sizeField = new TextField();

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
            if(nameField.getText().length() > 0){
                try {
                    group.setName(nameField.getText());
                    group.setSize(Integer.parseInt(sizeField.getText()));
                    ScheduleTab.refreshCanvas();
                    new EditGroupsPopup().show();
                    close();
                } catch(NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Enter a valid number.");
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Name is too short.");
            }
        });

        Scene scene = new Scene(borderPane);
        setScene(scene);

    }
}
